package com.tang.framework.interceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import com.tang.commons.annotation.DictPermission;
import com.tang.commons.model.SysDictDataModel;
import com.tang.commons.utils.RedisUtils;
import com.tang.commons.utils.SecurityUtils;
import com.tang.commons.utils.SpringUtils;

import static com.tang.commons.constants.CachePrefix.DICT_TYPE;
import static com.tang.commons.utils.StringUtils.format;

/**
 * 字典数据权限拦截器
 *
 * @author Tang
 */
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
    @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
})
public class DictPermissionInterceptor implements Interceptor {

    private final RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 如果未登录，则不进行字典数据权限过滤
        if (!SecurityUtils.isAuthenticated()) {
            return invocation.proceed();
        }

        var args = invocation.getArgs();
        var mappedStatement = (MappedStatement) args[0];
        var parameter = args[1];
        var rowBounds = (RowBounds) args[2];
        var resultHandler = (ResultHandler<?>) args[3];
        var executor = (Executor) invocation.getTarget();
        var cacheKey = args.length == 4 ? executor.createCacheKey(mappedStatement, parameter, rowBounds, mappedStatement.getBoundSql(parameter)) : (CacheKey) args[4];
        var boundSql = args.length == 4 ? mappedStatement.getBoundSql(parameter) : (BoundSql) args[5];
        var originalSql = boundSql.getSql();

        var roleDictDataMap = SecurityUtils.getDictPermissions();

        // 获取所有有字典数据权限的字段
        var fields = new LinkedHashMap<Field, DictPermission>();
        // 获取表名
        var tableName = findTableName(originalSql);

        if (mappedStatement.getId().endsWith("_COUNT")) {
            fields.putAll(getFields(parameter.getClass()));
        } else {
            fields.putAll(getFields(mappedStatement.getResultMaps().get(0).getType()));
        }

        // 如果没有字典数据权限字段，则不进行字典数据权限过滤
        if (fields.isEmpty()) {
            return invocation.proceed();
        }

        // 拼接字典数据权限 SQL
        var extraSql = new StringBuilder();
        fields.forEach((field, dictPermission) -> {
            var dictDataList = selectDictDataListByDictType(dictPermission.name());
            var alias = findTableAlias(originalSql, tableName);
            extraSql.append(" and ");
            if (Objects.nonNull(alias)) {
                extraSql.append(alias)
                    .append(".");
            }
            extraSql.append(field.getName())
                .append(" in (");
            var roleDictDataList = roleDictDataMap.get(dictPermission.name());
            extraSql.append(dictDataList.stream()
                .filter(dictData -> roleDictDataList.contains(dictData.getDataValue()))
                .map(dictData -> format("'{}'", dictData.getDataValue()))
                .collect(Collectors.joining(",")));
            extraSql.append(") ");
        });

        // 插入字典数据权限 SQL
        var keywordList = List.of("group by", "having", "order by", "limit");
        var indexKeyword = keywordList.stream()
            .filter(keyword -> StringUtils.containsIgnoreCase(originalSql, keyword))
            .findFirst()
            .orElse(null);
        var sql = new StringBuilder();
        if (Objects.nonNull(indexKeyword)) {
            sql.append(originalSql, 0, originalSql.indexOf(indexKeyword))
                .append(extraSql)
                .append(originalSql, originalSql.indexOf(indexKeyword), originalSql.length());
        } else {
            if (originalSql.contains("SELECT count(0) FROM (")) {
                sql.append(originalSql, 0, originalSql.lastIndexOf(")"))
                    .append(extraSql)
                    .append(originalSql, originalSql.lastIndexOf(")"), originalSql.length());
            } else {
                sql.append(originalSql)
                    .append(extraSql);
            }
        }

        boundSql = new BoundSql(mappedStatement.getConfiguration(), sql.toString(), boundSql.getParameterMappings(), boundSql.getParameterObject());
        cacheKey = executor.createCacheKey(mappedStatement, parameter, rowBounds, boundSql);

        return executor.query(mappedStatement, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    /**
     * 根据字典类型查询字典数据集合
     *
     * @param dictType 字典类型
     * @return 字典数据集合
     */
    private List<SysDictDataModel> selectDictDataListByDictType(String dictType) {
        var dictDataList = redisUtils.get(DICT_TYPE + dictType);
        if (dictDataList instanceof List<?> list) {
            return list.stream().map(SysDictDataModel.class::cast).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 获取所有字段
     *
     * @param clazz 类
     * @return LinkedHashMap
     */
    private static <T> LinkedHashMap<Field, DictPermission> getFields(Class<T> clazz) {
        var fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

        return fields.stream()
            .filter(field -> field.isAnnotationPresent(DictPermission.class))
            .collect(Collectors.toMap(field -> field, field -> AnnotationUtils.getAnnotation(field, DictPermission.class), (k1, k2) -> k1, LinkedHashMap::new));
    }

    /**
     * 获取表名
     *
     * @param sql SQL 语句
     * @return 表名
     */
    @Nullable
    private static String findTableName(String sql) {
        final var pattern = Pattern.compile("(?i)\\bfrom\\b\\s+(\\w+)");
        final var matcher = pattern.matcher(sql);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    /**
     * 获取表别名
     *
     * @param sql       SQL 语句
     * @param tableName 表名
     * @return 表别名
     */
    @Nullable
    private static String findTableAlias(String sql, String tableName) {
        final var pattern = Pattern.compile("(?i)\\b" + tableName + "\\b\\s+(\\w+)");
        final var matcher = pattern.matcher(sql);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

}

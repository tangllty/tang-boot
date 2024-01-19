package com.tang.framework.interceptor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import com.tang.commons.utils.SecurityUtils;

/**
 * 自动填充拦截器
 *
 * @author Tang
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class AutoFillInterceptor implements Interceptor {

    private static final String SET_CREATE_BY = "setCreateBy";

    private static final String SET_CREATE_TIME = "setCreateTime";

    private static final String SET_UPDATE_BY = "setUpdateBy";

    private static final String SET_UPDATE_TIME = "setUpdateTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        var invocationArgs = invocation.getArgs();
        var mappedStatement = (MappedStatement) invocationArgs[0];
        var invocationObject = invocationArgs[1];
        var sqlCommandType = mappedStatement.getSqlCommandType();

        switch (sqlCommandType) {
            case INSERT -> {
                var clazz = invocationObject.getClass();
                if (hasMethod(clazz, SET_CREATE_BY)) {
                    clazz.getMethod(SET_CREATE_BY, String.class).invoke(invocationObject, Objects.isNull(SecurityUtils.getUserModel()) ? null : SecurityUtils.getUser().getUsername());
                }
                if (hasMethod(clazz, SET_CREATE_TIME)) {
                    clazz.getMethod(SET_CREATE_TIME, LocalDateTime.class).invoke(invocationObject, LocalDateTime.now());
                }
            }
            case UPDATE -> {
                var clazz = invocationObject.getClass();
                if (hasMethod(clazz, SET_UPDATE_BY)) {
                    clazz.getMethod(SET_UPDATE_BY, String.class).invoke(invocationObject, SecurityUtils.getUser().getUsername());
                }
                if (hasMethod(clazz, SET_UPDATE_TIME)) {
                    clazz.getMethod(SET_UPDATE_TIME, LocalDateTime.class).invoke(invocationObject, LocalDateTime.now());
                }
            }
            default -> {
            }
        }

        return invocation.proceed();
    }

    private boolean hasMethod(Class<?> clazz, String method) {
        var methods = new ArrayList<Method>();
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        methods.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredMethods()));
        return methods.stream().anyMatch(m -> m.getName().equals(method));
    }

}

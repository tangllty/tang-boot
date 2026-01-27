package com.tang.generator.mapper;

import com.tang.commons.utils.If;
import com.tang.generator.entity.GenTable;
import com.tang.kite.annotation.Param;
import com.tang.kite.annotation.Select;
import com.tang.kite.mapper.BaseMapper;
import com.tang.kite.spring.annotation.Mapper;
import com.tang.kite.sql.function.SqlFunction;

import java.util.List;

/**
 * 代码生成表 gen_table 表数据库访问层
 *
 * @author Tang
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 获取代码生成列表
     *
     * @param table 代码生成对象
     * @return 代码生成列表
     */
    default List<GenTable> selectTableList(GenTable table) {
        return this.queryWrapper()
            .like(SqlFunction.lower(GenTable::getTableName), SqlFunction.lower(table.getTableName()), If.nonEmpty(table.getTableName()))
            .and(it -> {
                it.like(GenTable::getTableComment, table.getTableComment(), If.nonEmpty(table.getTableComment()))
                .or().like("to_pinyin(table_comment)", table.getTableComment(), If.nonEmpty(table.getTableComment()));
            })
            .orderByDesc(GenTable::getCreateTime)
            .list();
    }

    /**
     * 获取表列表
     *
     * @param table 代码生成对象
     * @return 表列表
     */
    @Select("""
        select table_name, table_comment, create_time, update_time
        from information_schema.tables
        where table_schema = (select database())
        and table_name not like 'gen_%'
        and table_name not in (select table_name from gen_table)
        if (tableName != null && tableName != '') {
            and lower(table_name) like lower(concat('%', #{tableName}, '%'))
        }
        if (tableComment != null and tableComment != '') {
            and table_comment like concat('%', #{tableComment}, '%')
        }
        order by create_time desc
        limit #{pageOffset}, #{pageSize}
    """)
    List<GenTable> selectDatabaseTableList(GenTable table);

    /**
     * 根据表名查询单条数据
     *
     * @param tableName 主键
     * @return 代码生成对象
     */
    @Select("""
        select table_name, table_comment
        from information_schema.tables
        where table_schema = (select database())
            and table_name not like 'gen_%'
            and table_name not in (select table_name from gen_table)
            and table_name = #{tableName}
    """)
    GenTable selectDatabaseTableByTableName(String tableName);

    /**
     * 根据表名查询单条数据
     *
     * @param tableName 主键
     * @return 代码生成对象
     */
    default GenTable selectTableByTableName(String tableName) {
        return this.queryWrapper().eq(GenTable::getTableName, tableName, If.nonEmpty(tableName)).one();
    }

    /**
     * 根据表名查询条数
     *
     * @param tableName 表名称
     * @return 条数
     */
    @Select("""
        select count(1)
        from information_schema.tables
        where table_schema = (select database())
            and table_name = #{tableName}
    """)
    Integer selectTableNameCount(@Param("tableName") String tableName);

}

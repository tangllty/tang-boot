package com.tang.generator.mapper;

import com.tang.generator.entity.GenTableColumn;
import com.tang.kite.annotation.Param;
import com.tang.kite.annotation.Select;
import com.tang.kite.mapper.BaseMapper;
import com.tang.kite.spring.annotation.Mapper;

import java.util.List;

/**
 * 代码字段生成表 gen_table_column 表数据库访问层
 *
 * @author Tang
 */
@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {

    /**
     * 通过主键获取代码字段生成列表
     *
     * @param tableId 主键
     * @return 代码字段生成列表
     */
    default List<GenTableColumn> selectTableColumnListByTableId(Long tableId) {
        return this.queryWrapper()
            .eq(GenTableColumn::getTableId, tableId)
            .orderByAsc(GenTableColumn::getSort)
            .list();
    }

    /**
     * 根据表名称查询代码字段生成列表
     *
     * @param tableName 表名称
     * @return 代码字段生成列表
     */
    @Select("""
        select column_name, column_comment, column_type, ordinal_position as sort,
            (case when column_key = 'PRI' then '1' else '0' end) as is_pk,
            (case when extra = 'auto_increment' then '1' else '0' end) as is_increment,
            (case when is_nullable = 'no' then '1' else 0 end) as is_required
        from information_schema.columns
        where table_schema = (select database()) and table_name = #{tableName}
        order by ordinal_position
    """)
    List<GenTableColumn> selectDatabaseTableColumnListByTableName(@Param("tableName") String tableName);

    /**
     * 通过表主键删除数据
     *
     * @param tableId 表主键
     * @return 影响行数
     */
    default int deleteTableColumnByTableId(Long tableId) {
        return this.deleteWrapper().eq(GenTableColumn::getTableId, tableId).delete();
    }

    /**
     * 通过表主键数组批量删除数据
     *
     * @param tableIds 表主键数组
     * @return 影响行数
     */
    default int deleteTableColumnByTableIds(Long[] tableIds) {
        return this.deleteWrapper().in(GenTableColumn::getTableId, tableIds).delete();
    }

}

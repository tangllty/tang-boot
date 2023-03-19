package com.tang.generator.mapper;

import java.util.List;

import com.tang.generator.entity.GenTableColumn;

/**
 * 代码字段生成表 gen_table_column 表数据库访问层
 *
 * @author Tang
 */
public interface GenTableColumnMapper {

    /**
     * 获取代码字段生成列表
     *
     * @param tableColumn 代码字段生成对象
     * @return 代码字段生成列表
     */
    List<GenTableColumn> selectTableColumnList(GenTableColumn tableColumn);

    /**
     * 通过主键获取代码字段生成列表
     *
     * @param tableId 主键
     * @return 代码字段生成列表
     */
    List<GenTableColumn> selectTableColumnListByTableId(Long tableId);

    /**
     * 根据表名称查询代码字段生成列表
     *
     * @param tableName 表名称
     * @return 代码字段生成列表
     */
    List<GenTableColumn> selectDatabaseTableColumnListByTableName(String tableName);

    /**
     * 根据主键查询单条数据
     *
     * @param columnId 主键
     * @return 代码字段生成对象
     */
    GenTableColumn selectTableColumnByColumnId(Long columnId);

    /**
     * 新增代码字段生成
     *
     * @param tableColumn 代码字段生成对象
     * @return 影响行数
     */
    int insertTableColumn(GenTableColumn tableColumn);

    /**
     * 修改代码字段生成信息
     *
     * @param tableColumn 代码字段生成对象
     * @return 影响行数
     */
    int updateTableColumnByColumnId(GenTableColumn tableColumn);

    /**
     * 通过主键删除数据
     *
     * @param columnId 主键
     * @return 影响行数
     */
    int deleteTableColumnByColumnId(Long columnId);

    /**
     * 通过表主键删除数据
     *
     * @param tableId 表主键
     * @return 影响行数
     */
    int deleteTableColumnByTableId(Long tableId);

    /**
     * 通过表主键数组批量删除数据
     *
     * @param tableIds 表主键数组
     * @return 影响行数
     */
    int deleteTableColumnByTableIds(Long[] tableIds);

}

package com.tang.generator.mapper;

import java.util.List;

import com.tang.generator.entity.GenTable;

/**
 * 代码生成表 gen_table 表数据库访问层
 *
 * @author Tang
 */
public interface GenTableMapper {

    /**
     * 获取代码生成列表
     *
     * @param table 代码生成对象
     * @return 代码生成列表
     */
    List<GenTable> selectTableList(GenTable table);

    /**
     * 获取表列表
     *
     * @param table 代码生成对象
     * @return 表列表
     */
    List<GenTable> selectDatabaseTableList(GenTable table);

    /**
     * 根据表名查询单条数据
     *
     * @param tableName 主键
     * @return 代码生成对象
     */
    GenTable selectDatabaseTableByTableName(String tableName);

    /**
     * 根据表名查询单条数据
     *
     * @param tableName 主键
     * @return 代码生成对象
     */
    GenTable selectTableByTableName(String tableName);

    /**
     * 根据主键查询单条数据
     *
     * @param tableId 主键
     * @return 代码生成对象
     */
    GenTable selectTableByTableId(Long tableId);

    /**
     * 新增代码生成
     *
     * @param table 代码生成对象
     * @return 影响行数
     */
    int insertTable(GenTable table);

    /**
     * 修改代码生成信息
     *
     * @param table 代码生成对象
     * @return 影响行数
     */
    int updateTableByTableId(GenTable table);

    /**
     * 通过主键删除数据
     *
     * @param tableId 主键
     * @return 影响行数
     */
    int deleteTableByTableId(Long tableId);

    /**
     * 通过表主键数组删除数据
     *
     * @param tableIds 表主键数组
     * @return 影响行数
     */
    int deleteTableByTableIds(Long[] tableIds);

}

package com.tang.generator.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tang.generator.entity.GenTableColumn;
import com.tang.generator.mapper.GenTableColumnMapper;
import com.tang.generator.service.GenTableColumnService;

/**
 * 代码字段生成表 gen_table_column 表服务实现类
 *
 * @author Tang
 */
@Service
public class GenTableColumnServiceImpl implements GenTableColumnService {

    private final GenTableColumnMapper tableColumnMapper;

    public GenTableColumnServiceImpl(GenTableColumnMapper tableColumnMapper) {
        this.tableColumnMapper = tableColumnMapper;
    }

    /**
     * 获取代码字段生成列表
     *
     * @param tableColumn 代码字段生成对象
     * @return 代码字段生成列表
     */
    @Override
    public List<GenTableColumn> selectTableColumnList(GenTableColumn tableColumn) {
        return tableColumnMapper.select(tableColumn);
    }

    /**
     * 通过主键获取代码字段生成列表
     *
     * @param tableId 主键
     * @return 代码字段生成列表
     */
    @Override
    public List<GenTableColumn> selectTableColumnListByTableId(Long tableId) {
        return tableColumnMapper.selectTableColumnListByTableId(tableId);
    }

    /**
     * 根据主键查询单条数据
     *
     * @param columnId 主键
     * @return 代码字段生成对象
     */
    @Override
    public GenTableColumn selectTableColumnByColumnId(Long columnId) {
        return tableColumnMapper.selectById(columnId);
    }

    /**
     * 新增代码字段生成
     *
     * @param tableColumn 代码字段生成对象
     * @return 影响行数
     */
    @Override
    public int insertTableColumn(GenTableColumn tableColumn) {
        tableColumn.setCreateTime(LocalDateTime.now());
        return tableColumnMapper.insertSelective(tableColumn);
    }

    /**
     * 修改代码字段生成信息
     *
     * @param tableColumn 代码字段生成对象
     * @return 影响行数
     */
    @Override
    public int updateTableColumnByColumnId(GenTableColumn tableColumn) {
        return tableColumnMapper.updateSelective(tableColumn);
    }

    /**
     * 通过主键删除数据
     *
     * @param columnId 主键
     * @return 影响行数
     */
    @Override
    public int deleteTableColumnByColumnId(Long columnId) {
        return tableColumnMapper.deleteById(columnId);
    }

}

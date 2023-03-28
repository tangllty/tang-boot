package com.tang.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.generator.entity.GenTable;
import com.tang.generator.mapper.GenTableColumnMapper;
import com.tang.generator.mapper.GenTableMapper;
import com.tang.generator.service.GenTableService;
import com.tang.generator.utils.TableColumnUtils;
import com.tang.generator.utils.TableUtils;
import com.tang.generator.utils.VelocityInitializer;
import com.tang.generator.utils.VelocityUtils;

/**
 * 代码生成表 GenTable 表服务实现类
 *
 * @author Tang
 */
@Service
public class GenTableServiceImpl implements GenTableService {

    private final GenTableMapper tableMapper;

    private final GenTableColumnMapper tableColumnMapper;

    public GenTableServiceImpl(GenTableMapper tableMapper, GenTableColumnMapper tableColumnMapper) {
        this.tableMapper = tableMapper;
        this.tableColumnMapper = tableColumnMapper;
    }

    /**
     * 获取代码生成列表
     *
     * @param table 代码生成对象
     * @return 代码生成列表
     */
    @Override
    public List<GenTable> selectTableList(GenTable table) {
        return tableMapper.selectTableList(table);
    }

    /**
     * 获取表列表
     *
     * @param table 代码生成对象
     * @return 表列表
     */
    @Override
    public List<GenTable> selectDatabaseTableList(GenTable table) {
        return tableMapper.selectDatabaseTableList(table);
    }

    /**
     * 根据主键查询单条数据
     *
     * @param tableId 主键
     * @return 代码生成对象
     */
    @Override
    public GenTable selectTableByTableId(Long tableId) {
        return tableMapper.selectTableByTableId(tableId);
    }

    /**
     * 新增代码生成
     *
     * @param table 代码生成对象
     * @return 影响行数
     */
    @Override
    public int insertTable(GenTable table) {
        return tableMapper.insertTable(table);
    }

    /**
     * 修改代码生成信息
     *
     * @param table 代码生成对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTableByTableId(GenTable table) {
        var rows = tableMapper.updateTableByTableId(table);
        if (rows > 0) {
            var tableColumnList = table.getTableColumnList();
            tableColumnList.forEach(tableColumn -> tableColumnMapper.updateTableColumnByColumnId(tableColumn));
        }
        return rows;
    }

    /**
     * 通过主键删除数据
     *
     * @param tableId 主键
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTableByTableId(Long tableId) {
        tableColumnMapper.deleteTableColumnByTableId(tableId);
        return tableMapper.deleteTableByTableId(tableId);
    }

    /**
     * 通过表主键数组批量删除数据
     *
     * @param tableIds 表主键集合
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTableByTableIds(Long[] tableIds) {
        tableColumnMapper.deleteTableColumnByTableIds(tableIds);
        return tableMapper.deleteTableByTableIds(tableIds);
    }

    /**
     * 导入表信息
     *
     * @param tableNames 表名称集合
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(String[] tableNames) {
        var tableList = Stream.of(tableNames).map(tableName -> tableMapper.selectDatabaseTableByTableName(tableName)).toList();
        tableList.forEach(table -> {
            TableUtils.initTable(table);
            var rows = tableMapper.insertTable(table);
            if (rows > 0) {
                var tableColumnList = tableColumnMapper.selectDatabaseTableColumnListByTableName(table.getTableName());
                tableColumnList.forEach(tableColumn -> {
                    tableColumn.setTableId(table.getTableId());
                    TableColumnUtils.initTableColumn(tableColumn);
                    tableColumnMapper.insertTableColumn(tableColumn);
                });
            }
        });
    }

    /**
     * 代码预览
     *
     * @param tableId 主键
     * @return 数据
     */
    @Override
    public Map<String, String> previewCode(Long tableId) {
        var data = new LinkedHashMap<String, String>();
        var table = tableMapper.selectTableByTableId(tableId);
        table.setTableColumnList(tableColumnMapper.selectTableColumnListByTableId(tableId));
        VelocityInitializer.initVelocity();
        var context = VelocityUtils.prepareContext(table);
        var templateList = VelocityUtils.getTemplateList();
        templateList.forEach(template -> {
            var stringWriter = new StringWriter();
            var tpl = Velocity.getTemplate(template, StandardCharsets.UTF_8.name());
            tpl.merge(context, stringWriter);
            data.put(template, stringWriter.toString());
        });
        return data;
    }

    /**
     * 批量代码下载
     *
     * @param tableNames 表名称集合
     * @return 数据
     */
    @Override
    public byte[] downloadCodes(String[] tableNames) {
        var outputStream = new ByteArrayOutputStream();
        var zip = new ZipOutputStream(outputStream);
        for (String tableName: tableNames) {
            downloadCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 代码下载
     *
     * @param tableName 表名称
     * @param zip 流
     */
    private void downloadCode(String tableName, ZipOutputStream zip) {
        var table = tableMapper.selectTableByTableName(tableName);
        table.setTableColumnList(tableColumnMapper.selectTableColumnListByTableId(table.getTableId()));
        VelocityInitializer.initVelocity();
        var context = VelocityUtils.prepareContext(table);
        var templateList = VelocityUtils.getTemplateList();
        templateList.forEach(template -> {
            var stringWriter = new StringWriter();
            var tpl = Velocity.getTemplate(template, StandardCharsets.UTF_8.name());
            tpl.merge(context, stringWriter);
            try {
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(stringWriter.toString(), zip, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(stringWriter);
                if (zip != null) {
                    try {
                        zip.flush();
                        zip.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

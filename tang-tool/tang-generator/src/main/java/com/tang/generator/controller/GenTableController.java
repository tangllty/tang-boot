package com.tang.generator.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.constants.ContentType;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.Assert;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.generator.entity.GenTable;
import com.tang.generator.service.GenTableColumnService;
import com.tang.generator.service.GenTableService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 代码生成逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/tool/generator")
public class GenTableController {

    private final GenTableService tableService;

    private final GenTableColumnService tableColumnService;

    public GenTableController(GenTableService tableService, GenTableColumnService tableColumnService) {
        this.tableService = tableService;
        this.tableColumnService = tableColumnService;
    }

    /**
     * 获取代码生成列表
     *
     * @param table 代码生成对象
     * @return 代码生成列表
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:list')")
    @GetMapping("/list")
    public TableDataResult list(GenTable table) {
        PageUtils.startPage();
        var list = tableService.selectTableList(table);
        return PageUtils.getDataTable(list);
    }

    /**
     * 获取表列表
     *
     * @param table 代码生成对象
     * @return 表列表
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:list')")
    @GetMapping("/table/list")
    public TableDataResult listDatabaseTable(GenTable table) {
        PageUtils.startPage();
        var list = tableService.selectDatabaseTableList(table);
        return PageUtils.getDataTable(list);
    }

    /**
     * 根据主键查询单条数据
     *
     * @param tableId 主键
     * @return 代码生成对象
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:list')")
    @GetMapping("/{tableId}")
    public AjaxResult getInfo(@PathVariable Long tableId) {
        var table = tableService.selectTableByTableId(tableId);
        var list = tableColumnService.selectTableColumnListByTableId(tableId);
        return AjaxResult.success(Map.of("table", table, "tableColumn", list));
    }

    /**
     * 修改代码生成信息
     *
     * @param table 代码生成对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody GenTable table) {
        return AjaxResult.rows(tableService.updateTableByTableId(table));
    }

    /**
     * 通过主键删除数据
     *
     * @param tableId 主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:delete')")
    @DeleteMapping("/{tableId}")
    public AjaxResult delete(@PathVariable Long tableId) {
        return AjaxResult.rows(tableService.deleteTableByTableId(tableId));
    }

    /**
     * 通过表主键数组批量删除数据
     *
     * @param tableIds 表主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] tableIds) {
        return AjaxResult.rows(tableService.deleteTableByTableIds(tableIds));
    }

    /**
     * 导入表信息
     *
     * @param tableNames 表名称集合
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:import')")
    @PostMapping("/import")
    public AjaxResult importTable(@RequestBody String[] tableNames) {
        var rows = tableService.importTable(tableNames);
        return AjaxResult.rows(rows);
    }

    /**
     * 代码预览
     *
     * @param tableId 主键
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:list')")
    @GetMapping("/preview/{tableId}")
    public AjaxResult previewCode(@PathVariable Long tableId) {
        return AjaxResult.success(tableService.previewCode(tableId));
    }

    /**
     * 代码生成
     *
     * @param tableName 表名称
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:export')")
    @GetMapping("/download/{tableName}")
    public void downloadCode(HttpServletResponse response, @PathVariable String tableName) throws IOException {
        var data = tableService.downloadCodes(new String[] {tableName});
        responseCode(response, data);
    }

    /**
     * 批量代码生成
     *
     * @param tableNames 表名称集合
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:export')")
    @GetMapping("/downloads")
    public void downloadCodes(HttpServletResponse response, String[] tableNames) throws IOException {
        Assert.isNull(tableNames, "表名称不能为空");

        var data = tableService.downloadCodes(tableNames);
        responseCode(response, data);
    }

    /**
     * 执行 SQL
     *
     * @param tableName 表名称
     * @return 结果
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:execute')")
    @PutMapping("/execute/{tableName}")
    public AjaxResult execute(@PathVariable String tableName) {
        int rows = tableService.executes(new String[] {tableName});
        return AjaxResult.success(rows);
    }

    /**
     * 批量执行 SQL
     *
     * @param tableNames 表名称集合
     * @return 结果
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:execute')")
    @PutMapping("/executes")
    public AjaxResult executes(String[] tableNames) {
        int rows = tableService.executes(tableNames);
        return AjaxResult.success(rows);
    }

    /**
     * 同步表数据
     *
     * @param tableName 表名称
     * @return 结果
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:sync')")
    @PutMapping("/sync/{tableName}")
    public AjaxResult sync(@PathVariable String tableName) {
        var rows = tableService.syncs(new String[] {tableName});
        return AjaxResult.rows(rows);
    }

    /**
     * 批量同步表数据
     *
     * @param tableNames 表名称集合
     * @return 结果
     */
    @PreAuthorize("@auth.hasPermission('tool:generator:sync')")
    @PutMapping("/syncs")
    public AjaxResult syncs(String[] tableNames) {
        var rows = tableService.syncs(tableNames);
        return AjaxResult.rows(rows);
    }

    /**
     * 生成zip文件
     */
    private void responseCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setContentType(ContentType.APPLICATION_ZIP);
        response.setHeader("Content-Disposition", "attachment; filename="+ System.currentTimeMillis() +".zip");
        IOUtils.write(data, response.getOutputStream());
    }

}

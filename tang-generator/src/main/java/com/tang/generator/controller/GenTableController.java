package com.tang.generator.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.generator.entity.GenTable;
import com.tang.generator.service.GenTableColumnService;
import com.tang.generator.service.GenTableService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 代码生成表 gen_table 表控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/tool/generator")
public class GenTableController {

    @Autowired
    private GenTableService tableService;

    @Autowired
    private GenTableColumnService tableColumnService;

    /**
     * 获取代码生成列表
     *
     * @param table 代码生成对象
     * @return 代码生成列表
     */
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
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:list')")
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
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:list')")
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
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody GenTable table) {
        return AjaxResult.success(tableService.updateTableByTableId(table));
    }

    /**
     * 通过主键删除数据
     *
     * @param tableId 主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:delete')")
    @DeleteMapping("/{tableId}")
    public AjaxResult delete(@PathVariable Long tableId) {
        return AjaxResult.success(tableService.deleteTableByTableId(tableId));
    }

    /**
     * 导入表信息
     *
     * @param tableNames 表名称集合
     */
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:import')")
    @PostMapping("/import")
    public AjaxResult importTable(String[] tableNames) {
        tableService.importTable(tableNames);
        return AjaxResult.success();
    }

    /**
     * 代码预览
     *
     * @param tableId 主键
     */
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:import')")
    @GetMapping("/preview/{tableId}")
    public AjaxResult importTable(@PathVariable Long tableId) {
        return AjaxResult.success(tableService.previewCode(tableId));
    }

    /**
     * 代码生成
     *
     * @param tableName 表名称
     */
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:export')")
    @GetMapping("/download/{tableName}")
    public void downloadCode(HttpServletResponse response, @PathVariable String tableName) throws IOException {
        byte[] data = tableService.downloadCodes(new String[] {tableName});
        responseCode(response, data);
    }

    /**
     * 批量代码生成
     *
     * @param tableNames 表名称集合
     */
    @PreAuthorize("@auth.hasAnyPermission('tool:generator:export')")
    @GetMapping("/downloads")
    public void downloadCodes(HttpServletResponse response, String[] tableNames) throws IOException {
        byte[] data = tableService.downloadCodes(tableNames);
        responseCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void responseCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"tang.zip\"");
        response.setContentType(ContentType.APPLICATION_OCTET_STREAM);
        IOUtils.write(data, response.getOutputStream());
    }


}

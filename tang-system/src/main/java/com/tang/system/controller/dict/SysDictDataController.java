package com.tang.system.controller.dict;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.PageResult;
import com.tang.system.entity.dict.SysDictData;
import com.tang.system.service.dict.SysDictDataService;

import jakarta.validation.Valid;

/**
 * 字典数据逻辑控制层
 *
 * @author Tang
 * @since 2023-02-23 21:05:59
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final SysDictDataService dictDataService;

    public SysDictDataController(SysDictDataService dictDataService) {
        this.dictDataService = dictDataService;
    }

    /**
     * 获取字典数据列表
     *
     * @param dictData 字典数据对象
     * @return 字典数据列表
     */
    @PreAuthorize("@auth.hasPermission('system:dict:list')")
    @GetMapping("/list")
    public PageResult list(SysDictData dictData) {
        PageUtils.startPage();
        var list = dictDataService.selectDictDataList(dictData);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    @PreAuthorize("@auth.hasPermission('system:dict:list')")
    @GetMapping("/type/{dictType}")
    public AjaxResult selectDictDataListByDictType(@PathVariable String dictType) {
        return AjaxResult.success(dictDataService.selectDictDataListByDictType(dictType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dataId 字典数据主键
     * @return 字典数据对象
     */
    @PreAuthorize("@auth.hasPermission('system:dict:list')")
    @GetMapping("/{dataId}")
    public AjaxResult selectDictDataByDataId(@PathVariable Long dataId) {
        return AjaxResult.success(dictDataService.selectDictDataByDataId(dataId));
    }

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:dict:add')")
    @PostMapping
    public AjaxResult add(@Valid @RequestBody SysDictData dictData) {
        return AjaxResult.rows(dictDataService.insertDictData(dictData));
    }

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:dict:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysDictData dictData) {
        return AjaxResult.rows(dictDataService.updateDictDataByDataId(dictData));
    }

    /**
     * 通过主键删除字典数据数据
     *
     * @param dataId 字典数据主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:dict:delete')")
    @DeleteMapping("/{dataId}")
    public AjaxResult delete(@PathVariable Long dataId) {
        return AjaxResult.rows(dictDataService.deleteDictDataByDataId(dataId));
    }

    /**
     * 批量删除字典数据数据
     *
     * @param dataIds 字典数据主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:dict:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] dataIds) {
        return AjaxResult.rows(dictDataService.deleteDictDataByDataIds(dataIds));
    }

}

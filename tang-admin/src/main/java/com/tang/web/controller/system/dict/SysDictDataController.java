package com.tang.web.controller.system.dict;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tang.commons.utils.page.TableDataResult;
import com.tang.system.entity.dict.SysDictData;
import com.tang.system.service.dict.SysDictDataService;

/**
 * 字典数据表 SysDictData 表控制层
 *
 * @author Tang
 * @since 2023-02-23 21:05:59
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    @Autowired
    private SysDictDataService dictDataService;

    /**
     * 获取字典数据列表
     *
     * @param dictData 字典数据对象
     * @return 字典数据列表
     */
    @GetMapping("/list")
    public TableDataResult list(SysDictData dictData) {
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
    @GetMapping("/type/{dictType}")
    public AjaxResult selectDictDataListByDictType(@PathVariable("dictType") String dictType) {
        return AjaxResult.success(dictDataService.selectDictDataListByDictType(dictType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dataId 字典数据主键
     * @return 字典数据对象
     */
    @GetMapping("/{dataId}")
    public AjaxResult selectDictDataByDataId(@PathVariable("dataId") Long dataId) {
        return AjaxResult.success(dictDataService.selectDictDataByDataId(dataId));
    }

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysDictData dictData) {
        return AjaxResult.success(dictDataService.insertDictData(dictData));
    }

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysDictData dictData) {
        return AjaxResult.success(dictDataService.updateDictDataByDataId(dictData));
    }

    /**
     * 通过主键删除字典数据数据
     *
     * @param dataId 字典数据主键
     * @return 影响行数
     */
    @DeleteMapping
    public AjaxResult delete(Long dataId) {
        return AjaxResult.success(dictDataService.deleteDictDataByDataId(dataId));
    }

}

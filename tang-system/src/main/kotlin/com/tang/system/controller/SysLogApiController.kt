package com.tang.system.controller;

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
import com.tang.commons.utils.page.TableDataResult;
import com.tang.system.entity.SysLogApi;
import com.tang.system.service.SysLogApiService;

/**
 * 接口日志逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/log/api")
open class SysLogApiController(private val sysLogApiService: SysLogApiService) {

    /**
     * 查询接口日志列表
     *
     * @param sysLogApi 接口日志对象
     * @return 接口日志列表
     */
    @PreAuthorize("@auth.hasPermission('system:system:list')")
    @GetMapping("/list")
    open fun list(sysLogApi: SysLogApi): TableDataResult {
        PageUtils.startPage()
        val list = sysLogApiService.selectSysLogApiList(sysLogApi)
        return PageUtils.getDataTable(list)
    }

    /**
     * 通过接口日志主键查询接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 接口日志信息
     */
    @PreAuthorize("@auth.hasPermission('system:system:list')")
    @GetMapping("/{apiId}")
    open fun selectSysLogApiByApiId(@PathVariable apiId: Long): AjaxResult {
        val sysLogApi = sysLogApiService.selectSysLogApiByApiId(apiId)
        return AjaxResult.success(sysLogApi)
    }

    /**
     * 新增接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:system:add')")
    @PostMapping
    open fun insertSysLogApi(@RequestBody sysLogApi: SysLogApi): AjaxResult {
        return AjaxResult.rows(sysLogApiService.insertSysLogApi(sysLogApi))
    }

    /**
     * 通过接口日志主键修改接口日志信息
     *
     * @param sysLogApi 接口日志信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:system:edit')")
    @PutMapping
    open fun updateSysLogApiByApiId(@RequestBody sysLogApi: SysLogApi): AjaxResult {
        return AjaxResult.rows(sysLogApiService.updateSysLogApiByApiId(sysLogApi))
    }

    /**
     * 通过接口日志主键删除接口日志信息
     *
     * @param apiId 接口日志主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:system:delete')")
    @DeleteMapping("/{apiId}")
    open fun deleteSysLogApiByApiId(@PathVariable apiId: Long): AjaxResult {
        return AjaxResult.rows(sysLogApiService.deleteSysLogApiByApiId(apiId))
    }

    /**
     * 通过接口日志主键数组批量删除接口日志信息
     *
     * @param apiIds 接口日志主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:system:delete')")
    @DeleteMapping
    open fun deleteSysLogApiByApiIds(@RequestBody apiIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(sysLogApiService.deleteSysLogApiByApiIds(apiIds))
    }

}
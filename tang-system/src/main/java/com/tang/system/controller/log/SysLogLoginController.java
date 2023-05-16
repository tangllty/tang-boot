package com.tang.system.controller.log;

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
import com.tang.system.entity.log.SysLogLogin;
import com.tang.system.service.log.SysLogLoginService;

/**
 * 登陆日志逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/log/login")
public class SysLogLoginController {

    private final SysLogLoginService sysLogLoginService;

    public SysLogLoginController(SysLogLoginService sysLogLoginService) {
        this.sysLogLoginService = sysLogLoginService;
    }

    /**
     * 查询登陆日志列表
     *
     * @param sysLogLogin 登陆日志对象
     * @return 登陆日志列表
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:list')")
    @GetMapping("/list")
    public TableDataResult list(SysLogLogin sysLogLogin){
        PageUtils.startPage();
        var list = sysLogLoginService.selectSysLogLoginList(sysLogLogin);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过登陆日志主键查询登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 登陆日志信息
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:list')")
    @GetMapping("/{loginId}")
    public AjaxResult selectSysLogLoginByLoginId(@PathVariable Long loginId) {
        return AjaxResult.success(sysLogLoginService.selectSysLogLoginByLoginId(loginId));
    }

    /**
     * 新增登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysLogLogin sysLogLogin) {
        return AjaxResult.success(sysLogLoginService.insertSysLogLogin(sysLogLogin));
    }

    /**
     * 通过登陆日志主键修改登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysLogLogin sysLogLogin) {
        return AjaxResult.success(sysLogLoginService.updateSysLogLoginByLoginId(sysLogLogin));
    }

    /**
     * 通过登陆日志主键删除登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:delete')")
    @DeleteMapping("/{loginId}")
    public AjaxResult delete(@PathVariable Long loginId) {
        return AjaxResult.success(sysLogLoginService.deleteSysLogLoginByLoginId(loginId));
    }

    /**
     * 通过登陆日志主键数组批量删除登陆日志信息
     *
     * @param loginIds 登陆日志主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:log:login:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] loginIds) {
        return AjaxResult.success(sysLogLoginService.deleteSysLogLoginByLoginIds(loginIds));
    }

}

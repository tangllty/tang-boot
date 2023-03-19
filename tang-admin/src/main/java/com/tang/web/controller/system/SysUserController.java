package com.tang.web.controller.system;

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

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.system.entity.SysUser;
import com.tang.system.service.SysRoleService;
import com.tang.system.service.SysUserService;

/**
 * 用户逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    /**
     * 获取用户列表
     *
     * @param user 用户对象
     * @return 用户列表
     */
    @PreAuthorize("@auth.hasPermission('system:user:list')")
    @GetMapping("/list")
    public TableDataResult list(SysUser user){
        PageUtils.startPage();
        var list = userService.selectUserList(user);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 用户主键
     * @return 用户对象
     */
    @PreAuthorize("@auth.hasPermission('system:user:list')")
    @GetMapping("/{userId}")
    public AjaxResult selectUserByUserId(@PathVariable Long userId) {
        return AjaxResult.success(userService.selectUserByUserId(userId));
    }

    /**
     * 获取角色下拉框数据
     *
     * @return 角色下拉框数据
     */
    @PreAuthorize("@auth.hasPermission('system:user:add')")
    @GetMapping("/getRoleSelect")
    public AjaxResult getRoleSelect() {
        return AjaxResult.success(roleService.selectRolesSelect());
    }

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:user:add')")
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {
        return AjaxResult.success(userService.insertUser(user));
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:user:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        return AjaxResult.success(userService.updateUserByUserId(user));
    }

    /**
     * 修改用户状态
     *
     * @param user 用户对象
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:user:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        return AjaxResult.success(userService.updateUserStatusByUserId(user));
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 用户主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:user:delete')")
    @DeleteMapping("/{userId}")
    public AjaxResult delete(@PathVariable Long userId) {
        return AjaxResult.success(userService.deleteUserByUserId(userId));
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('system:user:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] userIds) {
        return AjaxResult.success(userService.deleteUserByUserIds(userIds));
    }

}

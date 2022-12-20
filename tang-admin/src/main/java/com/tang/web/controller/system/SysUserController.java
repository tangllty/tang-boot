package com.tang.web.controller.system;

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
import com.tang.system.entity.SysUser;
import com.tang.system.service.SysUserService;

/**
 * 用户表 SysUser 表控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    /**
     * 服务对象
     */
    @Autowired
    private SysUserService userService;

    /**
     * 获取用户列表
     *
     * @param user 用户对象
     * @return 用户列表
     */
    @GetMapping("/list")
    public TableDataResult list(SysUser user){
        PageUtils.startPage();
        var list = userService.selectUserList(user);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 用户对象
     */
    @GetMapping("/{userId}")
    public AjaxResult selectUserByUserId(@PathVariable("userId") Long userId) {
        return AjaxResult.success(this.userService.selectUserByUserId(userId));
    }

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
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
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        return AjaxResult.success(this.userService.updateUserByUserId(user));
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    @DeleteMapping("/{userId}")
    public AjaxResult deleteById(@PathVariable Long userId) {
        return AjaxResult.success(userService.deleteUserByUserId(userId));
    }

}

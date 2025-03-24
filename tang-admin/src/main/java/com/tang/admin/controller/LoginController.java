package com.tang.admin.controller;

import java.util.Map;

import com.tang.commons.utils.RsaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.model.LoginModel;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.SecurityUtils;
import com.tang.framework.web.service.LoginService;
import com.tang.system.service.SysMenuService;

import jakarta.validation.Valid;

/**
 * 登陆验证逻辑控制层
 *
 * @author Tang
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    private final SysMenuService menuService;

    public LoginController(LoginService loginService, SysMenuService menuService) {
        this.loginService = loginService;
        this.menuService = menuService;
    }

    /**
     * 登陆
     *
     * @param loginModel 登陆模型
     * @return 令牌
     */
    @PostMapping("/login")
    public AjaxResult login(@Valid @RequestBody LoginModel loginModel) {
        loginModel.setPassword(RsaUtils.decrypt(loginModel.getPassword()));
        var token = loginService.login(loginModel);
        return AjaxResult.success(Map.of("token", token));
    }

    /**
     * 获取用户信息
     *
     * @return 信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        var user = SecurityUtils.getUser();
        var roles = SecurityUtils.getRoles();
        var roleNames = SecurityUtils.getRoleNames();
        var permissions = SecurityUtils.getPermissions();
        return AjaxResult.success(Map.of(
            "user", user,
            "roles", roles,
            "roleNames", roleNames,
            "permissions", permissions
        ));
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRoutes")
    public AjaxResult getRoutes() {
        var user = SecurityUtils.getUser();
        var menuList = menuService.selectMenuListTreeByUserId(user.getUserId());
        return AjaxResult.success(menuList);
    }

}

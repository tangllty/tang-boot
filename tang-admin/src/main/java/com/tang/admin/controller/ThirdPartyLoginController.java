package com.tang.admin.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.enumeration.LoginType;
import com.tang.commons.model.LoginModel;
import com.tang.commons.utils.AjaxResult;
import com.tang.framework.web.service.LoginService;
import com.tang.system.service.SysMenuService;

/**
 * 登陆验证逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/third-party/oauth")
public class ThirdPartyLoginController {

    private final LoginService loginService;

    public ThirdPartyLoginController(LoginService loginService, SysMenuService menuService) {
        this.loginService = loginService;
    }

    @RequestMapping("/github/redirect")
    public AjaxResult oauthRedirect(String code) throws URISyntaxException, IOException, InterruptedException {
        var loginModel = new LoginModel();
        loginModel.setCode(code);
        loginModel.setLoginType(LoginType.GITHUB.getName());
        loginModel.setCaptchaEnable(false);
        var token = loginService.login(loginModel);
        return AjaxResult.success(Map.of("token", token));
    }

}

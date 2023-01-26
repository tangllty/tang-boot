package com.tang.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.core.model.LoginModel;
import com.tang.commons.utils.AjaxResult;
import com.tang.framework.web.service.LoginService;

/**
 * 登陆验证
 *
 * @author Tang
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginModel loginModel) {
        String token = loginService.login(loginModel);
        return AjaxResult.success(Map.of("token", token));
    }

}

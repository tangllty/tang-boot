package com.tang.admin.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.autoconfigure.oauth.GitHubProperties;
import com.tang.commons.enumeration.LoginType;
import com.tang.commons.model.LoginModel;
import com.tang.commons.utils.StringUtils;
import com.tang.framework.web.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 登陆验证逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/third-party/oauth")
public class ThirdPartyLoginController {

    private final LoginService loginService;

    private final GitHubProperties gitHubProperties;

    public ThirdPartyLoginController(LoginService loginService, GitHubProperties gitHubProperties) {
        this.loginService = loginService;
        this.gitHubProperties = gitHubProperties;
    }

    @RequestMapping("/github/redirect")
    public void oauthRedirect(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        var loginModel = new LoginModel();
        loginModel.setCode(code);
        loginModel.setLoginType(LoginType.GITHUB.getValue());
        loginModel.setCaptchaEnable(false);
        var token = loginService.login(loginModel);
        var redirectUrl = StringUtils.format("{}?token={}", gitHubProperties.getRedirectUrl(), token);
        var params = request.getParameterMap();
        var redirectParams = params.get("redirect");
        if (redirectParams != null) {
            response.sendRedirect(redirectUrl + "&redirect=" + redirectParams[0]);
            return;
        }
        response.sendRedirect(redirectUrl);
    }

}

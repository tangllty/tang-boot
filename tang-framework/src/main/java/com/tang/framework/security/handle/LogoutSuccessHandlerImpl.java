package com.tang.framework.security.handle;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.ServletUtils;
import com.tang.framework.web.service.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 登出入口
 *
 * @author Tang
 */
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    /**
     * 登出成功
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var userModel = tokenService.get(request);
        if (userModel != null) {
            tokenService.delete(userModel.getToken());
        }
        var ajaxResult = AjaxResult.success("登出成功");
        var objectMapper = new ObjectMapper();
        var objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        var json = objectWriter.writeValueAsString(ajaxResult);
        ServletUtils.renderString(response, json);
    }

}

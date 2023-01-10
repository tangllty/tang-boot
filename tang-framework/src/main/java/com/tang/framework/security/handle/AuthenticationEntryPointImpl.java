package com.tang.framework.security.handle;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tang.commons.constants.HttpStatus;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.ServletUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证入口
 *
 * @author Tang
 */
@Configuration
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * 认证失败
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AjaxResult ajaxResult = AjaxResult.error(HttpStatus.UNAUTHORIZED, "认证失败，无法访问：" + request.getRequestURI());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(ajaxResult);
        ServletUtils.renderString(response, json);
    }

}

package com.tang.framework.interceptor;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson2.JSON;
import com.tang.commons.autoconfigure.TangProperties;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.ServletUtils;
import com.tang.commons.utils.SpringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.tang.commons.utils.StringUtils.format;

/**
 * 演示模式拦截器
 *
 * @author Tang
 */
public class DemoModeInterceptor implements HandlerInterceptor {

    private final TangProperties tangProperties = SpringUtils.getBean(TangProperties.class);

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (tangProperties.getDemoMode() && notAllowModify(request.getRequestURI(), request.getMethod())) {
            final var aElement = "<a class=\"el-link el-link--primary is-underline\" href=\"{}\" target=\"_blank\">{}</a>";
            final var github = format(aElement, "https://github.com/tangllty", "GitHub");
            final var gitee = format(aElement, "https://gitee.com/tangllty", "Gitee");
            final var chineseMessage = format("演示模式已开启，不允许修改数据。您可以在 {} 或 {} 下载或克隆源码进行操作。", github, gitee);
            final var englishMessage = format("Demo mode is enabled, It is not allowed to modify data. You can download or clone the source code for operation on {} or {}.", github, gitee);
            final var demoModeMessage = format("{}<br />{}", chineseMessage, englishMessage);
            var ajaxResult = AjaxResult.error(demoModeMessage);
            ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean notAllowModify(String requestURI, String method) {
        final var startWithWhiteList = List.of(
            "/websocket/", "/system/dict/data/type/", "/system/user/", "/system/dept/", "/system/role/", "/system/menu/",
            "/dict/type/", "/system/dict/data/", "/system/dict/type/", "/monitor/online/", "/tool/generator/",  "/generator/preview/",
            "/app/chat/friend/", "/uploads/avatar"
        );
        final var getWhiteList = List.of(
            "/getInfo", "/getRoutes", "/system/dept/deptTree", "/system/user/getRoleSelect", "/system/menu/menuTree",
            "/system/user/list", "/system/dept/list", "/system/role/list", "/system/menu/list", "/system/dict/type/list",
            "/system/log/login/list", "/monitor/online/list", "/monitor/server", "/tool/generator/list", "/tool/generator/table/list",
            "/tool/generator/downloads", "/app/chat/chat-list/list-all", "/app/chat/message/list", "/app/chat/friend-apply/list",
            "/app/chat/friend-apply/list-fuzzy", "/app/chat/friend/list", "/captcha", "/profile/login-log", "/index/user-visit",
            "/index/get-wechat-gitee", "/index/get-wechat-github", "/system/log/api/list"
        );
        final var postWhiteList = List.of("/login", "/app/chat/message");
        final var putWhiteList = List.of();
        final var deleteWhiteList = List.of();

        return !(
            (startWithWhiteList.stream().anyMatch(requestURI::startsWith) && method.equals("GET")) ||
            (getWhiteList.stream().anyMatch(requestURI::equals) && method.equals("GET")) ||
            (postWhiteList.stream().anyMatch(requestURI::equals) && method.equals("POST")) ||
            (putWhiteList.stream().anyMatch(requestURI::equals) && method.equals("PUT")) ||
            (deleteWhiteList.stream().anyMatch(requestURI::equals) && method.equals("DELETE"))
        );
    }

}

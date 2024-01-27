package com.tang.admin.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.system.service.log.SysLogLoginService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 首页逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private final SysLogLoginService logLoginService;

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public IndexController(SysLogLoginService logLoginService) {
        this.logLoginService = logLoginService;
    }

    /**
     * 用户访问量
     *
     * @return 用户访问量
     */
    @GetMapping("/user-visit")
    public AjaxResult userVisit() {
        var userVisit = logLoginService.selectUserVisit();
        return AjaxResult.success(userVisit);
    }

    /**
     * 获取微信二维码
     *
     * @param response 响应
     * @throws IOException          IO异常
     * @throws InterruptedException 中断异常
     * @throws URISyntaxException   URI异常
     */
    @GetMapping("/get-wechat")
    public void getWeChat(HttpServletResponse response) throws IOException, InterruptedException, URISyntaxException {
        var url = "https://gitee.com/tangllty/tang-docs/raw/master/docs/public/wechat.png";
        var requestResult = HttpRequest.newBuilder()
            .uri(new URI(url))
            .GET()
            .build();

        var responseResult = httpClient.send(requestResult, HttpResponse.BodyHandlers.ofByteArray());
        response.setContentType("image/png");
        response.getOutputStream().write(responseResult.body());
    }

}

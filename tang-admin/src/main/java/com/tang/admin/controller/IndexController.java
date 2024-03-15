package com.tang.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.system.service.log.SysLogLoginService;

/**
 * 首页逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private final SysLogLoginService logLoginService;

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

}

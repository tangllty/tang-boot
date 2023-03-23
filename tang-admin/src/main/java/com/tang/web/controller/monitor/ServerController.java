package com.tang.web.controller.monitor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.oshi.OshiUtils;

/**
 * 服务监控逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    /**
     * 获取服务器信息
     *
     * @return 服务器信息
     */
    @PreAuthorize("@auth.hasPermission('monitor:server:list')")
    @GetMapping
    public AjaxResult getInfo() {
        return AjaxResult.success(OshiUtils.getInfo());
    }

}

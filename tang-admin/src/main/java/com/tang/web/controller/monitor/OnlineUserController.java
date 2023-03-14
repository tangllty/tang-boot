package com.tang.web.controller.monitor;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.tang.commons.constants.HttpStatus;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.ServletUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.monitor.entity.OnlineUser;
import com.tang.monitor.service.OnlineUserService;

/**
 * 在线用户控制器
 *
 * @author Tang
 */
@RestController
@RequestMapping("/monitor/online")
public class OnlineUserController {

    @Autowired
    private OnlineUserService onlineUserService;

    @PreAuthorize("@auth.hasAnyPermission('monitor:online:list')")
    @GetMapping("/list")
    public TableDataResult list(OnlineUser onlineUser) {
        var pageNum = ServletUtils.getParameter("pageNum");
        var pageSize = ServletUtils.getParameter("pageSize");
        var pageNumLong = Long.parseLong(pageNum);
        var pageSizeLong = Long.parseLong(pageSize);
        var skip = (pageNumLong - 1) * pageSizeLong;
        var list = onlineUserService.selectOnlineUserList(onlineUser);
        list.sort(Comparator.comparing(OnlineUser::getLoginTime).reversed());
        var tableDataResult = new TableDataResult();
        tableDataResult.setCode(HttpStatus.SUCCESS);
        tableDataResult.setMsg("查询成功");
        tableDataResult.setTotal(new PageInfo<>(list).getTotal());
        list = list.stream().skip(skip).limit(pageSizeLong).toList();
        tableDataResult.setRows(list);
        return tableDataResult;
    }

    /**
     * 查询在线用户
     *
     * @param token 唯一凭证
     * @return 在线用户
     */
    @PreAuthorize("@auth.hasAnyPermission('monitor:online:list')")
    @GetMapping("/{token}")
    public AjaxResult getInfo(@PathVariable String token) {
        return AjaxResult.success(onlineUserService.selectOnlineUserByToken(token));
    }

    /**
     * 删除在线用户
     *
     * @param token 唯一凭证
     * @return 结果
     */
    @PreAuthorize("@auth.hasAnyPermission('monitor:online:delete')")
    @DeleteMapping("/{token}")
    public AjaxResult delete(@PathVariable String token) {
        return AjaxResult.success(onlineUserService.deleteOnlineUserByToken(token));
    }

}

package com.tang.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.app.entity.AppFriend;
import com.tang.app.service.AppFriendService;

/**
 * 用户好友逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/friend")
public class AppFriendController {

    private final AppFriendService appFriendService;

    public AppFriendController(AppFriendService appFriendService) {
        this.appFriendService = appFriendService;
    }

    /**
     * 查询用户好友列表
     *
     * @param appFriend 用户好友对象
     * @return 用户好友列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:list')")
    @GetMapping("/list")
    public TableDataResult list(AppFriend appFriend){
        PageUtils.startPage();
        var list = appFriendService.selectAppFriendList(appFriend);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过用户好友主键查询用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 用户好友信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:list')")
    @GetMapping("/{userFriendId}")
    public AjaxResult selectAppFriendByUserFriendId(@PathVariable Long userFriendId) {
        return AjaxResult.success(appFriendService.selectAppFriendByUserFriendId(userFriendId));
    }

    /**
     * 新增用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:add')")
    @PostMapping
    public AjaxResult add(@RequestBody AppFriend appFriend) {
        return AjaxResult.rows(appFriendService.insertAppFriend(appFriend));
    }

    /**
     * 通过用户好友主键修改用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody AppFriend appFriend) {
        return AjaxResult.rows(appFriendService.updateAppFriendByUserFriendId(appFriend));
    }

    /**
     * 通过用户好友主键删除用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:delete')")
    @DeleteMapping("/{userFriendId}")
    public AjaxResult delete(@PathVariable Long userFriendId) {
        return AjaxResult.rows(appFriendService.deleteAppFriendByUserFriendId(userFriendId));
    }

    /**
     * 通过用户好友主键数组批量删除用户好友信息
     *
     * @param userFriendIds 用户好友主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] userFriendIds) {
        return AjaxResult.rows(appFriendService.deleteAppFriendByUserFriendIds(userFriendIds));
    }

}

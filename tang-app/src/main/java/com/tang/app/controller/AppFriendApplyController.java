package com.tang.app.controller;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.app.entity.AppFriendApply;
import com.tang.app.service.AppFriendApplyService;
import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.page.PageUtils;
import com.tang.commons.utils.page.TableDataResult;
import com.tang.system.service.SysUserService;

/**
 * 用户好友申请逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/friend-apply")
public class AppFriendApplyController {

    private final AppFriendApplyService appFriendApplyService;

    private final SysUserService userService;

    public AppFriendApplyController(AppFriendApplyService appFriendApplyService, SysUserService userService) {
        this.appFriendApplyService = appFriendApplyService;
        this.userService = userService;
    }

    /**
     * 查询用户好友申请列表
     *
     * @param appFriendApply 用户好友申请对象
     * @return 用户好友申请列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:list')")
    @GetMapping("/list")
    public TableDataResult list(AppFriendApply appFriendApply){
        PageUtils.startPage();
        var list = appFriendApplyService.selectAppFriendApplyList(appFriendApply);
        return PageUtils.getDataTable(list);
    }

    /**
     * 模糊查询用户列表
     *
     * @param keyword 关键字
     * @return 用户列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:list')")
    @GetMapping("/list-fuzzy")
    public TableDataResult listFuzzy(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return PageUtils.getDataTable(Collections.emptyList());
        }
        PageUtils.startPage();
        var list = userService.selectUserListFuzzy(keyword);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过用户好友申请主键查询用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 用户好友申请信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:list')")
    @GetMapping("/{applyId}")
    public AjaxResult selectAppFriendApplyByApplyId(@PathVariable Long applyId) {
        return AjaxResult.success(appFriendApplyService.selectAppFriendApplyByApplyId(applyId));
    }

    /**
     * 新增用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:add')")
    @PostMapping
    public AjaxResult add(@RequestBody AppFriendApply appFriendApply) {
        return AjaxResult.rows(appFriendApplyService.insertAppFriendApply(appFriendApply));
    }

    /**
     * 通过用户好友申请主键修改用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody AppFriendApply appFriendApply) {
        return AjaxResult.rows(appFriendApplyService.updateAppFriendApplyByApplyId(appFriendApply));
    }

    /**
     * 同意好友申请
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:accept')")
    @PutMapping("/accept/{uniqueId}")
    public AjaxResult accept(@PathVariable Long uniqueId) {
        return AjaxResult.rows(appFriendApplyService.acceptAppFriendApply(uniqueId));
    }

    /**
     * 拒绝好友申请
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:decline')")
    @PutMapping("/decline/{uniqueId}")
    public AjaxResult decline(@PathVariable Long uniqueId) {
        return AjaxResult.rows(appFriendApplyService.declineAppFriendApply(uniqueId));
    }

    /**
     * 通过用户好友申请主键删除用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:delete')")
    @DeleteMapping("/{applyId}")
    public AjaxResult delete(@PathVariable Long applyId) {
        return AjaxResult.rows(appFriendApplyService.deleteAppFriendApplyByApplyId(applyId));
    }

    /**
     * 通过用户好友申请主键数组批量删除用户好友申请信息
     *
     * @param applyIds 用户好友申请主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend-apply:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] applyIds) {
        return AjaxResult.rows(appFriendApplyService.deleteAppFriendApplyByApplyIds(applyIds));
    }

}

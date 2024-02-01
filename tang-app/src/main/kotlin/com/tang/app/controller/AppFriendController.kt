package com.tang.app.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.tang.app.entity.AppFriend
import com.tang.app.service.AppFriendService
import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.page.PageUtils
import com.tang.commons.utils.page.TableDataResult

/**
 * 用户好友逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/friend")
class AppFriendController(private val appFriendService: AppFriendService) {

    /**
     * 查询用户好友列表
     *
     * @param appFriend 用户好友对象
     * @return 用户好友列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:list')")
    @GetMapping("/list")
    fun list(appFriend: AppFriend): TableDataResult {
        PageUtils.startPage()
        val list: List<AppFriend> = appFriendService.selectAppFriendList(appFriend)
        return PageUtils.getDataTable(list)
    }

    /**
     * 通过用户好友主键查询用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 用户好友信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:list')")
    @GetMapping("/{userFriendId}")
    fun selectAppFriendByUserFriendId(@PathVariable userFriendId: Long): AjaxResult {
        return AjaxResult.success(appFriendService.selectAppFriendByUserFriendId(userFriendId))
    }

    /**
     * 新增用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:add')")
    @PostMapping
    fun add(@RequestBody appFriend: AppFriend): AjaxResult {
        return AjaxResult.rows(appFriendService.insertAppFriend(appFriend))
    }

    /**
     * 通过用户好友主键修改用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:edit')")
    @PutMapping
    fun edit(@RequestBody appFriend: AppFriend): AjaxResult {
        return AjaxResult.rows(appFriendService.updateAppFriendByUserFriendId(appFriend))
    }

    /**
     * 通过用户好友主键删除用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:delete')")
    @DeleteMapping("/{userFriendId}")
    fun delete(@PathVariable userFriendId: Long): AjaxResult {
        return AjaxResult.rows(appFriendService.deleteAppFriendByUserFriendId(userFriendId))
    }

    /**
     * 通过用户好友主键数组批量删除用户好友信息
     *
     * @param userFriendIds 用户好友主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:friend:delete')")
    @DeleteMapping
    fun deletes(@RequestBody userFriendIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(appFriendService.deleteAppFriendByUserFriendIds(userFriendIds))
    }

}

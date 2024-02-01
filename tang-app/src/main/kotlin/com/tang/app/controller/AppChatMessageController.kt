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

import com.tang.app.entity.AppChatMessage
import com.tang.app.service.AppChatMessageService
import com.tang.commons.utils.AjaxResult
import com.tang.commons.utils.page.PageUtils
import com.tang.commons.utils.page.TableDataResult

/**
 * 聊天消息逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/message")
class AppChatMessageController(private val appChatMessageService: AppChatMessageService) {

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:list')")
    @GetMapping("/list")
    fun list(appChatMessage: AppChatMessage): TableDataResult {
        if (appChatMessage.chatListId == null) {
            return PageUtils.getDataTable(emptyList<Any>())
        }
        PageUtils.startPage()
        val list: List<AppChatMessage> = appChatMessageService.selectAppChatMessageList(appChatMessage)
        return PageUtils.getDataTable(list)
    }

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:list')")
    @GetMapping("/{messageId}")
    fun selectAppChatMessageByMessageId(@PathVariable messageId: Long): AjaxResult {
        return AjaxResult.success(appChatMessageService.selectAppChatMessageByMessageId(messageId))
    }

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:add')")
    @PostMapping
    fun add(@RequestBody appChatMessage: AppChatMessage): AjaxResult {
        val rows = appChatMessageService.insertAppChatMessage(appChatMessage)
        return if (rows > 0) AjaxResult.success(appChatMessage) else AjaxResult.error()
    }

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:edit')")
    @PutMapping
    fun edit(@RequestBody appChatMessage: AppChatMessage): AjaxResult {
        return AjaxResult.rows(appChatMessageService.updateAppChatMessageByMessageId(appChatMessage))
    }

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:delete')")
    @DeleteMapping("/{messageId}")
    fun delete(@PathVariable messageId: Long): AjaxResult {
        return AjaxResult.rows(appChatMessageService.deleteAppChatMessageByMessageId(messageId))
    }

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:delete')")
    @DeleteMapping
    fun deletes(@RequestBody messageIds: Array<Long>): AjaxResult {
        return AjaxResult.rows(appChatMessageService.deleteAppChatMessageByMessageIds(messageIds))
    }

}

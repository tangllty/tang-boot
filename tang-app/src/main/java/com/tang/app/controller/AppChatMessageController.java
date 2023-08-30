package com.tang.app.controller;

import java.util.Collections;

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
import com.tang.app.entity.AppChatMessage;
import com.tang.app.service.AppChatMessageService;

/**
 * 聊天消息逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/app/chat/message")
public class AppChatMessageController {

    private final AppChatMessageService appChatMessageService;

    public AppChatMessageController(AppChatMessageService appChatMessageService) {
        this.appChatMessageService = appChatMessageService;
    }

    /**
     * 查询聊天消息列表
     *
     * @param appChatMessage 聊天消息对象
     * @return 聊天消息列表
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:list')")
    @GetMapping("/list")
    public TableDataResult list(AppChatMessage appChatMessage){
        if (appChatMessage.getChatListId() == null) {
            return PageUtils.getDataTable(Collections.emptyList());
        }
        PageUtils.startPage();
        var list = appChatMessageService.selectAppChatMessageList(appChatMessage);
        return PageUtils.getDataTable(list);
    }

    /**
     * 通过聊天消息主键查询聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 聊天消息信息
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:list')")
    @GetMapping("/{messageId}")
    public AjaxResult selectAppChatMessageByMessageId(@PathVariable Long messageId) {
        return AjaxResult.success(appChatMessageService.selectAppChatMessageByMessageId(messageId));
    }

    /**
     * 新增聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:add')")
    @PostMapping
    public AjaxResult add(@RequestBody AppChatMessage appChatMessage) {
        int rows = appChatMessageService.insertAppChatMessage(appChatMessage);
        return rows > 0 ? AjaxResult.success(appChatMessage) : AjaxResult.error();
    }

    /**
     * 通过聊天消息主键修改聊天消息信息
     *
     * @param appChatMessage 聊天消息信息
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody AppChatMessage appChatMessage) {
        return AjaxResult.rows(appChatMessageService.updateAppChatMessageByMessageId(appChatMessage));
    }

    /**
     * 通过聊天消息主键删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:delete')")
    @DeleteMapping("/{messageId}")
    public AjaxResult delete(@PathVariable Long messageId) {
        return AjaxResult.rows(appChatMessageService.deleteAppChatMessageByMessageId(messageId));
    }

    /**
     * 通过聊天消息主键数组批量删除聊天消息信息
     *
     * @param messageIds 聊天消息主键数组
     * @return 影响行数
     */
    @PreAuthorize("@auth.hasPermission('app:chat:message:delete')")
    @DeleteMapping
    public AjaxResult deletes(@RequestBody Long[] messageIds) {
        return AjaxResult.rows(appChatMessageService.deleteAppChatMessageByMessageIds(messageIds));
    }

}

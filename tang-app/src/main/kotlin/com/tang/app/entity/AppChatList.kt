package com.tang.app.entity

import com.tang.commons.base.entity.BaseEntity

/**
 * 聊天列表实体类 app_chat_list
 *
 * @author Tang
 */
class AppChatList : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 聊天列表ID
     */
    var chatListId: Long = 0

    /**
     * 用户ID
     */
    var userId: Long = 0

    /**
     * 聊天ID
     */
    var chatId: Long = 0

    /**
     * 聊天类型
     *
     * @see com.tang.app.constants.ChatType
     */
    var chatType: String? = null

    var username: String? = null

    var nickname: String? = null

    var name: String? = null

    var avatar: String? = null

    var message: String? = null

    var unreadCount: Int? = null

    var time: String? = null

    /**
     * 置顶标记
     *
     * @see com.tang.app.constants.AppChatListStick
     */
    var stickFlag: String? = null

    /**
     * 显示标记{0=否, 1=是}
     */
    var displayFlag: String? = null

    /**
     * 免打扰标记{0=否, 1=是}
     */
    var muteFlag: String? = null

}

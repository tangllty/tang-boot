package com.tang.app.entity

import com.tang.commons.base.entity.BaseEntity

/**
 * 用户好友实体类 app_friend
 *
 * @author Tang
 */
class AppFriend : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 用户好友ID
     */
    var userFriendId: Long? = null

    /**
     * 用户ID
     */
    var userId: Long? = null

    /**
     * 好友ID
     */
    var friendId: Long? = null

    /**
     * 唯一标识
     */
    var uniqueId: Long? = null

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 昵称
     */
    var nickname: String? = null

}

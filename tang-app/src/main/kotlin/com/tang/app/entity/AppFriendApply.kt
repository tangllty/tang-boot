package com.tang.app.entity

import com.tang.commons.base.entity.BaseEntity

/**
 * 用户好友申请实体类 app_friend_apply
 *
 * @author Tang
 */
class AppFriendApply : BaseEntity() {

    companion object {
        @java.io.Serial
        private val serialVersionUID = 1L
    }

    /**
     * 申请ID
     */
    var applyId: Long? = null

    /**
     * 用户ID
     */
    var userId: Long? = null

    /**
     * 好友ID
     */
    var friendId: Long? = null

    /**
     * 申请者ID
     */
    var requestorId: Long? = null

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

    /**
     * 申请理由
     */
    var reason: String? = null

    /**
     * 申请类型{0=申请添加好友, 1=申请添加群组}
     */
    var applyType: String? = null

    /**
     * 申请状态{0=已申请, 1=已同意, 2=已拒绝}
     */
    var status: String? = null

}

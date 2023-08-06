package com.tang.app.entity;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 用户好友申请实体类 app_friend_apply
 *
 * @author Tang
 */
public class AppFriendApply extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = 7549229705468840978L;

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long friendId;

    /**
     * 申请者ID
     */
    private Long requestorId;

    /**
     * 唯一标识
     */
    private Long uniqueId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 申请类型{0=申请添加好友, 1=申请添加群组}
     */
    private String applyType;

    /**
     * 申请状态{0=已申请, 1=已同意, 2=已拒绝}
     */
    private String status;


    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

package com.tang.app.entity;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 用户好友实体类 app_friend
 *
 * @author Tang
 */
public class AppFriend extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = 3418336663574009608L;

    /**
     * 用户好友ID
     */
    private Long userFriendId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long friendId;

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


    public Long getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(Long userFriendId) {
        this.userFriendId = userFriendId;
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

}

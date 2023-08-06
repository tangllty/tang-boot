package com.tang.app.entity;

import com.tang.commons.base.entity.BaseEntity;

/**
 * 聊天列表实体类 app_chat_list
 *
 * @author Tang
 */
public class AppChatList extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = -729260449825213462L;

    /**
     * 聊天列表ID
     */
    private Long chatListId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long friendId;

    private String username;

    private String nickname;

    private String name;

    private String avatar;

    private String message;

    private Integer unreadCount;

    private String time;

    /**
     * 置顶标记{0=否, 1=是}
     */
    private String stickFlag;

    /**
     * 显示标记{0=否, 1=是}
     */
    private String displayFlag;

    /**
     * 免打扰标记{0=否, 1=是}
     */
    private String muteFlag;


    public Long getChatListId() {
        return chatListId;
    }

    public void setChatListId(Long chatListId) {
        this.chatListId = chatListId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStickFlag() {
        return stickFlag;
    }

    public void setStickFlag(String stickFlag) {
        this.stickFlag = stickFlag;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    public String getMuteFlag() {
        return muteFlag;
    }

    public void setMuteFlag(String muteFlag) {
        this.muteFlag = muteFlag;
    }

}

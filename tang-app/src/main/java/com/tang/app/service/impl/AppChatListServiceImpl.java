package com.tang.app.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.tang.app.constants.AppChatListStick;
import com.tang.app.entity.AppChatList;
import com.tang.app.mapper.AppChatListMapper;
import com.tang.app.mapper.AppChatMessageMapper;
import com.tang.app.service.AppChatListService;
import com.tang.commons.utils.SecurityUtils;
import com.tang.commons.utils.date.DateUtils;
import com.tang.system.mapper.SysUserMapper;

/**
 * 聊天列业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
public class AppChatListServiceImpl implements AppChatListService {

    private final AppChatListMapper appChatListMapper;

    private final AppChatMessageMapper appChatMessageMapper;

    private final SysUserMapper userMapper;

    public AppChatListServiceImpl(AppChatListMapper appChatListMapper, AppChatMessageMapper appChatMessageMapper, SysUserMapper userMapper) {
        this.appChatListMapper = appChatListMapper;
        this.appChatMessageMapper = appChatMessageMapper;
        this.userMapper = userMapper;
    }

    /**
     * 查询聊天列列表
     *
     * @param appChatList 聊天列对象
     * @return 聊天列列表
     */
    @Override
    public List<AppChatList> selectAppChatListList(AppChatList appChatList) {
        return appChatListMapper.selectAppChatListList(appChatList);
    }

    /**
     * 查询当前用户聊天列表列表
     *
     * @return 聊天列表列表
     */
    @Override
    public List<AppChatList> selectAppChatListListAll() {
        var userId = SecurityUtils.getUser().getUserId();
        var list = appChatListMapper.selectAppChatListListAllByUserId(userId);
        list.forEach(item -> {
            var lastMessage = appChatMessageMapper.selectLastMessage(item.getChatListId(), item.getFriendId());
            if (Objects.nonNull(lastMessage)) {
                item.setAvatar(userMapper.selectUserByUserId(item.getFriendId()).getAvatar());
                item.setMessage(lastMessage.getContent());
                item.setTime(DateUtils.getChatTime(lastMessage.getCreateTime()));
            }
        });
        return list;
    }

    /**
     * 通过聊天列主键查询聊天列信息
     *
     * @param chatListId 聊天列主键
     * @return 聊天列信息
     */
    @Override
    public AppChatList selectAppChatListByChatListId(Long chatListId) {
        return appChatListMapper.selectAppChatListByChatListId(chatListId);
    }

    /**
     * 新增聊天列信息
     *
     * @param appChatList 聊天列信息
     * @return 影响行数
     */
    @Override
    public int insertAppChatList(AppChatList appChatList) {
        return appChatListMapper.insertAppChatList(appChatList);
    }

    /**
     * 通过聊天列主键修改聊天列信息
     *
     * @param appChatList 聊天列信息
     * @return 影响行数
     */
    @Override
    public int updateAppChatListByChatListId(AppChatList appChatList) {
        return appChatListMapper.updateAppChatListByChatListId(appChatList);
    }

    /**
     * 置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    @Override
    public int stickByChatListId(Long chatListId) {
        var appChatList = new AppChatList();
        appChatList.setChatListId(chatListId);
        appChatList.setStickFlag(AppChatListStick.STICK);
        return appChatListMapper.updateAppChatListByChatListId(appChatList);
    }

    /**
     * 取消置顶聊天列表
     *
     * @param chatListId 聊天列表主键
     * @return 影响行数
     */
    @Override
    public int unstickByChatListId(Long chatListId) {
        var appChatList = new AppChatList();
        appChatList.setChatListId(chatListId);
        appChatList.setStickFlag(AppChatListStick.UNSTICK);
        return appChatListMapper.updateAppChatListByChatListId(appChatList);
    }

    /**
     * 通过聊天列主键删除聊天列信息
     *
     * @param chatListId 聊天列主键
     * @return 影响行数
     */
    @Override
    public int deleteAppChatListByChatListId(Long chatListId) {
        return appChatListMapper.deleteAppChatListByChatListId(chatListId);
    }

    /**
     * 通过聊天列主键数组批量删除聊天列信息
     *
     * @param chatListIds 聊天列主键数组
     * @return 影响行数
     */
    @Override
    public int deleteAppChatListByChatListIds(Long[] chatListIds) {
        return appChatListMapper.deleteAppChatListByChatListIds(chatListIds);
    }

}

package com.tang.app.mapper;

import java.util.List;

import com.tang.app.entity.AppFriend;

/**
 * 用户好友数据访问层
 *
 * @author Tang
 */
public interface AppFriendMapper {

    /**
     * 查询用户好友列表
     *
     * @param appFriend 用户好友对象
     * @return 用户好友列表
     */
    List<AppFriend> selectAppFriendList(AppFriend appFriend);

    /**
     * 通过用户好友主键查询用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 用户好友信息
     */
    AppFriend selectAppFriendByUserFriendId(Long userFriendId);

    /**
     * 新增用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    int insertAppFriend(AppFriend appFriend);

    /**
     * 通过用户好友主键修改用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    int updateAppFriendByUserFriendId(AppFriend appFriend);

    /**
     * 通过用户好友主键删除用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 影响行数
     */
    int deleteAppFriendByUserFriendId(Long userFriendId);

    /**
     * 通过用户好友主键数组批量删除用户好友信息
     *
     * @param userFriendIds 用户好友主键数组
     * @return 影响行数
     */
    int deleteAppFriendByUserFriendIds(Long[] userFriendIds);

}

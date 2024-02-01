package com.tang.app.service.impl

import org.springframework.stereotype.Service

import com.tang.app.entity.AppFriend
import com.tang.app.mapper.AppFriendMapper
import com.tang.app.service.AppFriendService
import com.tang.commons.utils.SecurityUtils

/**
 * 用户好友业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class AppFriendServiceImpl(private val appFriendMapper: AppFriendMapper) : AppFriendService {

    /**
     * 查询用户好友列表
     *
     * @param appFriend 用户好友对象
     * @return 用户好友列表
     */
    override fun selectAppFriendList(appFriend: AppFriend): List<AppFriend> {
        appFriend.userId = SecurityUtils.getUser().userId
        return appFriendMapper.selectAppFriendList(appFriend)
    }

    /**
     * 通过用户好友主键查询用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 用户好友信息
     */
    override fun selectAppFriendByUserFriendId(userFriendId: Long): AppFriend? {
        return appFriendMapper.selectAppFriendByUserFriendId(userFriendId)
    }

    /**
     * 新增用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    override fun insertAppFriend(appFriend: AppFriend): Int {
        return appFriendMapper.insertAppFriend(appFriend)
    }

    /**
     * 通过用户好友主键修改用户好友信息
     *
     * @param appFriend 用户好友信息
     * @return 影响行数
     */
    override fun updateAppFriendByUserFriendId(appFriend: AppFriend): Int {
        return appFriendMapper.updateAppFriendByUserFriendId(appFriend)
    }

    /**
     * 通过用户好友主键删除用户好友信息
     *
     * @param userFriendId 用户好友主键
     * @return 影响行数
     */
    override fun deleteAppFriendByUserFriendId(userFriendId: Long): Int {
        return appFriendMapper.deleteAppFriendByUserFriendId(userFriendId)
    }

    /**
     * 通过用户好友主键数组批量删除用户好友信息
     *
     * @param userFriendIds 用户好友主键数组
     * @return 影响行数
     */
    override fun deleteAppFriendByUserFriendIds(userFriendIds: Array<Long>): Int {
        return appFriendMapper.deleteAppFriendByUserFriendIds(userFriendIds)
    }

}

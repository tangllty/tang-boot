package com.tang.app.service

import com.tang.app.entity.AppFriendApply

/**
 * 用户好友申请业务逻辑层接口
 *
 * @author Tang
 */
interface AppFriendApplyService {

    /**
     * 查询用户好友申请列表
     *
     * @param appFriendApply 用户好友申请对象
     * @return 用户好友申请列表
     */
    fun selectAppFriendApplyList(appFriendApply: AppFriendApply): List<AppFriendApply>

    /**
     * 通过用户好友申请主键查询用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 用户好友申请信息
     */
    fun selectAppFriendApplyByApplyId(applyId: Long): AppFriendApply?

    /**
     * 新增用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    fun insertAppFriendApply(appFriendApply: AppFriendApply): Int

    /**
     * 通过用户好友申请主键修改用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    fun updateAppFriendApplyByApplyId(appFriendApply: AppFriendApply): Int

    /**
     * 同意好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    fun acceptAppFriendApply(uniqueId: Long): Int

    /**
     * 拒绝好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    fun declineAppFriendApply(uniqueId: Long): Int

    /**
     * 通过用户好友申请主键删除用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 影响行数
     */
    fun deleteAppFriendApplyByApplyId(applyId: Long): Int

    /**
     * 通过用户好友申请主键数组批量删除用户好友申请信息
     *
     * @param applyIds 用户好友申请主键数组
     * @return 影响行数
     */
    fun deleteAppFriendApplyByApplyIds(applyIds: Array<Long>): Int

}

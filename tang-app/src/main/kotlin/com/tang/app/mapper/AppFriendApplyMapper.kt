package com.tang.app.mapper

import com.tang.app.entity.AppFriendApply
import org.apache.ibatis.annotations.Param

/**
 * 用户好友申请数据访问层
 *
 * @author Tang
 */
interface AppFriendApplyMapper {

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
     * 通过用户好友申请唯一标识修改用户好友申请状态
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    fun updateAppFriendApplyStatusByUniqueId(@Param("uniqueId") uniqueId: Long, @Param("status") status: String): Int

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

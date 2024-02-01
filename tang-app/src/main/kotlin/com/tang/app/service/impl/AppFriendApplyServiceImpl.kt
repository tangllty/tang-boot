package com.tang.app.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.tang.app.constants.AppFriendApplyStatus
import com.tang.app.entity.AppFriend
import com.tang.app.entity.AppFriendApply
import com.tang.app.mapper.AppFriendApplyMapper
import com.tang.app.mapper.AppFriendMapper
import com.tang.app.service.AppFriendApplyService
import com.tang.commons.utils.SecurityUtils
import com.tang.commons.utils.id.IdUtils

/**
 * 用户好友申请业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
class AppFriendApplyServiceImpl(
    private val appFriendApplyMapper: AppFriendApplyMapper,
    private val appFriendMapper: AppFriendMapper
) : AppFriendApplyService {

    /**
     * 查询用户好友申请列表
     *
     * @param appFriendApply 用户好友申请对象
     * @return 用户好友申请列表
     */
    override fun selectAppFriendApplyList(appFriendApply: AppFriendApply): List<AppFriendApply> {
        appFriendApply.userId = SecurityUtils.getUser().userId
        return appFriendApplyMapper.selectAppFriendApplyList(appFriendApply)
    }

    /**
     * 通过用户好友申请主键查询用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 用户好友申请信息
     */
    override fun selectAppFriendApplyByApplyId(applyId: Long): AppFriendApply? {
        return appFriendApplyMapper.selectAppFriendApplyByApplyId(applyId)
    }

    /**
     * 新增用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun insertAppFriendApply(appFriendApply: AppFriendApply): Int {
        val requestorId = SecurityUtils.getUser().userId
        val uniqueId = IdUtils.snowflake()
        appFriendApply.requestorId = requestorId
        appFriendApply.uniqueId = uniqueId
        val rows = appFriendApplyMapper.insertAppFriendApply(appFriendApply)
        if (rows <= 0) {
            return rows
        }
        val otherAppFriendApply = AppFriendApply()
        otherAppFriendApply.requestorId = requestorId
        otherAppFriendApply.uniqueId = uniqueId
        otherAppFriendApply.userId = appFriendApply.friendId
        otherAppFriendApply.friendId = appFriendApply.userId
        otherAppFriendApply.reason = appFriendApply.reason
        otherAppFriendApply.applyType = appFriendApply.applyType
        appFriendApplyMapper.insertAppFriendApply(otherAppFriendApply)
        return rows
    }

    /**
     * 通过用户好友申请主键修改用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    override fun updateAppFriendApplyByApplyId(appFriendApply: AppFriendApply): Int {
        return appFriendApplyMapper.updateAppFriendApplyByApplyId(appFriendApply)
    }

    /**
     * 同意好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun acceptAppFriendApply(uniqueId: Long): Int {
        val rows = appFriendApplyMapper.updateAppFriendApplyStatusByUniqueId(uniqueId, AppFriendApplyStatus.ACCEPTED)
        if (rows <= 0) {
            return rows
        }
        val appFriendApply = AppFriendApply()
        appFriendApply.uniqueId = uniqueId
        val list = appFriendApplyMapper.selectAppFriendApplyList(appFriendApply)
        list.forEach {
            val appFriend = AppFriend()
            appFriend.userId = it.userId
            appFriend.friendId = it.friendId
            appFriend.uniqueId = it.uniqueId
            appFriend.remark = it.remark
            appFriendMapper.insertAppFriend(appFriend)
        }
        return rows
    }

    /**
     * 拒绝好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    override fun declineAppFriendApply(uniqueId: Long): Int {
        return appFriendApplyMapper.updateAppFriendApplyStatusByUniqueId(uniqueId, AppFriendApplyStatus.DECLINED)
    }

    /**
     * 通过用户好友申请主键删除用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 影响行数
     */
    override fun deleteAppFriendApplyByApplyId(applyId: Long): Int {
        return appFriendApplyMapper.deleteAppFriendApplyByApplyId(applyId)
    }

    /**
     * 通过用户好友申请主键数组批量删除用户好友申请信息
     *
     * @param applyIds 用户好友申请主键数组
     * @return 影响行数
     */
    override fun deleteAppFriendApplyByApplyIds(applyIds: Array<Long>): Int {
        return appFriendApplyMapper.deleteAppFriendApplyByApplyIds(applyIds)
    }

}

package com.tang.app.service;

import java.util.List;

import com.tang.app.entity.AppFriendApply;

/**
 * 用户好友申请业务逻辑层接口
 *
 * @author Tang
 */
public interface AppFriendApplyService {

    /**
     * 查询用户好友申请列表
     *
     * @param appFriendApply 用户好友申请对象
     * @return 用户好友申请列表
     */
    List<AppFriendApply> selectAppFriendApplyList(AppFriendApply appFriendApply);

    /**
     * 通过用户好友申请主键查询用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 用户好友申请信息
     */
    AppFriendApply selectAppFriendApplyByApplyId(Long applyId);

    /**
     * 新增用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    int insertAppFriendApply(AppFriendApply appFriendApply);

    /**
     * 通过用户好友申请主键修改用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    int updateAppFriendApplyByApplyId(AppFriendApply appFriendApply);

    /**
     * 同意好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    int acceptAppFriendApply(Long uniqueId);

    /**
     * 拒绝好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    int declineAppFriendApply(Long uniqueId);

    /**
     * 通过用户好友申请主键删除用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 影响行数
     */
    int deleteAppFriendApplyByApplyId(Long applyId);

    /**
     * 通过用户好友申请主键数组批量删除用户好友申请信息
     *
     * @param applyIds 用户好友申请主键数组
     * @return 影响行数
     */
    int deleteAppFriendApplyByApplyIds(Long[] applyIds);

}

package com.tang.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.app.constants.AppFriendApplyStatus;
import com.tang.app.entity.AppFriend;
import com.tang.app.entity.AppFriendApply;
import com.tang.app.mapper.AppFriendApplyMapper;
import com.tang.app.mapper.AppFriendMapper;
import com.tang.app.service.AppFriendApplyService;
import com.tang.commons.utils.SecurityUtils;
import com.tang.commons.utils.id.IdUtils;

/**
 * 用户好友申请业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
public class AppFriendApplyServiceImpl implements AppFriendApplyService {

    private final AppFriendApplyMapper appFriendApplyMapper;

    private final AppFriendMapper appFriendMapper;

    public AppFriendApplyServiceImpl(AppFriendApplyMapper appFriendApplyMapper, AppFriendMapper appFriendMapper) {
        this.appFriendApplyMapper = appFriendApplyMapper;
        this.appFriendMapper = appFriendMapper;
    }

    /**
     * 查询用户好友申请列表
     *
     * @param appFriendApply 用户好友申请对象
     * @return 用户好友申请列表
     */
    @Override
    public List<AppFriendApply> selectAppFriendApplyList(AppFriendApply appFriendApply) {
        appFriendApply.setUserId(SecurityUtils.getUser().getUserId());
        return appFriendApplyMapper.selectAppFriendApplyList(appFriendApply);
    }

    /**
     * 通过用户好友申请主键查询用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 用户好友申请信息
     */
    @Override
    public AppFriendApply selectAppFriendApplyByApplyId(Long applyId) {
        return appFriendApplyMapper.selectAppFriendApplyByApplyId(applyId);
    }

    /**
     * 新增用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAppFriendApply(AppFriendApply appFriendApply) {
        var requestorId = SecurityUtils.getUser().getUserId();
        var uniqueId = IdUtils.snowflake();
        appFriendApply.setRequestorId(requestorId);
        appFriendApply.setUniqueId(uniqueId);
        int rows = appFriendApplyMapper.insertAppFriendApply(appFriendApply);
        if (rows > 0) {
            AppFriendApply otherAppFriendApply = new AppFriendApply();
            otherAppFriendApply.setRequestorId(requestorId);
            otherAppFriendApply.setUniqueId(uniqueId);
            otherAppFriendApply.setUserId(appFriendApply.getFriendId());
            otherAppFriendApply.setFriendId(appFriendApply.getUserId());
            otherAppFriendApply.setReason(appFriendApply.getReason());
            otherAppFriendApply.setApplyType(appFriendApply.getApplyType());
            appFriendApplyMapper.insertAppFriendApply(otherAppFriendApply);
        }
        return rows;
    }

    /**
     * 通过用户好友申请主键修改用户好友申请信息
     *
     * @param appFriendApply 用户好友申请信息
     * @return 影响行数
     */
    @Override
    public int updateAppFriendApplyByApplyId(AppFriendApply appFriendApply) {
        return appFriendApplyMapper.updateAppFriendApplyByApplyId(appFriendApply);
    }

    /**
     * 同意好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int acceptAppFriendApply(Long uniqueId) {
        int rows = appFriendApplyMapper.updateAppFriendApplyStatusByUniqueId(uniqueId, AppFriendApplyStatus.ACCEPTED);
        if (rows > 0) {
            var appFriendApply = new AppFriendApply();
            appFriendApply.setUniqueId(uniqueId);
            appFriendApplyMapper.selectAppFriendApplyList(appFriendApply).forEach(item -> {
                var appFriend = new AppFriend();
                appFriend.setUserId(item.getUserId());
                appFriend.setFriendId(item.getFriendId());
                appFriend.setUniqueId(item.getUniqueId());
                appFriend.setRemark(item.getRemark());
                appFriendMapper.insertAppFriend(appFriend);
            });
        }
        return rows;
    }

    /**
     * 拒绝好友请求
     *
     * @param uniqueId 用户好友申请唯一标识
     * @return 影响行数
     */
    @Override
    public int declineAppFriendApply(Long uniqueId) {
        return appFriendApplyMapper.updateAppFriendApplyStatusByUniqueId(uniqueId, AppFriendApplyStatus.DECLINED);
    }

    /**
     * 通过用户好友申请主键删除用户好友申请信息
     *
     * @param applyId 用户好友申请主键
     * @return 影响行数
     */
    @Override
    public int deleteAppFriendApplyByApplyId(Long applyId) {
        return appFriendApplyMapper.deleteAppFriendApplyByApplyId(applyId);
    }

    /**
     * 通过用户好友申请主键数组批量删除用户好友申请信息
     *
     * @param applyIds 用户好友申请主键数组
     * @return 影响行数
     */
    @Override
    public int deleteAppFriendApplyByApplyIds(Long[] applyIds) {
        return appFriendApplyMapper.deleteAppFriendApplyByApplyIds(applyIds);
    }

}

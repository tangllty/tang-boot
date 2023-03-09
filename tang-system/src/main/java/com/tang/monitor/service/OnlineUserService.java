package com.tang.monitor.service;

import java.util.List;

import com.tang.monitor.entity.OnlineUser;

/**
 * 在线用户服务接口
 *
 * @author Tang
 */
public interface OnlineUserService {

    /**
     * 查询在线用户列表
     *
     * @param onlineUser 在线用户对象
     * @return 在线用户列表
     */
    List<OnlineUser> selectOnlineUserList(OnlineUser onlineUser);

    /**
     * 查询在线用户
     *
     * @param token 唯一凭证
     * @return 在线用户
     */
    OnlineUser selectOnlineUserByToken(String token);

    /**
     * 删除在线用户
     *
     * @param token 唯一凭证
     * @return 结果
     */
    boolean deleteOnlineUserByToken(String token);

}

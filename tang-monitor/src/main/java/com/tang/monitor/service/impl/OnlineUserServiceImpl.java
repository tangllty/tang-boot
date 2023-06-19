package com.tang.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.tang.commons.core.model.UserModel;
import com.tang.commons.utils.RedisUtils;
import com.tang.monitor.entity.OnlineUser;
import com.tang.monitor.service.OnlineUserService;

import static com.tang.commons.constants.CachePrefix.LOGIN_TOKENS;

/**
 * 在线用户服务实现
 *
 * @author Tang
 */
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    private final RedisUtils redisUtils;

    public OnlineUserServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 查询在线用户列表
     *
     * @param onlineUser 在线用户对象
     * @return 在线用户列表
     */
    @Override
    public List<OnlineUser> selectOnlineUserList(OnlineUser onlineUser) {
        var keys = redisUtils.keys(LOGIN_TOKENS + "*");
        var onlineUserList = new ArrayList<OnlineUser>(keys.size());
        keys.forEach(key -> {
            var userModel = (UserModel) redisUtils.get(key);
            onlineUserList.add(userModelConvertOnlineUser(userModel));
        });
        var username = onlineUser.getUsername();
        if (StringUtils.isNotBlank(username)) {
            onlineUserList.removeIf(user -> !user.getUsername().contains(username));
        }
        var nickname = onlineUser.getNickname();
        if (StringUtils.isNotBlank(nickname)) {
            onlineUserList.removeIf(user -> !user.getNickname().contains(nickname));
        }
        return onlineUserList;
    }

    /**
     * 查询在线用户
     *
     * @param token 唯一凭证
     * @return 在线用户
     */
    @Override
    public OnlineUser selectOnlineUserByToken(String token) {
        var userModel = (UserModel) redisUtils.get(LOGIN_TOKENS + token);
        return userModelConvertOnlineUserDetail(userModel);
    }

    /**
     * 删除在线用户
     *
     * @param token 唯一凭证
     * @return 结果
     */
    @Override
    public boolean deleteOnlineUserByToken(String token) {
        return redisUtils.delete(LOGIN_TOKENS + token);
    }

    private OnlineUser userModelConvertOnlineUser(@NonNull UserModel userModel) {
        var onlineUser = new OnlineUser();
        var user = userModel.getUser();
        if (Objects.nonNull(user)) {
            onlineUser.setUsername(user.getUsername());
            onlineUser.setNickname(user.getNickname());
            var dept = user.getDept();
            if (Objects.nonNull(dept)) {
                onlineUser.setDeptName(dept.getDeptName());
            }
        }
        onlineUser.setToken(userModel.getToken());
        onlineUser.setIp(userModel.getIp());
        onlineUser.setLocation(userModel.getLocation());
        onlineUser.setBrowser(userModel.getBrowser());
        onlineUser.setOs(userModel.getOs());
        onlineUser.setLoginTime(userModel.getLoginTime());
        return onlineUser;
    }

    private OnlineUser userModelConvertOnlineUserDetail(@NonNull UserModel userModel) {
        var onlineUser = new OnlineUser();
        var user = userModel.getUser();
        if (Objects.nonNull(user)) {
            onlineUser.setUsername(user.getUsername());
            onlineUser.setNickname(user.getNickname());
            onlineUser.setEmail(user.getEmail());
            onlineUser.setPhone(user.getPhone());
            onlineUser.setGender(user.getGender());
            var dept = user.getDept();
            if (Objects.nonNull(dept)) {
                onlineUser.setDeptName(dept.getDeptName());
            }
        }
        onlineUser.setToken(userModel.getToken());
        onlineUser.setIp(userModel.getIp());
        onlineUser.setLocation(userModel.getLocation());
        onlineUser.setMobile(userModel.isMobile());
        onlineUser.setBrowser(userModel.getBrowser());
        onlineUser.setVersion(userModel.getVersion());
        onlineUser.setPlatform(userModel.getPlatform());
        onlineUser.setOs(userModel.getOs());
        onlineUser.setOsVersion(userModel.getOsVersion());
        onlineUser.setEngine(userModel.getEngine());
        onlineUser.setEngineVersion(userModel.getEngineVersion());
        onlineUser.setLoginType(userModel.getLoginType());
        onlineUser.setLoginTime(userModel.getLoginTime());
        onlineUser.setExpireTime(userModel.getExpireTime());
        return onlineUser;
    }

}

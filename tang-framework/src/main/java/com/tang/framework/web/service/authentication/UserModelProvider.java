package com.tang.framework.web.service.authentication;

import com.tang.commons.core.model.UserModel;
import com.tang.system.entity.SysUser;

/**
 * 用户模型提供者
 *
 * @author Tang
 */
public interface UserModelProvider {

    /**
     * 创建用户模型
     *
     * @param user 用户信息
     * @param password 明文密码
     * @param loginType 登陆类型
     * @return 用户模型
     */
    UserModel createUserModel(SysUser user, String password, String loginType);

}

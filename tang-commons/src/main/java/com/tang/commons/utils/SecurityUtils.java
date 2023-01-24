package com.tang.commons.utils;

import com.tang.commons.core.model.SysUserModel;
import com.tang.commons.core.model.UserModel;

/**
 * Spring Security 工具类
 *
 * @author Tang
 */
public class SecurityUtils {

    /**
     * 创建登陆用户模型
     *
     * @param userModel 用户模型
     * @return 登陆用户模型
     */
    public static UserModel createUserModel(SysUserModel userModel) {
        return new UserModel(userModel);
    }

}

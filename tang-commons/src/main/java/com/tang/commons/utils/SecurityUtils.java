package com.tang.commons.utils;

import java.util.Map;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tang.commons.model.SysUserModel;
import com.tang.commons.model.UserModel;

/**
 * Spring Security 工具类
 *
 * @author Tang
 */
public class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 管理员标识
     */
    public static final String ADMIN_ROLE_KEY = "admin";

    /**
     * 所有权限
     */
    public static final String ALL_PERMISSIONS = "*:*:*";

    /**
     * 获取 SecurityContext
     */
    private static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 获取 Authentication
     */
    private static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    /**
     * 获取 Principal
     */
    private static Object getPrincipal() {
        return getAuthentication().getPrincipal();
    }

    /**
     * 获取登陆用户信息
     *
     * @return 登陆用户信息
     */
    public static UserModel getUserModel() {
        if (!isAuthenticated()) {
            throw new SecurityException("用户未登录");
        }
        return (UserModel) getPrincipal();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static SysUserModel getUser() {
        return getUserModel().getUser();
    }

    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    public static Set<String> getRoles() {
        return getUserModel().getRoles();
    }

    /**
     * 获取角色名称信息
     *
     * @return 角色名称信息
     */
    public static Set<String> getRoleNames() {
        return getUserModel().getRoleNames();
    }

    /**
     * 获取权限信息
     *
     * @return 权限信息
     */
    public static Set<String> getPermissions() {
        return getUserModel().getPermissions();
    }

    /**
     * 获取字典权限信息
     *
     * @return 字典权限信息
     */
    public static Map<String, Set<String>> getDictPermissions() {
        return getUserModel().getDictPermissions();
    }

    /**
     * 判断是否为管理员
     *
     * @return 结果
     */
    public static boolean isAdmin() {
        return getRoles().contains(ADMIN_ROLE_KEY) && getPermissions().contains(ALL_PERMISSIONS);
    }

    /**
     * 判断是否登陆
     *
     * @return 结果
     */
    public static boolean isAuthenticated() {
        return getAuthentication() != null
            && getAuthentication().isAuthenticated()
            && !"anonymousUser".equals(getPrincipal())
            && getPrincipal() instanceof UserModel;
    }

    /**
     * BCryptPasswordEncoder 生成密文密码
     *
     * @param rawPassword 明文密码
     * @return 密文密码
     */
    public static String encryptPassword(String rawPassword) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证明文密码是否与密文密码匹配
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 密文密码
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}

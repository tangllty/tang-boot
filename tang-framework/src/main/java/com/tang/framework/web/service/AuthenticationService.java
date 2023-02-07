package com.tang.framework.web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tang.commons.core.model.SysDeptModel;
import com.tang.commons.core.model.SysUserModel;
import com.tang.commons.core.model.UserModel;
import com.tang.commons.exception.user.PasswordMismatchException;
import com.tang.commons.utils.SecurityUtils;
import com.tang.system.entity.SysUser;
import com.tang.system.service.SysMenuService;
import com.tang.system.service.SysRoleService;

import java.util.Set;

/**
 * 用户权限
 *
 * @author Tang
 */
@Component
public class AuthenticationService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    /**
     * 创建登陆用户模型
     *
     * @param user 用户信息
     * @return 登陆用户模型
     */
    public UserModel createUserModel(SysUser user, String password, String loginType) {
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            throw new PasswordMismatchException("密码错误");
        }

        var userModel = new UserModel();
        // 用户对象
        var sysUserModel = new SysUserModel();
        var sysDeptModel = new SysDeptModel();
        BeanUtils.copyProperties(user, sysUserModel);
        BeanUtils.copyProperties(user.getDept(), sysDeptModel);
        sysUserModel.setDept(sysDeptModel);
        // 角色集合
        var roles = roleService.getRolesByUserId(user.getUserId());
        // 权限集合
        var permissions = menuService.getPermissionsByUserId(user.getUserId());
        if (roles.contains(SecurityUtils.ADMIN_ROLE_KEY)) {
            permissions = Set.of(SecurityUtils.ALL_PERMISSIONS);
        }
        // 将信息放进登陆用户模型
        userModel.setLoginType(loginType);
        userModel.setUser(sysUserModel);
        userModel.setRoles(roles);
        userModel.setPermissions(permissions);
        return userModel;
    }

}
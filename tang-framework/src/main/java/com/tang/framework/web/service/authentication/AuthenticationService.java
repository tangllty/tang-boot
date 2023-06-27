package com.tang.framework.web.service.authentication;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.tang.commons.constants.Status;
import com.tang.commons.exception.status.DeletedException;
import com.tang.commons.exception.status.DisabledException;
import com.tang.commons.exception.user.PasswordMismatchException;
import com.tang.commons.exception.user.UserNotFoundException;
import com.tang.commons.model.SysDeptModel;
import com.tang.commons.model.SysUserModel;
import com.tang.commons.model.UserModel;
import com.tang.commons.utils.SecurityUtils;
import com.tang.framework.web.service.TokenService;
import com.tang.system.entity.SysUser;
import com.tang.system.service.SysMenuService;
import com.tang.system.service.SysRoleService;
import com.tang.system.service.log.SysLogLoginService;

/**
 * 用户权限
 *
 * @author Tang
 */
@Component
public class AuthenticationService implements UserModelProvider {

    private final SysRoleService roleService;

    private final SysMenuService menuService;

    private final SysLogLoginService logLoginService;

    private final TokenService tokenService;

    public AuthenticationService(SysRoleService roleService, SysMenuService menuService, SysLogLoginService logLoginService, TokenService tokenService) {
        this.roleService = roleService;
        this.menuService = menuService;
        this.logLoginService = logLoginService;
        this.tokenService = tokenService;
    }

    /**
     * 创建用户模型
     *
     * @param user 用户信息
     * @param password 明文密码
     * @param loginType 登陆类型
     * @return 用户模型
     */
    public UserModel createUserModel(SysUser user, String password, String account, String loginType) {
        try {
            if (Objects.isNull(user)) {
                throw new UserNotFoundException("用户不存在");
            }
            if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
                throw new PasswordMismatchException("密码错误");
            }
            if (user.getStatus().equals(Status.DISABLED)) {
                throw new DisabledException("账号已停用");
            }
            if (user.getDelFlag().equals(Status.DELETED)) {
                throw new DeletedException("账号已删除");
            }
        } catch (RuntimeException e) {
            logLoginService.recordLoginInfo(Objects.isNull(user) ? null : user.getUserId(), tokenService.getUserAgent(), account, loginType, false, e.getMessage());
            throw e;
        }

        var userModel = new UserModel();
        // 用户对象
        var sysUserModel = new SysUserModel();
        var sysDeptModel = new SysDeptModel();
        BeanUtils.copyProperties(user, sysUserModel);
        BeanUtils.copyProperties(user.getDept(), sysDeptModel);
        sysUserModel.setDept(sysDeptModel);
        sysUserModel.setRoleIds(roleService.getRoleIdsByUserId(user.getUserId()));
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

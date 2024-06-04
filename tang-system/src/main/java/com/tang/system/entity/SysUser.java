package com.tang.system.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tang.commons.annotation.poi.Excel;
import com.tang.commons.base.entity.BaseEntity;
import com.tang.commons.enumeration.poi.CellType;
import com.tang.commons.enumeration.poi.Type;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 用户表 sys_user 实体类
 *
 * @author Tang
 */
public class SysUser extends BaseEntity {

    @java.io.Serial
    private static final long serialVersionUID = 7083199038942669698L;

    /**
     * 用户ID
     */
    @Excel(name = "用户编号", cellType = CellType.NUMBER, type = Type.EXPORT, width = 8)
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", cellType = CellType.NUMBER, width = 8)
    @NotNull(message = "部门不能为空")
    @Min(value = 1, message = "部门编号不能小于1")
    @Max(value = Long.MAX_VALUE, message = "部门编号不能超过" + Long.MAX_VALUE)
    private Long deptId;

    /**
     * 用户账号
     */
    @Excel(value = "用户账号", width = 12)
    @Length(min = 4, max = 32, message = "用户名长度应在 4 到 32 之间")
    private String username;

    /**
     * 昵称
     */
    @Excel("昵称")
    @Length(min = 4, max = 32, message = "昵称长度应在 4 到 32 之间")
    private String nickname;

    /**
     * 邮箱
     */
    @Excel(value = "邮箱", width = 16)
    @Email(message = "邮箱格式不正确")
    @Length(min = 4, max = 64, message = "邮箱长度应在 4 到 64 之间")
    private String email;

    /**
     * 手机号码
     */
    @Excel(value = "手机号码", width = 12)
    private String phone;

    /**
     * 性别
     */
    @Excel(name = "性别", dictType = "sys_user_gender")
    private String gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @Length(min = 4, max = 32, message = "密码长度应在 4 到 32 之间")
    private String password;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 帐号状态
     */
    @Excel(name = "帐号状态", dictType = "sys_status")
    private String status;

    /**
     * 删除标志
     */
    @Excel(name = "删除标志", dictType = "sys_del_flag")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(value = "最后登录IP", width = 14)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", cellType = CellType.DATE, width = 18)
    private LocalDateTime loginDate;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 角色主键集合
     */
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

}

package com.tang.system.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tang.commons.annotation.poi.Excel;
import com.tang.commons.annotation.poi.Excel.CellType;
import com.tang.commons.annotation.poi.Excel.Type;
import com.tang.commons.core.base.entity.BaseEntity;

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
    @Excel(name = "用户ID", cellType = CellType.NUMBER, type = Type.EXPORT)
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID", cellType = CellType.NUMBER)
    private Long deptId;

    /**
     * 用户账号
     */
    @Excel("用户账号")
    private String username;

    /**
     * 昵称
     */
    @Excel("昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Excel("邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel("手机号码")
    private String phone;

    /**
     * 性别
     */
    @Excel("性别")
    private String gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态
     */
    @Excel("帐号状态")
    private String status;

    /**
     * 删除标志
     */
    @Excel("删除标志")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", cellType = CellType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDate;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 角色主键集合
     */
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

package com.tang.system.service.impl.log;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tang.commons.model.UserModel;
import com.tang.commons.utils.SecurityUtils;
import com.tang.system.entity.log.SysLogLogin;
import com.tang.system.mapper.log.SysLogLoginMapper;
import com.tang.system.service.log.SysLogLoginService;

/**
 * 登陆日志业务逻辑层接口实现
 *
 * @author Tang
 */
@Service
public class SysLogLoginServiceImpl implements SysLogLoginService {

    private final SysLogLoginMapper sysLogLoginMapper;

    public SysLogLoginServiceImpl(SysLogLoginMapper sysLogLoginMapper) {
        this.sysLogLoginMapper = sysLogLoginMapper;
    }

    /**
     * 查询登陆日志列表
     *
     * @param sysLogLogin 登陆日志对象
     * @return 登陆日志列表
     */
    @Override
    public List<SysLogLogin> selectSysLogLoginList(SysLogLogin sysLogLogin) {
        return sysLogLoginMapper.selectSysLogLoginList(sysLogLogin);
    }

    /**
     * 查询用户登陆日志列表
     *
     * @param logLogin 登陆日志对象
     * @return 登陆日志列表
     */
    @Override
    public List<SysLogLogin> selectSysLogLoginListByUser(SysLogLogin logLogin) {
        logLogin.setUserId(SecurityUtils.getUser().getUserId());
        return sysLogLoginMapper.selectSysLogLoginListByUser(logLogin);
    }

    /**
     * 通过登陆日志主键查询登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 登陆日志信息
     */
    @Override
    public SysLogLogin selectSysLogLoginByLoginId(Long loginId) {
        return sysLogLoginMapper.selectSysLogLoginByLoginId(loginId);
    }

    /*
     * 查询用户访问量
     *
     * @return 用户访问量
     */
    @Override
    public List<Long> selectUserVisit() {
        var userId = SecurityUtils.getUser().getUserId();
        var now = LocalDate.now();
        var monday = now.with(DayOfWeek.MONDAY);
        var sunday = monday.plusDays(6);
        var startDate = monday;
        var list = sysLogLoginMapper.selectUserVisit(userId, startDate);
        var listGrouped = list.stream()
            .collect(Collectors.groupingBy(logLogin -> {
                var loginTime = logLogin.getLoginTime();
                return LocalDate.of(loginTime.getYear(), loginTime.getMonth(), loginTime.getDayOfMonth());
            }));
        var userVisitList = new ArrayList<Long>();
        while (startDate.isBefore(sunday)) {
            var count = listGrouped.get(startDate);
            if (count != null) {
                userVisitList.add(count.stream().count());
            } else {
                userVisitList.add(0L);
            }
            startDate = startDate.plusDays(1);
        }
        return userVisitList;
    }

    /**
     * 新增登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    @Override
    public int insertSysLogLogin(SysLogLogin sysLogLogin) {
        return sysLogLoginMapper.insertSysLogLogin(sysLogLogin);
    }

    /**
     * 记录登陆信息
     *
     * @param userId   用户ID
     * @param userModel 用户信息
     * @param account 登陆账号
     * @param loginType 登陆类型
     * @param success 是否成功
     * @param message 返回消息
     */
    @Override
    public void recordLoginInfo(Long userId, UserModel userModel, String account, String loginType, boolean success, String message) {
        var logLogin = new SysLogLogin();
        logLogin.setUserId(userId);
        logLogin.setAccount(account);
        logLogin.setLoginType(loginType);
        logLogin.setOs(userModel.getOs());
        logLogin.setBrowser(userModel.getBrowser());
        logLogin.setIp(userModel.getIp());
        logLogin.setLocation(userModel.getLocation());
        logLogin.setLoginTime(userModel.getLoginTime());
        logLogin.setSuccess(success ? "成功" : "失败");
        logLogin.setMessage(message);
        sysLogLoginMapper.insertSysLogLogin(logLogin);
    }

    /**
     * 通过登陆日志主键修改登陆日志信息
     *
     * @param sysLogLogin 登陆日志信息
     * @return 影响行数
     */
    @Override
    public int updateSysLogLoginByLoginId(SysLogLogin sysLogLogin) {
        return sysLogLoginMapper.updateSysLogLoginByLoginId(sysLogLogin);
    }

    /**
     * 通过登陆日志主键删除登陆日志信息
     *
     * @param loginId 登陆日志主键
     * @return 影响行数
     */
    @Override
    public int deleteSysLogLoginByLoginId(Long loginId) {
        return sysLogLoginMapper.deleteSysLogLoginByLoginId(loginId);
    }

    /**
     * 通过登陆日志主键数组批量删除登陆日志信息
     *
     * @param loginIds 登陆日志主键数组
     * @return 影响行数
     */
    @Override
    public int deleteSysLogLoginByLoginIds(Long[] loginIds) {
        return sysLogLoginMapper.deleteSysLogLoginByLoginIds(loginIds);
    }

}

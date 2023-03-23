package com.tang.commons.utils.oshi;

/**
 * CPU 信息
 *
 * @author Tang
 */
public class CpuInfo {

    /**
     * CPU 名称
     */
    private String name;

    /**
     * CPU 核心数
     */
    private Integer cpuNum;

    /**
     * CPU 总的使用率
     */
    private String total;

    /**
     * CPU 系统使用率
     */
    private String sys;

    /**
     * CPU 用户使用率
     */
    private String user;

    /**
     * CPU 当前等待率
     */
    private String wait;

    /**
     * CPU 当前空闲率
     */
    private String free;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWait() {
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

}

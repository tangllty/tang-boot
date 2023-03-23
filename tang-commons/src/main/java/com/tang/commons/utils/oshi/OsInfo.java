package com.tang.commons.utils.oshi;

/**
 * 系统信息
 *
 * @author Tang
 */
public class OsInfo {

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统架构
     */
    private String arch;

    /**
     * 系统版本
     */
    private String version;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}

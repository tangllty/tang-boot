package com.tang.commons.utils.oshi;

/**
 * 分区信息
 *
 * @author Tang
 */
public class FileStoresInfo {

    /**
     * 分区名
     */
    private String name;

    /**
     * 文件系统
     */
    private String type;

    /**
     * 挂载点
     */
    private String mountPoint;

    /**
     * 总大小
     */
    private String total;

    /**
     * 已用大小
     */
    private String used;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 资源利用率
     */
    private String usage;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

}

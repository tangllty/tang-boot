package com.tang.commons.utils.oshi;

/**
 * Java 信息
 *
 * @author Tang
 */
public class JavaInfo {

    /**
     * Java 路径
     */
    private String javaHome;

    /**
     * Java 版本
     */
    private String javaVersion;

    /**
     * Jvm 名称
     */
    private String jvmName;

    /**
     * 项目路径
     */
    private String projectHome;


    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getProjectHome() {
        return projectHome;
    }

    public void setProjectHome(String projectHome) {
        this.projectHome = projectHome;
    }

}

package com.tang.commons.enumeration;

/**
 * 登陆方式
 *
 * @author Tang
 */
public enum LoginType {

    /**
     * 用户名密码
     */
    USERNAME("username", "用户名密码"),

    /**
     * 邮箱密码
     */
    EMAIL("email", "邮箱密码");

    /**
     * 登陆方式
     */
    private final String name;

    /**
     * 登陆方式名称
     */
    private final String label;

    LoginType(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 根据 name 返回枚举类型
     *
     * @param name 登陆方式
     * @return LoginType 登陆方式名称
     */
    public static LoginType getLoginType(String name) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.getName().equals(name)) {
                return loginType;
            }
        }
        return null;
    }

}

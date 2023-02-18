package com.tang.commons.enumeration;

/**
 * 菜单类型
 *
 * @author Tang
 */
public enum MenuType {

    /**
     * 目录
     */
    DIRECTORY("D"),

    /**
     * 菜单
     */
    MENU("M"),

    /**
     * 按钮
     */
    BUTTON("B");

    private String name;

    private MenuType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

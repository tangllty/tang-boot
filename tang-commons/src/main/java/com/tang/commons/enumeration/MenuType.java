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

    private String menuType;

    private MenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return menuType;
    }

}

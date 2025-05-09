package com.tang.commons.domain.vo;

/**
 * 路由显示信息
 *
 * @author Tang
 */
public class MetaVo {

    /**
     * 路由标题
     */
    private String title;

    /**
     * 路由图标
     */
    private String icon;

    /**
     * 隐藏路由
     */
    private Boolean hidden;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

}

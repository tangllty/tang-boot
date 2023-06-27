package com.tang.commons.domain.vo;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author Tang
 */
public class RouteVo {

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由组件
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 路由显示信息
     */
    private MetaVo meta;

    /**
     * 子路由集合
     */
    List<RouteVo> children;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public MetaVo getMeta() {
        return meta;
    }

    public void setMeta(MetaVo meta) {
        this.meta = meta;
    }

    public List<RouteVo> getChildren() {
        return children;
    }

    public void setChildren(List<RouteVo> children) {
        this.children = children;
    }

}

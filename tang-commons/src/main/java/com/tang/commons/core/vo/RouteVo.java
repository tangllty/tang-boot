package com.tang.commons.core.vo;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author Tang
 */
public class RouteVo {

    private String name;

    private String path;

    private String component;

    private String redirect;

    private MetaVo meta;

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

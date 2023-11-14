package com.tang.commons.utils.tree;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 树结构实体类
 *
 * @author Tang
 */
public class DictTreeSelect implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -8016273580724150714L;

    /**
     * 节点ID
     */
    private String value;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DictTreeSelect> children;

    public DictTreeSelect() {
    }

    public DictTreeSelect(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<DictTreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<DictTreeSelect> children) {
        this.children = children;
    }

}

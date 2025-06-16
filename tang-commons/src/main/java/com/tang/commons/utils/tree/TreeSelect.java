package com.tang.commons.utils.tree;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 树结构实体类
 *
 * @author Tang
 */
public class TreeSelect<T> implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -5072364851437166728L;

    /**
     * 父节点ID
     */
    @JsonIgnore
    private T parentId;

    /**
     * 节点ID
     */
    private T value;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect<T>> children;

    public TreeSelect() {
    }

    public TreeSelect(T value, T parentId) {
        this.value = value;
        this.parentId = parentId;
    }

    public TreeSelect(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public TreeSelect(T parentId, T value, String label) {
        this.parentId = parentId;
        this.value = value;
        this.label = label;
    }

    public T getParentId() {
        return parentId;
    }

    public void setParentId(T parentId) {
        this.parentId = parentId;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect<T>> children) {
        this.children = children;
    }

}

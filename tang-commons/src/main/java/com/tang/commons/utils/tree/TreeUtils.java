package com.tang.commons.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 树结构工具类
 *
 * @author Tang
 */
public class TreeUtils {

    private TreeUtils() {
    }

    /**
     * 构建树结构
     *
     * @param treeSelectList 树结构集合
     * @param parentId 父节点ID
     * @return 树结构数据
     */
    public static <T> List<TreeSelect<T>> buildTreeWithRecursion(List<TreeSelect<T>> treeSelectList, T parentId) {
        if (Objects.isNull(treeSelectList)) {
            return new ArrayList<>();
        }
        if (treeSelectList.isEmpty()) {
            return treeSelectList;
        }
        var list = new ArrayList<TreeSelect<T>>();
        treeSelectList.forEach(treeSelect -> {
            if (Objects.equals(treeSelect.getParentId(), parentId)) {
                list.add(treeSelect);
                recursionTree(treeSelectList, treeSelect);
            }
        });
        return list;
    }

    /**
     * 获取子节点
     *
     * @param treeSelectList 树结构集合
     * @param parentTreeSelect 父节点对象
     */
    private static <T> void recursionTree(List<TreeSelect<T>> treeSelectList, TreeSelect<T> parentTreeSelect) {
        treeSelectList.forEach(treeSelect -> {
            if (Objects.equals(treeSelect.getParentId(), parentTreeSelect.getValue())) {
                List<TreeSelect<T>> children = parentTreeSelect.getChildren();
                if (Objects.isNull(children)) {
                    children = new ArrayList<>();
                    parentTreeSelect.setChildren(children);
                }
                children.add(treeSelect);
                recursionTree(treeSelectList, treeSelect);
            }
        });
    }

    /**
     * Map 构建树结构
     *
     * @param nodes 树结构集合
     * @param parentId 父节点ID
     * @return 树结构数据
     * @param <T> 节点ID类型
     */
    public static <T> List<TreeSelect<T>> buildTreeWithMap(List<TreeSelect<T>> nodes, T parentId) {
        var nodeMap = new HashMap<T, TreeSelect<T>>();
        var roots = new ArrayList<TreeSelect<T>>();
        for (var node : nodes) {
            nodeMap.put(node.getValue(), node);
        }
        for (var node : nodes) {
            if (Objects.equals(node.getParentId(), parentId)) {
                roots.add(node);
                continue;
            }
            var parent = nodeMap.get(node.getParentId());
            if (parent == null) {
                continue;
            }
            if (parent.getChildren() == null) {
                parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(node);
        }
        return roots;
    }

    /**
     * 分组构建树结构
     *
     * @param nodes 树结构集合
     * @param parentId 父节点ID
     * @return 树结构数据
     * @param <T> 节点ID类型
     */
    public static <T> List<TreeSelect<T>> buildTreeWithGrouping(List<TreeSelect<T>> nodes, T parentId) {
        var groupMap = new HashMap<T, List<TreeSelect<T>>>();
        var roots = new ArrayList<TreeSelect<T>>();
        for (var node : nodes) {
            if (Objects.equals(node.getParentId(), parentId)) {
                roots.add(node);
            } else {
                groupMap.computeIfAbsent(node.getParentId(), k -> new ArrayList<>()).add(node);
            }
        }
        for (var root : roots) {
            buildChildren(root, groupMap);
        }
        return roots;
    }

    private static <T> void buildChildren(TreeSelect<T> parent, Map<T, List<TreeSelect<T>>> groupMap) {
        var children = groupMap.get(parent.getValue());
        if (children == null || children.isEmpty()) {
            return;
        }
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }
        parent.getChildren().addAll(children);
        for (var child : children) {
            buildChildren(child, groupMap);
        }
    }

}

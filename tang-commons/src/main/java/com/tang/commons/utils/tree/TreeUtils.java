package com.tang.commons.utils.tree;

import java.util.ArrayList;
import java.util.List;
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
     * 默认父ID
     */
    private static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 构建树结构
     *
     * @param treeSelectList 树结构集合
     * @return 树结构数据
     */
    public static List<TreeSelect> buildTree(List<TreeSelect> treeSelectList) {
        return buildTree(treeSelectList, DEFAULT_PARENT_ID);
    }

    /**
     * 构建树结构
     *
     * @param treeSelectList 树结构集合
     * @param parentId 父节点ID
     * @return 树结构数据
     */
    public static List<TreeSelect> buildTree(List<TreeSelect> treeSelectList, Long parentId) {
        if (Objects.isNull(treeSelectList)) {
            return new ArrayList<>();
        }
        if (treeSelectList.isEmpty()) {
            return treeSelectList;
        }
        var list = new ArrayList<TreeSelect>();
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
    private static void recursionTree(List<TreeSelect> treeSelectList, TreeSelect parentTreeSelect) {
        treeSelectList.forEach(treeSelect -> {
            if (Objects.equals(treeSelect.getParentId(), parentTreeSelect.getValue())) {
                List<TreeSelect> children = parentTreeSelect.getChildren();
                if (Objects.isNull(children)) {
                    children = new ArrayList<>();
                    parentTreeSelect.setChildren(children);
                }
                children.add(treeSelect);
                recursionTree(treeSelectList, treeSelect);
            }
        });
    }

}

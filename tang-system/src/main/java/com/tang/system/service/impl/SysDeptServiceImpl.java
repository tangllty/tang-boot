package com.tang.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.utils.tree.TreeSelect;
import com.tang.commons.utils.tree.TreeUtils;
import com.tang.system.entity.SysDept;
import com.tang.system.mapper.SysDeptMapper;
import com.tang.system.service.SysDeptService;

/**
 * 部门表 SysDept 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper deptMapper;

    public SysDeptServiceImpl(SysDeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    /**
     * 获取部门列表
     *
     * @param dept 部门对象
     * @return 部门列表
     */
    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 获取部门树
     *
     * @param dept 部门对象
     * @return 部门树
     */
    @Override
    public List<SysDept> selectDeptListTree(SysDept dept) {
        var deptList = deptMapper.selectDeptList(dept);
        return deptList.stream()
            .filter(it -> it.getParentId() == 0)
            .peek(it -> it.setChildren(getChildrenList(deptList, it)))
            .toList();
    }

    /**
     * 获取子部门列表
     *
     * @param deptList 部门列表
     * @param parentDept 上级部门对象
     * @return 子部门列表
     */
    private List<SysDept> getChildrenList(List<SysDept> deptList, SysDept parentDept) {
        return deptList.stream()
            .filter(dept -> Objects.equals(dept.getParentId(), parentDept.getDeptId()))
            .peek(dept -> dept.setChildren(getChildrenList(deptList, dept)))
            .toList();
    }

    /**
     * 获取部门树下拉选项
     *
     * @param dept 部门对象
     * @return 部门树下拉选项
     */
    @Override
    public List<TreeSelect> selectDeptTree(SysDept dept) {
        var deptList = deptMapper.selectDeptList(dept);
        var treeSelectList = new ArrayList<TreeSelect>();
        deptList.forEach(it -> treeSelectList.add(new TreeSelect(it.getParentId(), it.getDeptId(), it.getDeptName())));
        return TreeUtils.buildTree(treeSelectList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param deptId 主键
     * @return 部门对象
     */
    @Override
    public SysDept selectDeptByDeptId(Long deptId) {
        return deptMapper.selectDeptByDeptId(deptId);
    }

    /**
     * 新增部门
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @Override
    public int insertDept(SysDept dept) {
        var parentId = dept.getParentId();
        var parentDept = selectDeptByDeptId(parentId);
        dept.setAncestors(parentDept.getAncestors() + "," + parentId);
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改部门信息
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDeptByDeptId(SysDept dept) {
        var newDept = deptMapper.selectDeptByDeptId(dept.getParentId());
        var oldDept = deptMapper.selectDeptByDeptId(dept.getDeptId());
        if (Objects.nonNull(newDept) && Objects.nonNull(oldDept)) {
            var newAncestors = newDept.getAncestors() + "," + newDept.getDeptId();
            var oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            var deptId = dept.getDeptId();
            var childrenList = deptMapper.selectDeptChildrenByDeptId(deptId);
            if (!childrenList.isEmpty()) {
                childrenList.forEach(children -> {
                    children.setAncestors(children.getAncestors().replaceFirst(oldAncestors, newAncestors));
                    deptMapper.updateDeptChildren(children);
                });
            }
        }
        return deptMapper.updateDeptByDeptId(dept);
    }

    /**
     * 修改部门状态
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @Override
    public int updateDeptStatusByDeptId(SysDept dept) {
        return deptMapper.updateDeptStatusByDeptId(dept);
    }

    /**
     * 通过主键删除数据
     *
     * @param deptId 主键
     * @return 影响行数
     */
    @Override
    public int deleteDeptByDeptId(Long deptId) {
        return deptMapper.deleteDeptByDeptId(deptId);
    }

    /**
     * 是否含有子部门
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean checkHasChildren(Long deptId) {
        var childrenList = deptMapper.selectDeptChildrenByDeptId(deptId);
        return !childrenList.isEmpty();
    }

}

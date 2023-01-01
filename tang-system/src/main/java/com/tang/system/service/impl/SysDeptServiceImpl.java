package com.tang.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SysDeptMapper deptMapper;

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
        var list = deptList.stream()
            .filter(o -> o.getParentId() == 0)
            .map(o -> {
                o.setChildren(getChildrenList(deptList, o));
                return o;
            }).collect(Collectors.toList());
        return list;
    }

    /**
     * 获取子部门列表
     *
     * @param deptList 部门列表
     * @param parentDept 上级部门对象
     * @return 子部门列表
     */
    private List<SysDept> getChildrenList(List<SysDept> deptList, SysDept parentDept) {
        var childrenList = deptList.stream()
            .filter(dept -> dept.getParentId() == parentDept.getDeptId())
            .map(dept -> {
                dept.setChildren(getChildrenList(deptList, dept));
                return dept;
            }).collect(Collectors.toList());
        return childrenList;
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
        deptList.forEach(o -> treeSelectList.add(new TreeSelect(o.getParentId(), o.getDeptId(), o.getDeptName())));
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
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改部门信息
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @Override
    public int updateDeptByDeptId(SysDept dept) {
        return deptMapper.updateDeptByDeptId(dept);
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

}

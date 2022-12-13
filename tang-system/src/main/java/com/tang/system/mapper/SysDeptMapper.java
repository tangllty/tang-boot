package com.tang.system.mapper;

import java.util.List;

import com.tang.system.entity.SysDept;

/**
 * 部门表 sys_dept 表数据库访问层
 *
 * @author Tang
 */
public interface SysDeptMapper {

    /**
     * 获取部门列表
     *
     * @param dept 部门对象
     * @return 部门列表
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 通过主键查询单条数据
     *
     * @param deptId 主键
     * @return 部门对象
     */
    SysDept selectDeptByDeptId(Long deptId);

    /**
     * 新增部门
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    int updateDeptByDeptId(SysDept dept);

    /**
     * 通过主键删除数据
     *
     * @param deptId 主键
     * @return 影响行数
     */
    int deleteDeptByDeptId(Long deptId);

}


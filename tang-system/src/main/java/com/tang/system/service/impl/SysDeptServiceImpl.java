package com.tang.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.system.entity.SysDept;
import com.tang.system.mapper.SysDeptMapper;
import com.tang.system.service.SysDeptService;

import java.util.List;

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

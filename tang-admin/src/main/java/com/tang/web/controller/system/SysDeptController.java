package com.tang.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.commons.utils.AjaxResult;
import com.tang.system.entity.SysDept;
import com.tang.system.service.SysDeptService;

/**
 * 部门表 SysDept 表控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    /**
     * 服务对象
     */
    @Autowired
    private SysDeptService deptService;

    /**
     * 获取部门列表
     *
     * @param dept 部门对象
     * @return 部门列表
     */
    @GetMapping("/list")
    public AjaxResult list(SysDept dept){
        var list = deptService.selectDeptList(dept);
        return AjaxResult.success(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 部门对象
     */
    @GetMapping("/{userId}")
    public AjaxResult selectDeptByDeptId(@PathVariable("userId") Long userId) {
        return AjaxResult.success(deptService.selectDeptByDeptId(userId));
    }

    /**
     * 新增部门
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @PostMapping
    public AjaxResult add(SysDept dept) {
        return AjaxResult.success(deptService.insertDept(dept));
    }

    /**
     * 修改部门信息
     *
     * @param dept 部门对象
     * @return 影响行数
     */
    @PutMapping
    public AjaxResult edit(SysDept dept) {
        return AjaxResult.success(deptService.updateDeptByDeptId(dept));
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    @DeleteMapping("/{userId}")
    public AjaxResult deleteById(@PathVariable Long userId) {
        return AjaxResult.success(deptService.deleteDeptByDeptId(userId));
    }
}


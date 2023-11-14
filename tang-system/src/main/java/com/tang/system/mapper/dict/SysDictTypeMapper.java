package com.tang.system.mapper.dict;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tang.system.entity.dict.SysDictType;

/**
 * 字典类型表 sys_dict_type 表数据库访问层
 *
 * @author Tang
 */
public interface SysDictTypeMapper {

    /**
     * 获取字典类型列表
     *
     * @param dictType 字典类型对象
     * @return 字典类型列表
     */
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 字典类型主键
     * @return 字典类型对象
     */
    SysDictType selectDictTypeByTypeId(Long typeId);

    /**
     * 通过主键获取字典类型下拉树列表ID
     * @param roleId
     * @return
     */
    List<String> selectDictIdsByRoleId(Long roleId);

    /**
     * 通过用户主键获取字典类型下拉树列表
     *
     * @param userId 用户主键
     * @return 字典类型下拉树列表
     */
    List<String> selectDictPermissionListByUserId(Long userId);

    /**
     * 根据类型主键数组查询字典类型列表
     *
     * @param dictTypeIds 类型主键数组
     * @return 字典类型列表
     */
    List<String> selectDictTypeListByIds(List<Long> dictTypeIds);

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    int insertDictType(SysDictType dictType);

    /**
     * 新增角色字典关联信息
     *
     * @param roleId 角色主键
     * @param dictIds 字典主键集合
     * @return 影响行数
     */
    int insertRoleDict(@Param("roleId") Long roleId, @Param("dictIds") List<String> dictIds);

    /**
     * 修改字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    int updateDictTypeByTypeId(SysDictType dictType);

    /**
     * 通过主键删除字典类型数据
     *
     * @param typeId 字典类型主键
     * @return 影响行数
     */
    int deleteDictTypeByTypeId(Long typeId);

    /**
     * 通过主键数组批量删除字典类型数据
     *
     * @param typeIds 字典类型主键数组
     * @return 影响行数
     */
    int deleteDictTypeByTypeIds(Long[] typeIds);

}

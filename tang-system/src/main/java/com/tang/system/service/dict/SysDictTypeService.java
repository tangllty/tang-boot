package com.tang.system.service.dict;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tang.commons.utils.tree.DictTreeSelect;
import com.tang.system.entity.dict.SysDictType;

/**
 * 字典类型表 SysDictType 表服务接口
 *
 * @author Tang
 */
public interface SysDictTypeService {

    /**
     * 获取字典类型列表
     *
     * @param dictType 字典类型对象
     * @return 字典类型列表
     */
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 获取字典树下拉选项
     *
     * @param dictType 字典类型对象
     * @return 字典树下拉选项
     */
    List<DictTreeSelect> selectDictTree(SysDictType dictType);

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 字典类型主键
     * @return 字典类型对象
     */
    SysDictType selectDictTypeByTypeId(Long typeId);

    /**
     * 根据用户主键获取权限集合
     *
     * @param userId 用户主键
     * @return 权限集合
     */
    Map<String, Set<String>> getPermissionsByUserId(Long userId);

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    int insertDictType(SysDictType dictType);

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

package com.tang.system.mapper;

import java.util.List;

import com.tang.system.entity.SysDictType;

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
    List<SysDictType> selectDictDataList(SysDictType dictType);

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 字典类型主键
     * @return 字典类型对象
     */
    SysDictType selectDictDataByTypeId(Long typeId);

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    int insertDictData(SysDictType dictType);

    /**
     * 修改字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    int updateDictDataByTypeId(SysDictType dictType);

    /**
     * 通过主键删除字典类型数据
     *
     * @param typeId 字典类型主键
     * @return 影响行数
     */
    int deleteDictDataByTypeId(Long typeId);

}

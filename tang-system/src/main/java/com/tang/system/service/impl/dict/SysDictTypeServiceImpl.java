package com.tang.system.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.system.entity.dict.SysDictType;
import com.tang.system.mapper.dict.SysDictTypeMapper;
import com.tang.system.service.dict.SysDictTypeService;

/**
 * 字典类型表 SysDictType 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;


    /**
     * 获取字典类型列表
     *
     * @param dictType 字典类型对象
     * @return 字典类型列表
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 字典类型主键
     * @return 字典类型对象
     */
    public SysDictType selectDictTypeByTypeId(Long typeId) {
        return dictTypeMapper.selectDictTypeByTypeId(typeId);
    }

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    public int insertDictType(SysDictType dictType) {
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    public int updateDictTypeByTypeId(SysDictType dictType) {
        return dictTypeMapper.updateDictTypeByTypeId(dictType);
    }

    /**
     * 通过主键删除字典类型数据
     *
     * @param typeId 字典类型主键
     * @return 影响行数
     */
    public int deleteDictTypeByTypeId(Long typeId) {
        return dictTypeMapper.deleteDictTypeByTypeId(typeId);
    }

}

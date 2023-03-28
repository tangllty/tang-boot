package com.tang.system.service.impl.dict;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.system.entity.dict.SysDictType;
import com.tang.system.mapper.dict.SysDictDataMapper;
import com.tang.system.mapper.dict.SysDictTypeMapper;
import com.tang.system.service.dict.SysDictTypeService;

/**
 * 字典类型表 SysDictType 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeMapper dictTypeMapper;

    private final SysDictDataMapper dictDataMapper;

    public SysDictTypeServiceImpl(SysDictTypeMapper dictTypeMapper, SysDictDataMapper dictDataMapper) {
        this.dictTypeMapper = dictTypeMapper;
        this.dictDataMapper = dictDataMapper;
    }

    /**
     * 获取字典类型列表
     *
     * @param dictType 字典类型对象
     * @return 字典类型列表
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 字典类型主键
     * @return 字典类型对象
     */
    @Override
    public SysDictType selectDictTypeByTypeId(Long typeId) {
        return dictTypeMapper.selectDictTypeByTypeId(typeId);
    }

    /**
     * 新增字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改字典类型信息
     *
     * @param dictType 字典类型对象
     * @return 影响行数
     */
    @Override
    public int updateDictTypeByTypeId(SysDictType dictType) {
        return dictTypeMapper.updateDictTypeByTypeId(dictType);
    }

    /**
     * 通过主键删除字典类型数据
     *
     * @param typeId 字典类型主键
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictTypeByTypeId(Long typeId) {
        dictDataMapper.deleteDictDataByTypeId(typeId);
        return dictTypeMapper.deleteDictTypeByTypeId(typeId);
    }

    /**
     * 通过主键数组批量删除字典类型数据
     *
     * @param typeIds 字典类型主键数组
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDictTypeByTypeIds(Long[] typeIds) {
        dictDataMapper.deleteDictDataByTypeIds(typeIds);
        return dictTypeMapper.deleteDictTypeByTypeIds(typeIds);
    }

}

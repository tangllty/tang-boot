package com.tang.system.service.impl.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.system.entity.dict.SysDictData;
import com.tang.system.mapper.dict.SysDictDataMapper;
import com.tang.system.service.dict.SysDictDataService;

/**
 * 字典数据表 SysDictData 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;


    /**
     * 获取字典数据列表
     *
     * @param dictData 字典数据对象
     * @return 字典数据列表
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 通过字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    @Override
    public List<SysDictData> selectDictDataListByDictType(String dictType) {
        return dictDataMapper.selectDictDataListByDictType(dictType);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dataId 字典数据主键
     * @return 字典数据对象
     */
    @Override
    public SysDictData selectDictDataByDataId(Long dataId) {
        return dictDataMapper.selectDictDataByDataId(dataId);
    }

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @Override
    public int insertDictData(SysDictData dictData) {
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @Override
    public int updateDictDataByDataId(SysDictData dictData) {
        return dictDataMapper.updateDictDataByDataId(dictData);
    }

    /**
     * 通过主键删除字典数据数据
     *
     * @param dataId 字典数据主键
     * @return 影响行数
     */
    @Override
    public int deleteDictDataByDataId(Long dataId) {
        return dictDataMapper.deleteDictDataByDataId(dataId);
    }

}

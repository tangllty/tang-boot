package com.tang.system.service.impl.dict;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tang.commons.model.SysDictDataModel;
import com.tang.commons.utils.RedisUtils;
import com.tang.system.entity.dict.SysDictData;
import com.tang.system.mapper.dict.SysDictDataMapper;
import com.tang.system.service.dict.SysDictDataService;

import static com.tang.commons.constants.CachePrefix.DICT_TYPE;

/**
 * 字典数据表 SysDictData 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataMapper dictDataMapper;

    private final RedisUtils redisUtils;

    public SysDictDataServiceImpl(SysDictDataMapper dictDataMapper, RedisUtils redisUtils) {
        this.dictDataMapper = dictDataMapper;
        this.redisUtils = redisUtils;
    }

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
    public List<SysDictDataModel> selectDictDataListByDictType(String dictType) {
        var dictDataList = redisUtils.get(DICT_TYPE + dictType);
        if (dictDataList instanceof List<?> list) {
            return list.stream().map(SysDictDataModel.class::cast).toList();
        }
        return Collections.emptyList();
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
        var rows = dictDataMapper.insertDictData(dictData);
        var dictDataList = selectDictDataListByDictType(dictData.getDictType());
        dictDataList.add(convertDictDataModel(dictData));
        redisUtils.set(DICT_TYPE + dictData.getDictType(), dictDataList);
        return rows;
    }

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    @Override
    public int updateDictDataByDataId(SysDictData dictData) {
        var dictDataList = selectDictDataListByDictType(dictData.getDictType()).stream()
            .filter(item -> !item.getDataId().equals(dictData.getDataId()))
            .collect(Collectors.toList());
        dictDataList.add(convertDictDataModel(dictData));
        redisUtils.set(DICT_TYPE + dictData.getDictType(), dictDataList);
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
        var dictType = selectDictDataByDataId(dataId).getDictType();
        var dictDataList = selectDictDataListByDictType(dictType).stream()
            .filter(item -> !item.getDataId().equals(dataId))
            .toList();
        redisUtils.set(DICT_TYPE + dictType, dictDataList);
        return dictDataMapper.deleteDictDataByDataId(dataId);
    }

    /**
     * 通过字典数据主键数组批量删除字典数据数据
     *
     * @param dataIds 字典数据主键数组
     * @return 影响行数
     */
    @Override
    public int deleteDictDataByDataIds(Long[] dataIds) {
        for (Long dataId : dataIds) {
            var dictType = selectDictDataByDataId(dataId).getDictType();
            var dictDataList = selectDictDataListByDictType(dictType).stream()
                .filter(item -> !item.getDataId().equals(dataId))
                .toList();
            redisUtils.set(DICT_TYPE + dictType, dictDataList);
        }
        return dictDataMapper.deleteDictDataByDataIds(dataIds);
    }

    /**
     * 转换字典类型为字典数据模型
     *
     * @param dictData 字典数据
     * @return 字典数据模型
     */
    private SysDictDataModel convertDictDataModel(SysDictData dictData) {
        var dictDataModel = new SysDictDataModel();
        dictDataModel.setDataId(dictData.getDataId());
        dictDataModel.setDictType(dictData.getDictType());
        dictDataModel.setDataLabel(dictData.getDataLabel());
        dictDataModel.setDataValue(dictData.getDataValue());
        dictDataModel.setCssClass(dictData.getCssClass());
        dictDataModel.setTypeClass(dictData.getTypeClass());
        dictDataModel.setSort(dictData.getSort());
        dictDataModel.setStatus(dictData.getStatus());
        dictDataModel.setRemark(dictData.getRemark());
        return dictDataModel;
    }

}

package com.tang.system.service.impl.dict;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.core.model.SysDictDataModel;
import com.tang.commons.utils.LogUtils;
import com.tang.commons.utils.RedisUtils;
import com.tang.system.entity.dict.SysDictData;
import com.tang.system.entity.dict.SysDictType;
import com.tang.system.mapper.dict.SysDictDataMapper;
import com.tang.system.mapper.dict.SysDictTypeMapper;
import com.tang.system.service.dict.SysDictDataService;
import com.tang.system.service.dict.SysDictTypeService;

import jakarta.annotation.PostConstruct;

import static com.tang.commons.constants.CachePrefix.DICT_TYPE;

/**
 * 字典类型表 SysDictType 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final SysDictTypeMapper dictTypeMapper;

    private final SysDictDataMapper dictDataMapper;

    private final SysDictDataService dictDataService;

    private final RedisUtils redisUtils;

    public SysDictTypeServiceImpl(SysDictTypeMapper dictTypeMapper, SysDictDataMapper dictDataMapper, SysDictDataService dictDataService, RedisUtils redisUtils) {
        this.dictTypeMapper = dictTypeMapper;
        this.dictDataMapper = dictDataMapper;
        this.dictDataService = dictDataService;
        this.redisUtils = redisUtils;
    }

    /**
     * 缓存字典数据
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Start caching dictionary data");
        var dictTypeList = dictTypeMapper.selectDictTypeList(new SysDictType());
        dictTypeList.forEach(dictType -> {
            var dictDataList = dictDataMapper.selectDictDataListByDictType(dictType.getDictType());
            redisUtils.set(DICT_TYPE + dictType.getDictType(), convertDictDataModelList(dictDataList));
        });
        LOGGER.info("End caching dictionary data");
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
        redisUtils.set(DICT_TYPE + dictType.getDictType(), Collections.emptyList());
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
        var oldDictType = dictTypeMapper.selectDictTypeByTypeId(dictType.getTypeId()).getDictType();
        var dictDataList = dictDataService.selectDictDataListByDictType(oldDictType);
        redisUtils.delete(DICT_TYPE + oldDictType);
        redisUtils.set(DICT_TYPE + dictType.getDictType(), dictDataList);
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
        var dictType = dictTypeMapper.selectDictTypeByTypeId(typeId).getDictType();
        redisUtils.delete(DICT_TYPE + dictType);
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
        for (Long typeId : typeIds) {
            var dictType = dictTypeMapper.selectDictTypeByTypeId(typeId).getDictType();
            redisUtils.delete(DICT_TYPE + dictType);
        }
        dictDataMapper.deleteDictDataByTypeIds(typeIds);
        return dictTypeMapper.deleteDictTypeByTypeIds(typeIds);
    }

    /**
     * 转换字典类型集合为字典类型模型集合
     *
     * @param dictDataList 字典数据集合
     * @return 字典数据模型集合
     */
    private List<SysDictDataModel> convertDictDataModelList(List<SysDictData> dictDataList) {
        return dictDataList.stream().map(dictData -> {
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
        }).collect(Collectors.toList());
    }

}

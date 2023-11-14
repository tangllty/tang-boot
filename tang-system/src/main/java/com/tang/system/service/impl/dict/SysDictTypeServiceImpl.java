package com.tang.system.service.impl.dict;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tang.commons.model.SysDictDataModel;
import com.tang.commons.utils.LogUtils;
import com.tang.commons.utils.RedisUtils;
import com.tang.commons.utils.tree.DictTreeSelect;
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
        long startTime = System.nanoTime();
        var dictTypeList = dictTypeMapper.selectDictTypeList(new SysDictType());
        dictTypeList.forEach(dictType -> {
            var dictDataList = dictDataMapper.selectDictDataListByDictType(dictType.getDictType());
            redisUtils.set(DICT_TYPE + dictType.getDictType(), convertDictDataModelList(dictDataList));
        });
        var timeTaken = Duration.ofNanos(System.nanoTime() - startTime);
        LOGGER.info("Caching dictionary data in {} seconds", timeTaken.toMillis() / 1000.0);
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
     * 获取字典树下拉选项
     *
     * @param dictType 字典类型对象
     * @return 字典树下拉选项
     */
    @Override
    public List<DictTreeSelect> selectDictTree(SysDictType dictType) {
        var dictTypeList = dictTypeMapper.selectDictTypeList(dictType);
        var dictDataList = dictDataMapper.selectDictDataList(null);
        var list = new ArrayList<DictTreeSelect>();
        dictTypeList.forEach(type -> {
            var treeSelect = new DictTreeSelect(String.valueOf(type.getTypeId()), type.getTypeName());
            var children = dictDataList.stream()
                .filter(data -> data.getDictType().equals(type.getDictType()))
                .map(data -> new DictTreeSelect(type.getTypeId() + "-" + data.getDataId(), data.getDataValue()))
                .toList();
            treeSelect.setChildren(children);
            list.add(treeSelect);
        });
        return list;
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
     * 根据用户主键获取权限集合
     *
     * @param userId 用户主键
     * @return 权限集合
     */
    public Map<String, Set<String>> getPermissionsByUserId(Long userId) {
        var dictPermissionList =  dictTypeMapper.selectDictPermissionListByUserId(userId);
        var dictTypeIds = dictPermissionList.stream()
            .filter(dictPermission -> !dictPermission.contains("-"))
            .map(Long::valueOf)
            .collect(Collectors.toList());
        var dictDataList = dictPermissionList.stream()
            .filter(dictPermission -> dictPermission.contains("-"))
            .map(dictPermission -> dictPermission.split("-")[1])
            .toList();

        // TODO Caused by: org.apache.ibatis.binding.BindingException: Parameter '__frch_typeId_0' not found. Available parameters are [arg0, collection, list]
        // var dictTypeList = dictTypeMapper.selectDictTypeListByIds(dictTypeIds);
        var dictTypeList = dictTypeIds.stream()
            .map(dictTypeId -> dictTypeMapper.selectDictTypeByTypeId(dictTypeId).getDictType())
            .collect(Collectors.toList());

        return dictTypeList.stream()
            .map(dictType -> {
                var dataList = dictDataService.selectDictDataListByDictType(dictType);
                var permissionList = dataList.stream()
                    .filter(dictData -> dictDataList.contains(String.valueOf(dictData.getDataId())))
                    .map(SysDictDataModel::getDataValue)
                    .collect(Collectors.toSet());
                return Map.of(dictType, permissionList);
            })
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

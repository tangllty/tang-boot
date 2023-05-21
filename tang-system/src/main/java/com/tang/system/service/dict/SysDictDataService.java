package com.tang.system.service.dict;

import java.util.List;

import com.tang.commons.core.model.SysDictDataModel;
import com.tang.system.entity.dict.SysDictData;

/**
 * 字典数据表 SysDictData 表服务接口
 *
 * @author Tang
 */
public interface SysDictDataService {

    /**
     * 获取字典数据列表
     *
     * @param dictData 字典数据对象
     * @return 字典数据列表
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 通过字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictDataModel> selectDictDataListByDictType(String dictType);

    /**
     * 通过主键查询单条数据
     *
     * @param dataId 字典数据主键
     * @return 字典数据对象
     */
    SysDictData selectDictDataByDataId(Long dataId);

    /**
     * 新增字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    int insertDictData(SysDictData dictData);

    /**
     * 修改字典数据信息
     *
     * @param dictData 字典数据对象
     * @return 影响行数
     */
    int updateDictDataByDataId(SysDictData dictData);

    /**
     * 通过主键删除字典数据数据
     *
     * @param dataId 字典数据主键
     * @return 影响行数
     */
    int deleteDictDataByDataId(Long dataId);

    /**
     * 通过字典数据主键数组批量删除字典数据数据
     *
     * @param dataIds 字典数据主键数组
     * @return 影响行数
     */
    int deleteDictDataByDataIds(Long[] dataIds);

}

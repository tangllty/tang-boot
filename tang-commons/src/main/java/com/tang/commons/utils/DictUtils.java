package com.tang.commons.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.tang.commons.constants.CachePrefix;
import com.tang.commons.model.SysDictDataModel;

/**
 * 字典工具类
 *
 * @author Tang
 */
public class DictUtils {

    private DictUtils() {
    }

    private static final RedisUtils  REDIS_UTILS = SpringUtils.getBean(RedisUtils.class);

    /**
     * 获取字典数据集合
     *
     * @param dictType  字典类型
     * @return 字典数据集合
     */
    public static List<SysDictDataModel> getDictDataList(String dictType) {
        var dictDataList = REDIS_UTILS.get(CachePrefix.DICT_TYPE + dictType);
        if (dictDataList instanceof List<?> list) {
            return list.stream().map(SysDictDataModel.class::cast).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue) {
        var dictDataList = getDictDataList(dictType);
        for (SysDictDataModel dict : dictDataList) {
            if (dict.getDataValue().equals(dictValue)) {
                return dict.getDataLabel();
            }
        }
        return dictValue;
    }

    /**
     * 获取字典键值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典键值
     */
    public static String getDictValue(String dictType, String dictLabel) {
        var dictDataList = getDictDataList(dictType);
        for (SysDictDataModel dict : dictDataList) {
            if (dict.getDataLabel().equals(dictLabel)) {
                return dict.getDataValue();
            }
        }
        return dictLabel;
    }

}

package com.tang.admin.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tang.commons.utils.AjaxResult;
import com.tang.commons.utils.Assert;
import com.tang.commons.utils.UploadsUtils;

/**
 * 文件上传逻辑控制层
 *
 * @author Tang
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 上传文件
     * @param file 文件
     * @return 文件地址
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {
        Assert.isNull(file, "上传文件不能为空");
        var filePath = UploadsUtils.upload(file);
        return AjaxResult.success(Map.of("filePath", filePath));
    }

}

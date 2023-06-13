package com.tang.commons.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tang.commons.constants.UploadsPrefix;
import com.tang.commons.core.autoconfigure.TangProperties;

/**
 * 文件上传工具类
 *
 * @author Tang
 */
public class UploadsUtils {

    private UploadsUtils() {
    }

    private static final TangProperties TANG_PROPERTIES = SpringUtils.getBean(TangProperties.class);

    /**
     * 获取文件上传路径
     *
     * @return 文件上传路径
     */
    public static String getUploads() {
        return TANG_PROPERTIES.getUploads();
    }

    /**
     * 获取头像上传路径
     *
     * @return 头像上传路径
     */
    public static String getAvatar() {
        return getUploads() + UploadsPrefix.AVATAR_PREFIX;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    public static String upload(MultipartFile file) {
        var filePath = upload(file, getUploads());
        return filePath.replace(getUploads(), UploadsPrefix.UPLOADS);
    }

    /**
     * 上传头像
     *
     * @param file 文件
     * @return 文件路径
     */
    public static String uploadAvatar(MultipartFile file) {
        var avatarPath = upload(file, getAvatar());
        return avatarPath.replace(getAvatar(), UploadsPrefix.AVATAR);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    private static String upload(MultipartFile file, String path) {
        var fileName = file.getOriginalFilename();
        var destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        var filePath = destDir + File.separator + fileName;
        var dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

}

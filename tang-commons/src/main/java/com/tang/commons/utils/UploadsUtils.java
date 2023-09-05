package com.tang.commons.utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.tang.commons.autoconfigure.TangProperties;
import com.tang.commons.constants.UploadsPrefix;
import com.tang.commons.exception.file.MaxFileNameLengthException;
import com.tang.commons.exception.file.MaxFileSizeException;

/**
 * 文件上传工具类
 *
 * @author Tang
 */
public class UploadsUtils {

    private UploadsUtils() {
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final TangProperties TANG_PROPERTIES = SpringUtils.getBean(TangProperties.class);

    /**
     * 文件名最大长度
     */
    private static final int MAX_FILE_NAME_LENGTH = 128;

    /**
     * 文件最大大小
     */
    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L;

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
     * @param avatar 头像文件
     * @return 文件路径
     */
    public static String uploadAvatar(MultipartFile avatar) {
        var avatarPath = upload(avatar, getAvatar());
        return avatarPath.replace(getAvatar(), UploadsPrefix.AVATAR);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    private static String upload(MultipartFile file, String path) {
        var fileName = getFileName(file);
        if (fileName.length() > MAX_FILE_NAME_LENGTH) {
            throw new MaxFileNameLengthException("上传失败, 文件名最大长度为: " + MAX_FILE_NAME_LENGTH);
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new MaxFileSizeException("上传失败, 文件最大大小为: " + ByteUtils.getSize(MAX_FILE_SIZE));
        }
        fileName = System.currentTimeMillis() + "_" + fileName;
        var destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        var filePath = destDir + File.separator + fileName;
        var dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            LOGGER.error("上传文件失败, 文件路径: {}, 文件大小: {}", filePath, ByteUtils.getSize(file.getSize()), e);
        }
        filePath = filePath.replace("\\", "/");
        LOGGER.info("上传文件成功, 文件路径: {}, 文件大小: {}", filePath, ByteUtils.getSize(file.getSize()));
        return filePath;
    }

    /**
     * 获取文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(MultipartFile file) {
        var fileName = file.getOriginalFilename();
        if (Objects.isNull(fileName)) {
            fileName = "unknown-file-name";
        }

        // 从文件名中删除所有点和目录分隔符
        fileName = fileName
            .replaceAll("\\.", "")
            .replaceAll("/", "")
            .replaceAll("\\\\", "");
        return fileName;
    }

}

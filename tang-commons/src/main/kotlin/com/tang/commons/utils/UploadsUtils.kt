package com.tang.commons.utils

import java.io.File
import java.io.IOException

import org.springframework.web.multipart.MultipartFile

import com.tang.commons.constants.UploadsPrefix
import com.tang.commons.exception.file.MaxFileNameLengthException
import com.tang.commons.exception.file.MaxFileSizeException
import com.tang.commons.autoconfigure.FileProperties
import com.tang.commons.utils.id.IdUtils

/**
 * 文件上传工具类
 *
 * @author Tang
 */
object UploadsUtils {

    private val LOGGER = LogUtils.getLogger()

    private val FILE_PROPERTIES = SpringUtils.getBean(FileProperties::class.java)

    /**
     * 文件上传路径
     */
    private val uploads: String get() = FILE_PROPERTIES.uploads

    private val maxSize: Long get() = (FILE_PROPERTIES.maxSize * FILE_PROPERTIES.sizeUnit.value.toDouble()).toLong()

    private val maxFilenameLength: Int get() = FILE_PROPERTIES.maxFileNameLength

    private val allowedTypes: Array<String> get() = FILE_PROPERTIES.allowedTypes

    /**
     * 头像上传路径
     */
    private val avatar: String get() = uploads + UploadsPrefix.AVATAR_PREFIX

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    @JvmStatic
    fun upload(file: MultipartFile): String {
        val filePath = upload(file, uploads)
        return filePath.replace(uploads, UploadsPrefix.UPLOADS)
    }

    /**
     * 上传头像
     *
     * @param avatar 头像文件
     * @return 文件路径
     */
    @JvmStatic
    fun uploadAvatar(avatar: MultipartFile): String {
        val avatarPath = upload(avatar, UploadsUtils.avatar)
        return avatarPath.replace(UploadsUtils.avatar, UploadsPrefix.AVATAR)
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件路径
     */
    private fun upload(file: MultipartFile, path: String): String {
        var fileName = getFileName(file)

        Assert.isTrue(fileName.length > maxFilenameLength, MaxFileNameLengthException("上传失败, 文件名最大长度为: $maxFilenameLength, 当前文件名长度: ${fileName.length}"))
        Assert.isTrue(file.size > maxSize, MaxFileSizeException("上传失败, 文件最大大小为: ${ByteUtils.getSize(maxSize)}, 当前文件大小: ${ByteUtils.getSize(file.size)}"))
        Assert.isTrue(!allowedTypes.contains(file.originalFilename!!.substringAfterLast(".")), "上传失败, 文件类型不允许, 仅支持: ${allowedTypes.joinToString(", ")}")

        fileName = "${IdUtils.snowflake()}_$fileName"
        val destDir = File(path)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }

        val filePath = "${destDir}${File.separator}${fileName}"
        val dest = File(filePath)
        try {
            file.transferTo(dest)
            LOGGER.info("上传文件成功, 文件路径: {}, 文件大小: {}", filePath, ByteUtils.getSize(file.size))
        } catch (e: IllegalStateException) {
            LOGGER.error("上传文件失败, 文件路径: {}, 文件大小: {}", filePath, ByteUtils.getSize(file.size), e)
        } catch (e: IOException) {
            LOGGER.error("上传文件失败, 文件路径: {}, 文件大小: {}", filePath, ByteUtils.getSize(file.size), e)
        }
        return filePath.replace("\\", "/")
    }

    /**
     * 获取文件名
     *
     * @param file 文件
     * @return 文件名
     */
    private fun getFileName(file: MultipartFile): String {
        var fileName = file.originalFilename ?: "unknown-file-name"

        // 删除目录分隔符和特殊字符
        fileName = fileName.replace("[\\\\/:*?\"<>|]".toRegex(), "")
        return fileName
    }

}

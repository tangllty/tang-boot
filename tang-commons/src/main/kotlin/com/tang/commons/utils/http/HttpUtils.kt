package com.tang.commons.utils.http

import com.fasterxml.jackson.core.type.TypeReference
import com.tang.commons.constants.ContentType
import com.tang.commons.utils.json.JSONUtils
import com.tang.commons.utils.LogUtils
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Path

/**
 * Http 工具类
 *
 * @author Tang
 */
object HttpUtils {

    private val LOGGER = LogUtils.getLogger()

    private val client = HttpClient.newHttpClient()

    private val typeReference: TypeReference<Map<String, Any>> = object : TypeReference<Map<String, Any>>() {}

    @JvmStatic
    fun parse(response: String): Map<String, Any> {
        return JSONUtils.parse(response, typeReference)
    }

    @JvmStatic
    fun get(url: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("accept", ContentType.APPLICATION_JSON)
            .GET()
            .build()
        LOGGER.info("request: ${request.uri()}")
        val response = client.send(request, BodyHandlers.ofString())
        return response.body()
    }

    @JvmStatic
    fun get(url: String, params: Map<String, String>): String {
        val urlWithParams = if (url.contains("?")) {
            if (url.endsWith("?")) url else "$url&"
        } else {
            "$url?"
        }
        val urlParams = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        return get(urlWithParams + urlParams)
    }

    @JvmStatic
    fun <T> getFile(url: String, responseBodyHandler: BodyHandler<T>): HttpResponse<T> {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("accept", ContentType.APPLICATION_OCTET_STREAM)
            .GET()
            .build()
        return client.send(request, responseBodyHandler)
    }

    @JvmStatic
    fun getFile(url: String): MultipartFile {
        val response = getFile(url, BodyHandlers.ofByteArray())
        val fileByteArray = response.body()
        val fileName = getFileName(url)
        val contentType = getContentType(response)
        val tempFile: Path = Files.createTempFile("temp", fileName)
        Files.write(tempFile, fileByteArray)
        return MockMultipartFile("file", "${fileName}.${contentType.substringAfterLast("/")}", contentType, Files.readAllBytes(tempFile))
    }

    @JvmStatic
    fun post(url: String, body: String): String {
        return post(url, BodyPublishers.ofString(body))
    }

    @JvmStatic
    fun post(url: String, body: Map<String, String>): String {
        return post(url, JSONUtils.toString(body))
    }

    @JvmStatic
    fun post(url: String, body: File): String {
        return post(url, BodyPublishers.ofFile(body.toPath()))
    }

    @JvmStatic
    fun post(url: String, body: ByteArray): String {
        return post(url, BodyPublishers.ofByteArray(body))
    }

    @JvmStatic
    fun post(url: String, bodyPublisher: BodyPublisher = BodyPublishers.noBody()): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("accept", ContentType.APPLICATION_JSON)
            .POST(bodyPublisher)
            .build()
        val response = client.send(request, BodyHandlers.ofString())
        return response.body()
    }

    private fun getFileName(url: String): String {
        return URL(url).path.substringAfterLast("/")
    }

    private fun getContentType(response: HttpResponse<*>): String {
        return response.headers().firstValue("Content-Type").orElse(ContentType.APPLICATION_OCTET_STREAM)
    }

    /**
     * 加密指定参数
     *
     * @param url        url
     * @param paramNames 参数名
     */
    @JvmStatic
    fun encryptParams(url: String, paramNames: List<String>): String {
        if (paramNames.isEmpty()) return url

        val params: MutableMap<String, String> = url.substringAfter("?").split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }.toMutableMap()

        // 加密参数替换为同长度的x
        paramNames.forEach { params[it] = "x".repeat(params[it]!!.length) }
        return url.substringBefore("?") + "?" + params.map { "${it.key}=${it.value}" }.joinToString("&")
    }

    /**
     * 加密指定参数
     *
     * @param url        url
     * @param paramNames 参数名
     */
    @JvmStatic
    fun encryptParams(url: String, vararg paramNames: String): String {
        return encryptParams(url, paramNames.toList())
    }

    @JvmStatic
    fun log(url: String, vararg encryptParams: String) {
        LOGGER.info("request: ${encryptParams(url, *encryptParams)}")
    }

    @JvmStatic
    fun log(url: String, encryptParams: List<String>) {
        log(url, *encryptParams.toTypedArray())
    }

}

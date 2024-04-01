package com.tang.commons.utils.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.tang.commons.constants.ContentType
import com.tang.commons.utils.LogUtils
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers

/**
 * Http 工具类
 *
 * @author Tang
 */
object HttpUtils {

    private val LOGGER = LogUtils.getLogger()

    private val client = HttpClient.newHttpClient()

    private val objectMapper = ObjectMapper()

    private val typeReference: TypeReference<Map<String, Any>> = object : TypeReference<Map<String, Any>>() {}

    @JvmStatic
    fun parse(response: String): Map<String, Any> {
        return objectMapper.readValue(response, typeReference)
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
    fun get(url: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .header("accept", ContentType.APPLICATION_JSON)
            .GET()
            .build()
        LOGGER.info("request: ${request.uri()}")
        val response = client.send(request, BodyHandlers.ofString())
        return response.body()
    }

    @JvmStatic
    fun post(url: String, body: String): String {
        return post(url, BodyPublishers.ofString(body))
    }

    @JvmStatic
    fun post(url: String, body: Map<String, String>): String {
        return post(url, objectMapper.writeValueAsString(body))
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
            .uri(URI(url))
            .header("accept", ContentType.APPLICATION_JSON)
            .POST(bodyPublisher)
            .build()
        LOGGER.info("request: ${request.uri()}")
        val response = client.send(request, BodyHandlers.ofString())
        return response.body()
    }

}

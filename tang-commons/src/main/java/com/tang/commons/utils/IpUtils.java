package com.tang.commons.utils;

import java.util.List;
import java.util.Objects;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;

import jakarta.servlet.http.HttpServletRequest;

/**
 * IP 地址工具类
 *
 * @author Tang
 */
public class IpUtils {

    private IpUtils() {
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 获取客户端IP
     *
     * @param request {@link HttpServletRequest}
     * @return IP 地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        var headers = List.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP");
        var unknown = "unknown";

        if (Objects.isNull(request)) {
            return unknown;
        }
        var ipAddr = headers.stream()
            .map(request::getHeader)
            .filter(ip -> Objects.nonNull(ip) && !ip.isEmpty() && !unknown.equalsIgnoreCase(ip))
            .findFirst()
            .orElse(request.getRemoteAddr());
        return "0:0:0:0:0:0:0:1".equals(ipAddr) ? "127.0.0.1" : ipAddr;
    }

    /**
     * 获取城市名称
     *
     * @param ip IP 地址
     * @return 城市名称
     */
    public static String getCity(String ip) {
        var region = getRegion(ip);
        return region.split("\\|")[3];
    }

    /**
     * 获取地区信息
     *
     * @param ip IP 地址
     * @return 地区信息
     */
    private static String getRegion(String ip) {
        try {
            var classPathResource = new ClassPathResource("ip2region.xdb");
            var inputStream = classPathResource.getInputStream();
            var byteArray = inputStream.readAllBytes();
            var searcher = Searcher.newWithBuffer(byteArray);
            var region = searcher.search(ip);
            searcher.close();
            return region;
        } catch (Exception e) {
            LOGGER.error("获取地区信息失败", e);
        }
        return "";
    }

}

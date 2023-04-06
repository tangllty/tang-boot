package com.tang.commons.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ResourceUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * IP 地址工具类
 *
 * @author Tang
 */
public class IpUtils {

    private IpUtils() {
    }

    /**
     * 获取客户端IP
     *
     * @param request request
     * @return IP 地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";

        if (request == null) {
            return unknown;
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取城市名称
     *
     * @param ip IP 地址
     * @return 城市名称
     */
    public static String getCity(String ip) {
        String region = getRegion(ip);
        return region.split("\\|")[3];
    }

    /**
     * 获取地区信息
     *
     * @param ip IP 地址
     * @return 地区信息
     */
    public static String getRegion(String ip) {
        // 1、创建 searcher 对象
        String dbPath = null;
        try {
            dbPath = ResourceUtils.getFile("classpath:ip2region.xdb").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String region = null;
        try {
            if (searcher != null) {
                // 2、查询
                region = searcher.search(ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 3、关闭资源
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return region != null ? region : "";
    }

}

package com.tang.commons.utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import com.tang.commons.enumeration.SizeUnit;

/**
 * 字节工具类
 *
 * @author Tang
 */
public class ByteUtils {

    private ByteUtils() {
    }

    /**
     * 格式化为带单位的字符串
     *
     * @param size 字节大小
     * @return 带单位的字符串
     */
    public static String getSize(long size) {
        if (size <= 0) {
            return "0 B";
        }
        var sizeUnits = List.of("B", "KB", "MB", "GB", "TB", "PB", "EB");
        var formatter = new DecimalFormat("#,##0.#");
        var digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return formatter.format(size / Math.pow(1024, digitGroups)) + " " + sizeUnits.get(digitGroups);
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String byteToHex(byte[] bytes) {
        // 每个字节用两个字符表示，所以字符串的长度是数组长度的两倍
        final var hexChars = new char[bytes.length << 1];
        final var hexDigits = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        var index = 0;
        for (var b : bytes) {
            // 将字节的高位四个比特转换为相应的十六进制字符
            hexChars[index++] = hexDigits[(b >> 4) & 0xF];
            // 将字节的低位四个比特转换为相应的十六进制字符
            hexChars[index++] = hexDigits[b & 0xF];
        }
        return new String(hexChars);
    }

    /**
     * 文件单位转换
     *
     * @param size       大小
     * @param inputUnit  输入单位
     * @param outputUnit 输出单位
     * @return 转换后的大小
     */
    public static double convert(double size, SizeUnit inputUnit, SizeUnit outputUnit) {
        if (size == 0) {
            return size;
        }

        if (Objects.isNull(inputUnit) || Objects.isNull(outputUnit)) {
            return size;
        }

        var inputUnitValue = inputUnit.getValue();
        var outputUnitValue = outputUnit.getValue();

        return size * inputUnitValue / outputUnitValue;
    }

}

package com.tang.commons.utils.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;

import com.tang.commons.utils.LogUtils;
import com.tang.commons.utils.id.IdUtils;

/**
 * 验证码工具类
 *
 * @author Tang
 */
public class CaptchaUtils {

    private CaptchaUtils() {
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 验证码图片宽度
     */
    private static final int WIDTH = 130;

    /**
     * 验证码图片高度
     */
    private static final int HEIGHT = 48;

    /**
     * 随机数
     */
    private static final Random RANDOM = new Random();

    /**
     * 验证码图片
     */
    private static final BufferedImage BUFFERED_IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    /**
     * 生成验证码
     *
     * @param type 验证码类型
     * @return 验证码
     */
    public static Captcha generate(CaptchaType type) {
        final char[] characters = switch (type) {
            case NUMBER -> "0123456789".toCharArray();
            case LETTER -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            case LETTER_IGNORE_CASE -> new char[0];
            case MIXED -> new char[0];
        };

        var graphics = BUFFERED_IMAGE.getGraphics();
        // 填充背景色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        // 绘制边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        // 绘制干扰线
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            graphics.drawLine(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT), RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
        }
        // 绘制干扰点
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 6; i++) {
            graphics.drawOval(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT), RANDOM.nextInt(WIDTH / 2), RANDOM.nextInt(HEIGHT / 2));
        }
        var captcha = new StringBuilder();
        // 绘制验证码
        for (int i = 0; i < 4; i++) {
            var character = characters[RANDOM.nextInt(characters.length)];
            captcha.append(character);
            graphics.setColor(new Color(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)));
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            graphics.drawString(String.valueOf(character), 20 + i * 30, 35);
        }
        graphics.dispose();

        return new Captcha(IdUtils.snowflake(), captcha.toString(), toByteArray());
    }

    /**
     * 将验证码图片转换为字节数组
     *
     * @return 字节数组
     */
    private static byte[] toByteArray() {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(BUFFERED_IMAGE, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("生成验证码失败", e);
            return new byte[0];
        } finally {
            BUFFERED_IMAGE.flush();
        }
    }

}

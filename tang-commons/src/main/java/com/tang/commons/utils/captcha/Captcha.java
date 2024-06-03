package com.tang.commons.utils.captcha;

/**
 * 验证码模型
 *
 * @param id    验证码 ID
 * @param text  答案
 * @param image 图片
 * @author Tang
 */
public record Captcha(Long id, String text, byte[] image) {
}

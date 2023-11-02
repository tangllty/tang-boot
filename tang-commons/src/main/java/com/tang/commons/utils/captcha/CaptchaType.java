package com.tang.commons.utils.captcha;

/**
 * @author Tang
 */
public enum CaptchaType {

    /**
     * 数字
     */
    NUMBER,

    /**
     * 字母（大小写敏感）
     */
    LETTER,

    /**
     * 字母（大小写不敏感）
     */
    LETTER_IGNORE_CASE,

    /**
     * 混合（大小写不敏感）
     */
    MIXED

}

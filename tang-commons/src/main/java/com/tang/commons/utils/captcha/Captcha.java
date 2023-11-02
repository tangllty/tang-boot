package com.tang.commons.utils.captcha;

/**
 * 验证码模型
 *
 * @author Tang
 */
public class Captcha {

    /**
     * 验证码 ID
     */
    private Long id;

    /**
     * 答案
     */
    private String text;

    /**
     * 图片
     */
    private byte[] image;

    public Captcha() {
    }

    public Captcha(Long id, String text, byte[] image) {
        this.id = id;
        this.text = text;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}

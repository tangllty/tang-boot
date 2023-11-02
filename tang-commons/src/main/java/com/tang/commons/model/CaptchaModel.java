package com.tang.commons.model;

import java.io.Serializable;

/**
 * 验证码模型
 *
 * @author Tang
 */
public class CaptchaModel implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 2521646489620204864L;

    /**
     * 验证码 ID
     */
    private Long id;

    /**
     * 验证码图片
     */
    private String text;

    public CaptchaModel() {
    }

    public CaptchaModel(Long id, String text) {
        this.id = id;
        this.text = text;
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

}

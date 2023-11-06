package com.tang.commons.constants;

/**
 * 缓存前缀
 *
 * @author Tang
 */
public class CachePrefix {

    private CachePrefix() {
    }

    /**
     * 缓存前缀
     */
    public static final String SYSTEM = "tang-boot:";

    /**
     * 登陆用户信息缓存前缀
     */
    public static final String LOGIN_TOKENS = SYSTEM + "login_tokens:";

    /**
     * 登陆用户ID缓存前缀
     */
    public static final String LOGIN_USER_ID = SYSTEM + "login_user_id:";

    /**
     * 字典数据缓存前缀
     */
    public static final String DICT_TYPE = SYSTEM + "dict_type:";

    /**
     * 验证码缓存前缀
     */
    public static final String CAPTCHA = SYSTEM + "captcha:";

}

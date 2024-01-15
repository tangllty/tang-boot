package com.tang.commons.constants

/**
 * 缓存前缀
 *
 * @author Tang
 */
object CachePrefix {

    /**
     * 缓存前缀
     */
    const val SYSTEM = "tang-boot:"

    /**
     * 登陆用户信息缓存前缀
     */
    const val LOGIN_TOKENS = SYSTEM + "login_tokens:"

    /**
     * 登陆用户ID缓存前缀
     */
    const val LOGIN_USER_ID = SYSTEM + "login_user_id:"

    /**
     * 字典数据缓存前缀
     */
    const val DICT_TYPE = SYSTEM + "dict_type:"

    /**
     * 验证码缓存前缀
     */
    const val CAPTCHA = SYSTEM + "captcha:"

}

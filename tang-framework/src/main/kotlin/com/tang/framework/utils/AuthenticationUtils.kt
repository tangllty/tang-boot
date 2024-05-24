package com.tang.framework.utils

import org.springframework.security.authentication.AbstractAuthenticationToken

import com.tang.commons.enumeration.LoginType
import com.tang.framework.security.authentication.email.EmailAuthenticationToken
import com.tang.framework.security.authentication.github.GitHubAuthenticationToken
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken

/**
 * @author Tang
 */
object AuthenticationUtils {

    @JvmStatic
    fun newInstance(loginType: String, vararg params: Any): AbstractAuthenticationToken {
        return when (LoginType.getLoginType(loginType)) {
            LoginType.USERNAME -> UsernameAuthenticationToken(params[0], params[1])
            LoginType.EMAIL -> EmailAuthenticationToken(params[0], params[1])
            LoginType.GITHUB -> GitHubAuthenticationToken(params[0])
            else -> throw IllegalArgumentException("Unexpected login type: $loginType")
        }
    }

}

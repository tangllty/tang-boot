package com.tang.framework.utils

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

import com.tang.commons.enumeration.LoginType
import com.tang.framework.security.authentication.email.EmailAuthenticationToken
import com.tang.framework.security.authentication.github.GitHubAuthenticationToken
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken

/**
 * @author Tang
 */
object AuthenticationUtils {

    @JvmStatic
    fun newInstance(loginType: String, principal: Any, authorities: Collection<GrantedAuthority>): AbstractAuthenticationToken {
        return when (LoginType.getLoginType(loginType)) {
            LoginType.USERNAME -> UsernameAuthenticationToken(principal, authorities)
            LoginType.EMAIL -> EmailAuthenticationToken(principal, authorities)
            LoginType.GITHUB -> GitHubAuthenticationToken(principal)
            else -> throw IllegalArgumentException("Unexpected login type: $loginType")
        }
    }

}

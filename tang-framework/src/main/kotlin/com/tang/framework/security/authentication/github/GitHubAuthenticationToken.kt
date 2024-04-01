package com.tang.framework.security.authentication.github

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * GitHub 身份验证令牌
 *
 * @author Tang
 */
class GitHubAuthenticationToken: AbstractAuthenticationToken {

    @Transient
    private val principal: Any

    @Transient
    private val credentials: Any

    constructor(principal: Any) : super(null) {
        this.principal = principal
        this.credentials = ""
        isAuthenticated = false
    }

    constructor(principal: Any, authorities: Collection<GrantedAuthority>) : super(authorities) {
        this.principal = principal
        this.credentials = ""
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return credentials
    }

    override fun getPrincipal(): Any {
        return principal
    }

}

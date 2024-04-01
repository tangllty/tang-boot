package com.tang.framework.security.authentication.github

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.tang.commons.autoconfigure.oauth.GitHubProperties
import com.tang.commons.enumeration.LoginType
import com.tang.commons.utils.StringUtils
import com.tang.framework.web.service.authentication.AuthenticationService
import com.tang.system.entity.SysUser
import com.tang.system.service.SysUserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.Collections
import java.util.Objects

/**
 * 用户名密码身份验证
 *
 * @author Tang
 */
@Component
class GitHubAuthenticationProvider(
    private val githubProperties: GitHubProperties,
    private val userService: SysUserService,
    private val authenticationService: AuthenticationService
) : AuthenticationProvider {

    private val httpClient: HttpClient = HttpClient.newBuilder().build()

    private val objectMapper = ObjectMapper()

    private val typeReference: TypeReference<Map<String, Any>> = object : TypeReference<Map<String, Any>>() {}

    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is GitHubAuthenticationToken) {
            return null
        }

        var authenticationToken = authentication
        val code = authenticationToken.principal.toString()

        var tokenUrl = "https://github.com/login/oauth/access_token?client_id={}&client_secret={}&code={}"
        tokenUrl = StringUtils.format(tokenUrl, githubProperties.clientId, githubProperties.clientSecret, code)
        val tokenRequest = HttpRequest.newBuilder()
            .uri(URI(tokenUrl))
            .header("accept", "application/json")
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()
        val tokenResponse = httpClient.send(tokenRequest, BodyHandlers.ofString())
        val tokenResult = objectMapper.readValue(tokenResponse.body(), typeReference)
        val accessToken = tokenResult["access_token"].toString()

        val userUrl = "https://api.github.com/user"
        val userRequest = HttpRequest.newBuilder()
            .uri(URI(userUrl))
            .header("accept", "application/json")
            .header("Authorization", "token $accessToken")
            .GET()
            .build()
        val userResponse = httpClient.send(userRequest, BodyHandlers.ofString())
        val userResult = objectMapper.readValue(userResponse.body(), typeReference)
        val username = userResult["login"].toString()

        val user = userService.selectUserByUsername(username)
        if (Objects.isNull(user)) {
            val insertUser = SysUser()
            insertUser.username = username

        }
        val userModel = authenticationService.createUserModel(user, null, username, LoginType.GITHUB.value)

        authenticationToken = GitHubAuthenticationToken(userModel, Collections.emptyList())

        return authenticationToken
    }

    override fun supports(authentication: Class<*>): Boolean {
        return GitHubAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}

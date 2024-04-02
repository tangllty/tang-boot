package com.tang.framework.security.authentication.github

import com.tang.commons.autoconfigure.oauth.GitHubProperties
import com.tang.commons.constants.ContentType
import com.tang.commons.enumeration.LoginType
import com.tang.commons.enumeration.UserType
import com.tang.commons.utils.StringUtils
import com.tang.commons.utils.UploadsUtils
import com.tang.commons.utils.http.HttpUtils
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

    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is GitHubAuthenticationToken) {
            return null
        }

        var authenticationToken = authentication
        val code = authenticationToken.principal.toString()

        var tokenUrl = "https://github.com/login/oauth/access_token?client_id={}&client_secret={}&code={}"
        tokenUrl = StringUtils.format(tokenUrl, githubProperties.clientId, githubProperties.clientSecret, code)
        val tokenResponse = HttpUtils.post(tokenUrl)
        val tokenResult = HttpUtils.parse(tokenResponse)
        val accessToken = tokenResult["access_token"].toString()

        val userUrl = "https://api.github.com/user"
        val userRequest = HttpRequest.newBuilder()
            .uri(URI(userUrl))
            .header("accept", ContentType.APPLICATION_JSON)
            .header("Authorization", "Bearer $accessToken")
            .GET()
            .build()
        val userResponse = httpClient.send(userRequest, BodyHandlers.ofString())
        val userResult = HttpUtils.parse(userResponse.body())
        val username = userResult["login"].toString()

        var user = userService.selectUserByUserType(username, UserType.GITHUB.value)
        if (Objects.isNull(user)) {
            val insertUser = SysUser()
            insertUser.username = username
            insertUser.nickname = userResult["name"].toString()
            val avatarUrl = userResult["avatar_url"].toString()
            insertUser.avatar = UploadsUtils.uploadAvatar(HttpUtils.getFile(avatarUrl))
            insertUser.email = userResult["email"].toString()
            insertUser.userType = UserType.GITHUB.value
            insertUser.deptId = 8
            userService.insertOauthUser(insertUser)
            user = userService.selectUserByUserId(insertUser.userId)
        }
        val userModel = authenticationService.createUserModel(user, null, username, LoginType.GITHUB.value)

        authenticationToken = GitHubAuthenticationToken(userModel, Collections.emptyList())

        return authenticationToken
    }

    override fun supports(authentication: Class<*>): Boolean {
        return GitHubAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}

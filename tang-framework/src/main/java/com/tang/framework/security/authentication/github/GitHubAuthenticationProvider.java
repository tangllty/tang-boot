package com.tang.framework.security.authentication.github;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.commons.autoconfigure.oauth.GitHubProperties;
import com.tang.commons.enumeration.LoginType;
import com.tang.commons.utils.StringUtils;
import com.tang.framework.web.service.authentication.AuthenticationService;
import com.tang.system.service.SysUserService;

/**
 * 用户名密码身份验证
 *
 * @author Tang
 */
@Component
public class GitHubAuthenticationProvider implements AuthenticationProvider {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};

    private final GitHubProperties githubProperties;

    private final SysUserService userService;

    private final AuthenticationService authenticationService;

    public GitHubAuthenticationProvider(GitHubProperties githubProperties, SysUserService userService, AuthenticationService authenticationService) {
        this.githubProperties = githubProperties;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        var authenticationToken = (GitHubAuthenticationToken) authentication;
        var code = authenticationToken.getPrincipal().toString();

        var url = "https://github.com/login/oauth/access_token?client_id={}&client_secret={}&code={}";
        url = StringUtils.format(url, githubProperties.getClientId(), githubProperties.getClientSecret(), code);

        // TODO GitHub OAuth2.0 登录逻辑

        // var request = HttpRequest.newBuilder()
        //     .uri(new URI(url))
        //     .header("accept", "application/json")
        //     .POST(HttpRequest.BodyPublishers.noBody())
        //     .build();

        // var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // var map = objectMapper.readValue(response.body(), typeReference);

        // var urlResult = "https://api.github.com/user";

        // var requestResult = HttpRequest.newBuilder()
        //     .uri(new URI(urlResult))
        //     .header("accept", "application/json")
        //     .header("Authorization", "token " + map.get("access_token"))
        //     .GET()
        //     .build();

        // var responseResult = httpClient.send(requestResult, HttpResponse.BodyHandlers.ofString());

        // var result = objectMapper.readValue(responseResult.body(), typeReference);

        // var user = userService.selectUserByUsername(username);

        // var userModel = authenticationService.createUserModel(user, password, username, LoginType.USERNAME.getName());

        // authenticationToken = new GitHubAuthenticationToken(userModel, Collections.emptyList());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GitHubAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

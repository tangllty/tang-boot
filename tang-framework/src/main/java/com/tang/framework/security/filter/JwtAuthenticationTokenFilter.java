package com.tang.framework.security.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tang.commons.constants.LoginType;
import com.tang.framework.security.authentication.email.EmailAuthenticationToken;
import com.tang.framework.security.authentication.username.UsernameAuthenticationToken;
import com.tang.framework.web.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * token 过滤器
 *
 * @author Tang
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var userModel = tokenService.get(request);

        if (userModel != null) {
            tokenService.verifyToken(userModel);

            AbstractAuthenticationToken authenticationToken;
            authenticationToken = switch (userModel.getLoginType()) {
                case LoginType.USERNAME -> new UsernameAuthenticationToken(userModel, Collections.emptyList());
                case LoginType.EMAIL -> new EmailAuthenticationToken(userModel, Collections.emptyList());
                default -> throw new IllegalStateException("Unexpected value: " + userModel.getLoginType());
            };

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}

package com.tang.framework.security.authentication.github;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * GitHub 身份验证令牌
 *
 * @author Tang
 */
public class GitHubAuthenticationToken extends AbstractAuthenticationToken {

    @java.io.Serial
    private static final long serialVersionUID = 6183503186676400429L;

    private final transient Object principal;

    private final transient Object credentials;

    public GitHubAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.credentials = null;
        setAuthenticated(false);
    }

    public GitHubAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public GitHubAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}

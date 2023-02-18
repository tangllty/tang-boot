package com.tang.framework.security.authentication.email;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 邮箱密码身份令牌
 *
 * @author Tang
 */
public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    @java.io.Serial
    private static final long serialVersionUID = -5268051187371586993L;

    private final transient Object principal;

    private final transient Object credentials;

    public EmailAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public EmailAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
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

package com.shyamalmadura.spring.spring.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomAuthentication implements Authentication {
    private final List<GrantedAuthority> authorities;
    private final boolean isAuthenticated;
    private final String password;

    public CustomAuthentication(List<GrantedAuthority> authorities, String password) {
        this.password = password;
        this.authorities = authorities;
        this.isAuthenticated = password == null;
    }

    public static CustomAuthentication unAuthenticated(String password) {
        return new CustomAuthentication(Collections.emptyList(), password);
    }

    public static CustomAuthentication authenticated() {
        return new CustomAuthentication(AuthorityUtils.createAuthorityList("ROLE_CUSTOM"), null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return getName();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Don't let set here..");
    }

    @Override
    public String getName() {
        return "Mr Custom 😎";
    }

    public String getPassword() {
        return password;
    }
}

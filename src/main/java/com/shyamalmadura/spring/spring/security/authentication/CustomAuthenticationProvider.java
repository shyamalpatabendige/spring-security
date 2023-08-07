package com.shyamalmadura.spring.spring.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final List<String> passwords;

    public CustomAuthenticationProvider(List<String> passwords) {
        this.passwords = passwords;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var authRequest = (CustomAuthentication) authentication;
        var password = authRequest.getPassword();
        if (!passwords.contains(password)) {
            throw new BadCredentialsException("You are not Mr. Custom 😎 🛑, ");
        }

        return CustomAuthentication.authenticated();

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.isAssignableFrom(authentication);
    }
}

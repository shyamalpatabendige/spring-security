package com.shyamalmadura.spring.spring.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        if ("x-username".equals(username)) {
            return UsernamePasswordAuthenticationToken.authenticated(
                    "x-username",
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_X_USER")
            );
        }
        return null; // Allow other auth providers to handle
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

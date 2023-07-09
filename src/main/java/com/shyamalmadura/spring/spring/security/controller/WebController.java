package com.shyamalmadura.spring.spring.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WebController {

    @GetMapping("/")
    public String publicPage() {
        return "Hello, Welcome everyone to the Application";
    }

    @GetMapping("/secured")
    public String securedPage(Authentication authentication) {
        return "Hello [" +
                getName(authentication)
                + "], you are allowed to access this page";
    }

    private String getName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail) //Can be extended to get user details as well
                .orElseGet(authentication::getName);
    }
}

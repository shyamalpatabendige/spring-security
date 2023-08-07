package com.shyamalmadura.spring.spring.security.filter;

import com.shyamalmadura.spring.spring.security.authentication.CustomAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CustomFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "x-custom-password";

    private final AuthenticationManager authenticationManager;

    public CustomFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("🆗 Hello from the CUSTOM filter..");
        // 0. Should execute the filter?

        if (!Collections.list(request.getHeaderNames()).contains(HEADER_NAME)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Authentication Decision
        var password = request.getHeader(HEADER_NAME);
        var authRequest = CustomAuthentication.unAuthenticated(password);

        try {
            //OK 👍
            var authentication = authenticationManager.authenticate(authRequest);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ae) {
            //KO 👎
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().println(ae.getMessage());
        }
    }
}

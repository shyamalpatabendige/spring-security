package com.shyamalmadura.spring.spring.security.filter;

import com.shyamalmadura.spring.spring.security.authentication.CustomAuthentication;
import org.springframework.http.HttpStatus;
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
        if (!"cx-pword".equals(password)) {
            //KO 👎
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().println("You are not Mr. Custom 😎 🛑");
        } else {
            //OK 👍
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(new CustomAuthentication());
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        }
    }
}

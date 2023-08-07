package com.shyamalmadura.spring.spring.security.config;

import com.shyamalmadura.spring.spring.security.authentication.CustomAuthenticationProvider;
import com.shyamalmadura.spring.spring.security.filter.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authConfig -> {
                    authConfig.antMatchers("/").permitAll();
                    authConfig.antMatchers("/error").permitAll();
                    authConfig.antMatchers("/favicon.ico").permitAll();
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults()) // US1
                .oauth2Login(Customizer.withDefaults()) // US2
                .addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class) // US3
                .authenticationProvider(new CustomAuthenticationProvider()) //US4
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return  new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}password") // pain text password
                        .authorities("ROLE_user")
                        .build()
        );
    }
}

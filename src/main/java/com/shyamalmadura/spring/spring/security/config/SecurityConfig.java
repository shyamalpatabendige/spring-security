package com.shyamalmadura.spring.spring.security.config;

import com.shyamalmadura.spring.spring.security.authentication.CustomAuthenticationProvider;
import com.shyamalmadura.spring.spring.security.authentication.SampleAuthenticationProvider;
import com.shyamalmadura.spring.spring.security.filter.CustomFilter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEventPublisher publisher
    ) throws Exception {
        // Will be removed with Spring 5.8x and simplified
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationEventPublisher(publisher);

        var authManager = new ProviderManager(new CustomAuthenticationProvider(List.of("cx-pword", "cx-passwd")));
        authManager.setAuthenticationEventPublisher(publisher);

        return http
                .authorizeRequests(authConfig -> {
                    authConfig.antMatchers("/").permitAll();
                    authConfig.antMatchers("/error").permitAll();
                    authConfig.antMatchers("/favicon.ico").permitAll();
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults()) // US1
                .oauth2Login(Customizer.withDefaults()) // US2
                .addFilterBefore(new CustomFilter(authManager), UsernamePasswordAuthenticationFilter.class) // US3, //US4
                .authenticationProvider(new SampleAuthenticationProvider()) //US4
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}password") // pain text password
                        .authorities("ROLE_user")
                        .build()
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.out.println(String.format("🎉 SUCCESS [%s] %s",
                    event.getAuthentication().getClass().getSimpleName(),
                    event.getAuthentication().getName()));
        };
    }
}

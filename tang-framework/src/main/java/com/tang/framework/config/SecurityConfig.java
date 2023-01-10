package com.tang.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 * spring security 配置
 *
 * @author Tang
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .authorizeHttpRequests().anyRequest().authenticated();

//                .formLogin((form) -> form
//                        .loginPage("/login").permitAll()
//                );
        return http.build();
    }

}

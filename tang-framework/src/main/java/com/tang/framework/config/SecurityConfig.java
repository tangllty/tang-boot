package com.tang.framework.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.tang.framework.security.filter.JwtAuthenticationTokenFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 配置
 *
 * @author Tang
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final List<AuthenticationProvider> providers;

    public SecurityConfig(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint authenticationEntryPoint,
            JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, LogoutSuccessHandler logoutSuccessHandler) throws Exception {
        return httpSecurity
            // Disabled Spring CSRF protection
            // .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            // 禁用 CSRF
            .csrf(csrf -> csrf.disable())
            // 启用 CORS
            .cors(withDefaults())
            // 设置 session 管理器为无状态
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 设置未登陆过滤器
            .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/third-party/oauth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs").permitAll()
                .requestMatchers("/websocket/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/captcha/**").permitAll()
                .requestMatchers("/druid/**").permitAll()
                .requestMatchers("/actuator/**", "/admin/applications").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(options -> options.disable()))
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler))
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(providers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

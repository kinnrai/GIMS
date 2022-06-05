package com.jin.config;

import com.jin.config.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 金磊
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] URL_WHITE_LIST = {"/login", "logout", "favicon.ico","/sys/register/**","/sys/info/**"};
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final UserDetailsService userDetailsService;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Autowired
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler, JwtLogoutSuccessHandler jwtLogoutSuccessHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, UserDetailsService userDetailsService) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilter(jwtAuthenticationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

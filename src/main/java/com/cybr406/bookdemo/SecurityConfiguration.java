package com.cybr406.bookdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Customize access here using the http object
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/**").permitAll()  // Allow all read-only requests using GET
                .anyRequest().authenticated()                                 // Any other requests (POST, PUT)
                .and()
                .csrf().disable()                                             // Disable Cross Site Request Forgery protection
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)       // Never use http session to obtain a SecurityContext
                .and()
                .httpBasic();                                                 // Continue to use HTTP Basic for authentication
    }

}

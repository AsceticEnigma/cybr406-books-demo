package com.cybr406.bookdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.h2.console.enabled}")
    boolean h2ConsoleEnabled;

    @Autowired
    DataSource dataSource;

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager() {
        // We plan to store and retrieve user details from a database.
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // A DelegatingPasswordEncoder can handle numerous different password encodings based on the prefix
        // of the password.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    User.UserBuilder userBuilder() {
        // Having a User.UserBuilder instance as an @Bean that can be @Autowired can be helpful.
        PasswordEncoder passwordEncoder = passwordEncoder();
        User.UserBuilder users = User.builder();
        users.passwordEncoder(passwordEncoder::encode);
        return users;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configure authentication to use the database.
        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure security for the h2 console. This should only ever happen in dev environments.
        if (h2ConsoleEnabled) {
            http
                    .authorizeRequests()
                    .antMatchers("/h2-console", "/h2-console/**").permitAll();

            // By default, frame options is set to DENY. The h2 console is rendered in a frame, however. Changing to
            // SAMEORIGIN allows the content to appear since it is originating from the same server. DENY is a better
            // option for prod, where the h2 console should be disabled anyhow.
            // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options
            http.headers().frameOptions().sameOrigin();
        }

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

package com.cybr406.bookdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

  @Autowired
  JdbcUserDetailsManager userDetailsManager;

  @Autowired
  User.UserBuilder builder;
  
  @PostMapping("/signup")
  public void register(@RequestBody SignUp signUp) {
    userDetailsManager
        .createUser(builder
            .username(signUp.getUsername())
            .password(signUp.getPassword())
            .roles("AUTHOR")
            .build());
  }
  
}

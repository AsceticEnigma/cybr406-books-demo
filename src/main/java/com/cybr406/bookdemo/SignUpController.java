package com.cybr406.bookdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignUpController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private User.UserBuilder userBuilder;

    @PostMapping("/signup")
    public ResponseEntity<Author> SignUp(@Valid @RequestBody SignUp signUp) {
        if (userDetailsManager.userExists(signUp.getUsername())) {
            throw new UsernameAlreadyExistsException(String.format(
                    "The username %s is already in use.", signUp.getUsername()));
        }

        userDetailsManager.createUser(userBuilder
                .username(signUp.getUsername())
                .password(signUp.getPassword())
                .roles("AUTHOR")
                .build());

        Author author = new Author();
        author.setUsername(signUp.getUsername());
        author.setName(signUp.getName());
        author.setBio(signUp.getBio());

        return new ResponseEntity<>(authorRepository.save(author), HttpStatus.CREATED);
    }

}

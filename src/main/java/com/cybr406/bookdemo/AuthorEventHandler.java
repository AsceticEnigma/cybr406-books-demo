package com.cybr406.bookdemo;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class AuthorEventHandler {

    @HandleBeforeCreate
    public void handleAuthorCreate(Author author) {
        System.out.println("About to create a author named " + author.getName());
    }

    @HandleAfterCreate
    public void handleAuthorCreated(Author author) {
        System.out.println(String.format("Newly created author %s has id %d", author.getName(), author.getId()));
    }
    
//    @HandleBeforeSave
//    @PreAuthorize("hasRole('ROLE_ADMIN') or #author.username == authentication.principal.username")
//    public void handleBeforeSave(Author author) {
//        System.out.println("Saved!");
//    }

}

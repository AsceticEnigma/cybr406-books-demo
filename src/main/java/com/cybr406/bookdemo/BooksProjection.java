package com.cybr406.bookdemo;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "books", types = Author.class)
public interface BooksProjection {

    String getUsername();

    String getName();

    String getBio();

    List<Book> getBooks();

}

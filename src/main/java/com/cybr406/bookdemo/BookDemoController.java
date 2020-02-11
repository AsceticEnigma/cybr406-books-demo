package com.cybr406.bookdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@RestController
public class BookDemoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        // KeyHolder is used to capture newly generated IDs.
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            // Prepared statements guard again SQL injection attacks.
            // The "?" characters are place holders for real values.
            PreparedStatement ps = con.prepareStatement(
                    "insert into author (username, name, bio) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            // Provide the actual values for each place holder.
            // The first argument is the place holder position.
            // The second argument is the actual value.
            ps.setString(1, author.getUsername());
            ps.setString(2, author.getName());
            ps.setString(3, author.getBio());
            return ps;
        }, keyHolder);

        // Extract the generated id.
        author.setId((long) keyHolder.getKey());

        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public List<Author> findAuthors() {
        return jdbcTemplate.query(
                "select * from author",
                (resultSet, rowNum) -> {
                    // This row mapper is written as a lambda and converts
                    // raw database results into Java objects.
                    Author author = new Author();
                    author.setId(resultSet.getLong("id"));
                    author.setUsername(resultSet.getString("username"));
                    author.setName(resultSet.getString("name"));
                    author.setBio(resultSet.getString("bio"));
                    return author;
                });
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> findAuthor(@PathVariable Long id) {
        Author result = jdbcTemplate.queryForObject(
                "select * from author where id = ?",
                (resultSet, rowNum) -> {
                    Author author = new Author();
                    author.setId(resultSet.getLong("id"));
                    author.setUsername(resultSet.getString("username"));
                    author.setName(resultSet.getString("name"));
                    author.setBio(resultSet.getString("bio"));
                    return author;
                },
                id); // This id will be used in the place holder.

        return result == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(result, HttpStatus.OK);
    }

}

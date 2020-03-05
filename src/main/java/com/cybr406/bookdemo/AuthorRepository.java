package com.cybr406.bookdemo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByUsername(@Param("username") String username);

    Page<Author> findByNameContains(@Param("name") String name, Pageable pageable);

}

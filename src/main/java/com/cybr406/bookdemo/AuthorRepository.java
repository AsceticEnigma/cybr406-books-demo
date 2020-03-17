package com.cybr406.bookdemo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Method-level security can be added to repositories by overriding methods from the parent class.
    // Alternatively, method-level security can be added to event handlers.
//    @PreAuthorize("hasRole('ROLE_ADMIN') or #author.username == authentication.principal.username")
//    @Override
//    <S extends Author> S save(@Param("author") S entity);

    Optional<Author> findByUsername(@Param("username") String username);

    Page<Author> findByNameContains(@Param("name") String name, Pageable pageable);

}

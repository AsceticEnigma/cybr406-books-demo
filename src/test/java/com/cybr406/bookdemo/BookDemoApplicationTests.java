package com.cybr406.bookdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.UUID;

@SpringBootTest
@Transactional
class BookDemoApplicationTests {

	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	void testThatBooksCanHaveManyAuthors() {
		for (int i = 0; i < 40; i++) {
			System.out.println(String.format("%s,%s,%s",
					"random_user_" + UUID.randomUUID(),
					"random_name_" + UUID.randomUUID(),
					"random_bio_" + UUID.randomUUID()));
		}
		
		System.out.println();
		
		for (int i = 0; i < 40; i++) {
			System.out.println("random_book_" + UUID.randomUUID());
		}
		
		System.out.println();
		
		for (int i = 0; i < 40; i++) {
			System.out.println((i + 3) + "," + (i + 8));
		}
	}

	@Test
	void testPasswordEncoder() {
		System.out.println(passwordEncoder.encode("admin"));
		System.out.println(passwordEncoder.encode("password"));
		System.out.println(passwordEncoder.encode("password"));
	}

}

package com.cybr406.bookdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookDemoApplicationTests {

	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Test
	@Transactional
	void testThatBooksCanHaveManyAuthors() {
		List<Book> frankhBooks = bookRepository.findAllByAuthorsId(1L);
		List<Book> philipkdBooks = bookRepository.findAllByAuthorsId(2L);

		assertTrue(frankhBooks.stream().anyMatch(book -> book.getTitle().equals("The Amalgamation")));
		assertTrue(philipkdBooks.stream().anyMatch(book -> book.getTitle().equals("The Amalgamation")));
		
		Author frankh = authorRepository.findById(1L).orElseThrow(NoSuchElementException::new);
		Author philipkd = authorRepository.findById(2L).orElseThrow(NoSuchElementException::new);
		
		// Test adding creating a book and mapping it to authors from the book side of the relationship
		
		Book testBook = new Book();
		testBook.setTitle("Testing for Fun and Profit");
		testBook.setAuthors(Arrays.asList(frankh, philipkd));
		bookRepository.save(testBook);
		
		frankhBooks = bookRepository.findAllByAuthorsId(1L);
		philipkdBooks = bookRepository.findAllByAuthorsId(2L);
		assertTrue(frankhBooks.stream().anyMatch(book -> book.getTitle().equals("Testing for Fun and Profit")));
		assertTrue(philipkdBooks.stream().anyMatch(book -> book.getTitle().equals("Testing for Fun and Profit")));

		// Test adding creating a book and mapping it to authors from the author side of the relationship
		
		Book anotherTestBook = new Book();
		testBook.setTitle("The Caffeine Monster");
		frankh.getBooks().add(anotherTestBook);
		philipkd.getBooks().add(anotherTestBook);
		
		authorRepository.save(frankh);
		authorRepository.save(philipkd);

		frankhBooks = bookRepository.findAllByAuthorsId(1L);
		philipkdBooks = bookRepository.findAllByAuthorsId(2L);
		assertTrue(frankhBooks.stream().anyMatch(book -> book.getTitle().equals("The Caffeine Monster")));
		assertTrue(philipkdBooks.stream().anyMatch(book -> book.getTitle().equals("The Caffeine Monster")));
	}

}

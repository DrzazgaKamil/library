package com.example.demo;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseInitializerTest {

    private DatabaseInitializer databaseInitializer;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        databaseInitializer = new DatabaseInitializer(authorRepository, bookRepository);
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author("John Doe");
        Author savedAuthor = new Author("John Doe");
        savedAuthor.setId(1);

        when(authorRepository.save(author)).thenReturn(savedAuthor);

        Author createdAuthor = databaseInitializer.createAuthor(author);

        assertNotNull(createdAuthor);
        assertNotNull(createdAuthor.getId());
        assertEquals("John Doe", createdAuthor.getName());
    }
}

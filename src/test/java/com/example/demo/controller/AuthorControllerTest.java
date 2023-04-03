package com.example.demo.controller;

import com.example.demo.DatabaseInitializer;
import com.example.demo.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    private DatabaseInitializer databaseInitializer;
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        databaseInitializer = mock(DatabaseInitializer.class);
        authorController = new AuthorController(databaseInitializer);
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author("John Doe");
        author.setId(1);
        when(databaseInitializer.getAuthorById(1)).thenReturn(Optional.of(author));

        ResponseEntity<Author> response = authorController.getAuthorByIdEntity(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
    }
}

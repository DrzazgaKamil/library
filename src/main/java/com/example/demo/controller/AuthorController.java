package com.example.demo.controller;

import com.example.demo.DatabaseInitializer;
import com.example.demo.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final DatabaseInitializer databaseInitializer;

    @Autowired
    public AuthorController(DatabaseInitializer databaseInitializer) {
        this.databaseInitializer = databaseInitializer;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    System.out.println("Error: " + ex.getMessage());
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + ex.getMessage());
}

    @ApiOperation(value = "Create a new author", notes = "Provide the required author information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author created successfully"),
            @ApiResponse(code = 400, message = "Invalid input data provided")
    })
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(databaseInitializer.createAuthor(author));
    }

    @ApiOperation(value = "Get an author by ID", notes = "Provide the an author ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author found"),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorByIdEntity(@PathVariable Integer id) {
        return databaseInitializer.getAuthorById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get all authors", notes = "Get a list of all authors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authors found"),
            @ApiResponse(code = 404, message = "Authors not found")
    })    
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(databaseInitializer.getAllAuthors());
    }

    @ApiOperation(value = "Get all books for an author", notes = "Provide an author ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books found"),
            @ApiResponse(code = 404, message = "Author not found")
    })  
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getAllBooksByAuthor(@PathVariable Integer id) {
        return databaseInitializer.getAuthorById(id)
                .map(author -> {
                    List<BookDTO> bookDTOs = author.getBooks().stream()
                            .map(book -> new BookDTO(book.getId(), book.getTitle(), book instanceof FictionBook ? "fiction" : "book"))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(bookDTOs);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    

    @ApiOperation(value = "Update an author", notes = "Provide an author ID and updated author information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author updated successfully"),
            @ApiResponse(code = 400, message = "Invalid data provided"),
            @ApiResponse(code = 404, message = "Author not found")
    })  
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        author.setId(id);
        return ResponseEntity.ok(databaseInitializer.updateAuthor(author));
    }

    @ApiOperation(value = "Delete an author", notes = "Provide an author id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Author deleted successfully"),
            @ApiResponse(code = 404, message = "Author not found")
    })  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        databaseInitializer.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}


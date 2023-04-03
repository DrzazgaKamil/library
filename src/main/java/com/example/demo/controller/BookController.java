package com.example.demo.controller;

import com.example.demo.DatabaseInitializer;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController{
    private final DatabaseInitializer databaseInitializer;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(DatabaseInitializer databaseInitializer, BookRepository bookRepository) {
        this.databaseInitializer = databaseInitializer;
        this.bookRepository = bookRepository;
    }

    @ApiOperation(value = "Create a new book", notes = "Provide the required book information, an author ID, and the book type (BOOK or FICTIONBOOK)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book created successfully"),
            @ApiResponse(code = 400, message = "Invalid input data provided"),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @PostMapping("/author/{authorId}")
    public ResponseEntity<Book> createBook(@PathVariable Integer authorId, @RequestBody Book book) {
        return databaseInitializer.getAuthorById(authorId)
                .map(author -> {
                    if (book instanceof FictionBook) {
                        FictionBook fictionBook = (FictionBook) book;
                        fictionBook.setAuthor(author);
                        return ResponseEntity.ok(databaseInitializer.createBook(fictionBook));
                    } else {
                        book.setAuthor(author);
                        return ResponseEntity.ok(databaseInitializer.createBook(book));
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    

    @ApiOperation(value = "Get a book by ID", notes = "Provide a book ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @ApiOperation(value = "Get all books", notes = "Get a list of all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books found"),
            @ApiResponse(code = 404, message = "Books not found")
    })    
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    

    @ApiOperation(value = "Update a book", notes = "Provide an author ID and updated book information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book updated successfully"),
            @ApiResponse(code = 400, message = "Invalid data provided"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    book.setId(id);
                    book.setAuthor(existingBook.getAuthor());
                    return ResponseEntity.ok(bookRepository.save(book));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    

    @ApiOperation(value = "Delete a book", notes = "Provide a book id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book deleted successfully"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    
}
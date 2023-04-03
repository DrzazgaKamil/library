package com.example.demo;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private int authorIdCounter = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public DatabaseInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    

    @PostConstruct
    public void init() {
        Author author = new Author("John Doe");
        createAuthor(author);
    }

    public Author createAuthor(Author author) {
        author.setId(authorIdCounter++);
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(int id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }

    public Book createBook(Book book) {
        Author author = book.getAuthor();
        if (author != null) {
            author.getBooks().add(book);
        }
        return bookRepository.save(book);
    }
    

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}

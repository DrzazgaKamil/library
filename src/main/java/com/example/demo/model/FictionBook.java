package com.example.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("fiction")
public class FictionBook extends Book {
    private String genre;

    public FictionBook() {
    }

    public FictionBook(String title, Author author, String genre) {
        super(title);
        setAuthor(author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

package com.example.demo.model;

public class BookDTO {
    private Integer id;
    private String title;
    private String type;

    public BookDTO() {
    }

    public BookDTO(Integer id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

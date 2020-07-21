package com.lib.webLibrary.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    private String FIO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    @ManyToMany
    @JoinTable (name="Book_Author",
            joinColumns=@JoinColumn (name="author_id"),
            inverseJoinColumns=@JoinColumn(name="book_id"))
    private List<Book> books;

    public Author() { }

    public Author(String FIO, User user) {
        this.FIO = FIO;
        this.user=user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

package com.lib.webLibrary.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    private String book_title,publishing_house;
    private Year year_publishing;
    private String place_publication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(mappedBy="id_Book", fetch= FetchType.EAGER)
    private List<BookInstance> inst;


    @ManyToMany
    @JoinTable (name="Book_Author",
            joinColumns=@JoinColumn (name="book_id"),
            inverseJoinColumns=@JoinColumn(name="author_id"))
    private List<Author> authors;

    public Book() {
    }

    public Book(String book_title, String publishing_house, Year year_publishing, String place_publication,User user) {
        this.book_title = book_title;
        this.publishing_house = publishing_house;
        this.year_publishing = year_publishing;
        this.place_publication = place_publication;
        this.user=user;
    }

    public String getAuthorName(){
        return user !=null?user.getUsername():"none";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public void setPublishing_house(String publishing_house) {
        this.publishing_house = publishing_house;
    }

    public Year getYear_publishing() {
        return year_publishing;
    }

    public void setYear_publishing(Year year_publishing) {
        this.year_publishing = year_publishing;
    }

    public String getPlace_publication() {
        return place_publication;
    }

    public void setPlace_publication(String place_publication) {
        this.place_publication = place_publication;
    }


    public List<BookInstance> getInst() {
        return inst;
    }

    public void setInst(List<BookInstance> inst) {
        this.inst = inst;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}

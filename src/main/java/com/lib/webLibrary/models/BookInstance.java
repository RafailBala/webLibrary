package com.lib.webLibrary.models;


import javax.persistence.*;


@Entity
public class BookInstance {
    @Id
   // @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="id_Book", referencedColumnName = "id")
    private Book id_Book;


    @ManyToOne (optional=false)
    @JoinColumn (name="id_Department", referencedColumnName = "id")
    private Department id_Department;

    //для указания пользователя
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    public String getAuthorName(){
        return user !=null?user.getUsername():"none";
    }

    public BookInstance() { }
    public BookInstance(Long id, Book id_Book, Department id_Department,User user) {
        this.id = id;
        this.id_Book = id_Book;
        this.id_Department = id_Department;
        this.user=user;
    }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Book getId_Book() {
        return id_Book;
    }
    public void setId_Book(Book id_Book) {
        this.id_Book = id_Book;
    }
    public Department getId_Department() {
        return id_Department;
    }
    public void setId_Department(Department id_Department) {
        this.id_Department = id_Department;
    }
}

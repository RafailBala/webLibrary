package com.lib.webLibrary.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {

    @Id
    private Long id;
    @NotNull
    private String department_name;

    @OneToMany(mappedBy="id_Department", fetch=FetchType.EAGER)
    private List<BookInstance> bookInstances;

    //для указания пользователя
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    public String getAuthorName(){
        return user !=null?user.getUsername():"none";
    }

    public Department() { }
    public Department(Long id, String department_name,User user) {
        this.id = id;
        this.department_name = department_name;
        this.user=user;
    }
    public List<BookInstance> getBookInstances() { return bookInstances; }
    public void setBookInstances(List<BookInstance> bookInstances) { this.bookInstances = bookInstances; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDepartment_name() {
        return department_name;
    }
    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

}

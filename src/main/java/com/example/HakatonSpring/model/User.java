package com.example.HakatonSpring.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Integer id_user;
    private String name;
    private String email;
    private String pass;
    private String dataOfBith;
    private String avatar;
    private String text;
    private Integer balance;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

//    @OneToMany
//    private List<History> history;
//
//    @OneToMany
//    private List<Statistics> statistics;

    public User() {
    }

    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public User(Integer id_user, String name, String email, String dataOfBith, String avatar, String text, Integer balance) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.dataOfBith = dataOfBith;
        this.avatar = avatar;
        this.text = text;
        this.balance = balance;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataOfBith() {
        return dataOfBith;
    }

    public void setDataOfBith(String dataOfBith) {
        this.dataOfBith = dataOfBith;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}

package com.example.HakatonSpring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_history;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;
    private Games games;
    private String money;
    private Date time;

    public History(User user, Games games, String money, Date time) {
        this.user = user;
        this.games = games;
        this.money = money;
        this.time = time;
    }

    public History() {
    }

    public Integer getId_history() {
        return id_history;
    }

    public void setId_history(Integer id_history) {
        this.id_history = id_history;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

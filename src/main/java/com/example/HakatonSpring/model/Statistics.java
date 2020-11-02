package com.example.HakatonSpring.model;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_his;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;
    private Games games;
    private Integer win;
    private Integer lose;
    private String earning;

    public Statistics(User user, Games games, Integer win, Integer lose, String earning) {
        this.user = user;
        this.games = games;
        this.win = win;
        this.lose = lose;
        this.earning = earning;
    }

    public Statistics() {
    }

    public Integer getId_his() {
        return id_his;
    }

    public void setId_his(Integer id_his) {
        this.id_his = id_his;
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

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }
}

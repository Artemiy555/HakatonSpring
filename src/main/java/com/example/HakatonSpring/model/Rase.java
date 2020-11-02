package com.example.HakatonSpring.model;

import java.util.HashSet;
import java.util.Set;

public class Rase {

    private Set<Double> doubles = new HashSet<>();
    private boolean winLose;

    public Rase() {
    }

    public Rase(Set<Double> doubles, boolean winLose) {
        this.doubles = doubles;
        this.winLose = winLose;
    }

    public Set<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(Set<Double> doubles) {
        this.doubles = doubles;
    }

    public boolean isWinLose() {
        return winLose;
    }

    public void setWinLose(boolean winLose) {
        this.winLose = winLose;
    }
}

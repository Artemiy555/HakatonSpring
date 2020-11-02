package com.example.HakatonSpring.model;

public class AvailableGame {

	private int id;
	private String name;
	private int bet;

	public AvailableGame(int id, String name,int bet) {
		this.id = id;
		this.name = name;
		this.bet = bet;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getBet() {
		return bet;
	}
}

package com.example.HakatonSpring.model;

public class PlayerJoinMessage {

	private String player;

	public PlayerJoinMessage() {
	}

	public PlayerJoinMessage(String player) {
		this.player = player;
	}

	public String getPlayer() {
		return player;
	}
}
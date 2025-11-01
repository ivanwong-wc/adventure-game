package com.example.models;

public class BuyResponse {

    private final String message;
    private  Player player;

    public BuyResponse(String message, Player player) {
        this.message = message;
        this.player = player;
    }

    public String getMessage() {
        return message;
    }
    public Player getPlayer() {
        return player;
    }

}

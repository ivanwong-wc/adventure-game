package com.example.models;

public class BuyResponse {

    private final String error;
    private final Player player;

    public BuyResponse(String error, Player player) {
        this.error = error;
        this.player = player;
    }

    public String getError() {
        return error;
    }

    public Player getPlayer() {
        return player;
    }

}

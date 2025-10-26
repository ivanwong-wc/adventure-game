package com.example.models;

import java.util.List;

public class EventResponse {

    private final int playerHp;
    private final int playerAttack;
    private final List<String> inventory;
    private final int gold;
    private final String message;

    public EventResponse(String message, int playerHp, int playerAttack, List<String> inventory, int gold) {
        this.message = message;
        this.playerHp = playerHp;
        this.playerAttack = playerAttack;
        this.inventory = inventory;
        this.gold = gold;
    }
    public String getMessage() {
        return message;
    }
    public int getPlayerHp() {
        return playerHp;
    }
    public int getPlayerAttack() {
        return playerAttack;
    }
    public List<String> getInventory() {
        return inventory;               
    }
    public int getGold() {
        return gold;
    }
}

package com.example.models;

public class AttackResponse {

    private final int playerHp;
    private final int playerMp;
    private final int enemyHp;
    private final String error;
    private final boolean victory;
    private final int gold;

    public AttackResponse(String error, int playerHp, int playerMp, int enemyHp, boolean victory, int gold) {
        this.error = error;
        this.playerHp = playerHp;
        this.playerMp = playerMp;
        this.enemyHp = enemyHp;
        this.victory = victory;
        this.gold = gold;
    }
}

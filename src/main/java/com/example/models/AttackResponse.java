package com.example.models;

public class AttackResponse {

    private final int playerHp;
    private final int playerMp;
    private final int enemyHp;
    private final String message;
    private final boolean victory;
    private final int gold;

    public AttackResponse(String message, int playerHp, int playerMp, int enemyHp, boolean victory, int gold) {
        this.message = message;
        this.playerHp = playerHp;
        this.playerMp = playerMp;
        this.enemyHp = enemyHp;
        this.victory = victory;
        this.gold = gold;
    }
        
    public String getMessage() {
        return message;
    }
    public int getPlayerHp() {
        return playerHp;
    }
    public int getPlayerMp() {
        return playerMp;
    }
    public int getEnemyHp() {
        return enemyHp;
    }
    public boolean isVictory() {
        return victory;
    }
    public int getGold() {
        return gold;
    }
}

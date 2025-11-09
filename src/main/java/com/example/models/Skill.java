package com.example.models;

public class Skill {
    private int damage;
    private int mpCost;

    public Skill(int damage, int mpCost) {
        this.damage = damage;
        this.mpCost = mpCost;
    }

    public int getDamage() { return damage; }
    public int getMpCost() { return mpCost; }
}
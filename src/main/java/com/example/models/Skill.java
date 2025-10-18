package com.example.models;

public class Skill {
    private int damage;
    private int mpCost;
    private int maxUses;

    public Skill(int damage, int mpCost, int maxUses) {
        this.damage = damage;
        this.mpCost = mpCost;
        this.maxUses = maxUses;
    }

    // Getter methods
    public int getDamage() { return damage; }
    public int getMpCost() { return mpCost; }
    public int getMaxUses() { return maxUses; }
    public void setMaxUses(int maxUses) { this.maxUses = maxUses; }
}
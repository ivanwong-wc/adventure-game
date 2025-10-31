package com.example.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.example.models.*;

public class Villager implements Character {
    private int attack =4;
    private int hp =80;
    private int mp =70 ;
    private int gold =50;
    private double luck =0.6;
    private List<String> inventory = new ArrayList<>(List.of("HealthPotion", "MpPotion"));
    private Map<String, Skill> skills = new HashMap<>() {{
        put("Punch", new Skill(0, 0));
        put("Fiststorm", new Skill(10, 5));
        put("FuryBlow", new Skill(25, 10));
        put("Haymaker", new Skill(35, 20));
    }};

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getMp() {
        return mp;
    }

    @Override
    public int getGold() {
        return gold;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public List<String> getInventory() {
        return inventory;
    }

    @Override
    public Map<String,Skill> getSkills() {
        return skills;
    }
}

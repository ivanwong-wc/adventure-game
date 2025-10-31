package com.example.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.example.models.*;

public class Knight implements Character {
    private int attack =5;
    private int hp =100;
    private int mp =60 ;
    private int gold =20;
    private double luck =0.3;
    private List<String> inventory = new ArrayList<>(List.of("StrengthPotion"));
    private Map<String, Skill> skills = new HashMap<>() {{
        put("Punch", new Skill(0, 0));
        put("Slash", new Skill(10, 3));
        put("Power Strike", new Skill(20, 8));
        put("Whirlwind", new Skill(35, 15));
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

package com.example.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.example.models.*;

public class Wizard implements Character {
    private int attack =3;
    private int hp =60;
    private int mp =100 ;
    private int gold =20;
    private double luck =0.3;
    private List<String> inventory = new ArrayList<>(List.of("MpPotion"));
    private Map<String, Skill> skills = new HashMap<>() {{
        put("Punch", new Skill(0, 0));
        put("Avis", new Skill(15, 3));
        put("Expelliarmus", new Skill(30, 17));
        put("Sectum Sempra", new Skill(45, 27));
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

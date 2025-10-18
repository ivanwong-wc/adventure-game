package com.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Knight implements Player {
    private int attack;
    private int hp;
    private int mp;
    private int gold ;
    private double luck;
    private List<String> inventory;
    private Map<String, Skill> skills;
    private int buff=0;

    @Override
    public void setUp() {
        this.attack = 5;
        this.hp = 100;
        this.mp = 50;
        this.gold = 20;
        this.luck = 0.5;
        this.inventory = new ArrayList<>();
        this.skills = new HashMap<>();
        inventory.add("StrengthPotion");
        skills.put("Slash", new Skill(5, 0, 999)); // basic damage, mpCost, maxUses
        skills.put("Power Strike", new Skill(10, 5, 20));
        skills.put("Whirlwind", new Skill(15, 15, 5));  
    }

    @Override
    public Map<String, Integer> attackEnemy(String skill, Enemy enemy) {
        return Action.attackEnemy(skill, enemy, this, skills);
    }

    @Override
    public int takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        System.out.println("Current HP of Knight: " + hp);
        return hp;
    }

    @Override
    public int useItem(String item) {
        int result = switch (item) {
            case "HealthPotion" -> Action.useHealthPotion( this);
            case "StrengthPotion" -> Action.useAttackPotion( this);
            case "MpPotion" -> Action.useMpPotion( this);
            default -> -1;
        };
        return result;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getBuff() {
        return buff;
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

    @Override
    public double changeLuck(double d) {
        this.luck += d;
        if (this.luck < 0) this.luck = 0;
        if (this.luck > 1) this.luck = 1;
        return luck;
    }
    @Override
    public int changeAttack(int i) {
        this.attack += i;
        return this.attack;
    }
    @Override
    public int changeMp(int i) {
        this.mp += i;
        return this.mp;
    }
    @Override
    public int changeHp(int i) {
        this.hp = Math.max(0,this.hp+i);
        return this.hp;
    }
    @Override
    public int changeGold(int g) {
        this.gold += g;
        return this.gold;
    }
    @Override
    public int changeBuff(String item) {
        if (item.equals("StrengthPotion")) {
            this.buff += 5;
        }else{
            this.buff =0;
        }
        return this.buff;
    }
}

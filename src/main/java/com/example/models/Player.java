package com.example.models;
import java.util.List;
import java.util.Map;
import com.example.character.Character;
import com.example.character.CharacterSetUp;

public class Player{
    private int attack;
    private int hp;
    private int mp;
    private int gold ;
    private double luck;
    private List<String> inventory;
    private Map<String, Skill> skills;
    private int buff=0;
    private Character character;

    public void setUpCharacter(String characterType) {
        CharacterSetUp.setupPlayer(this, characterType);
    }

    public void setUpData(Character character) {
        this.character = character;
        this.attack = character.getAttack();
        this.hp = character.getHp();
        this.mp = character.getMp();
        this.gold = character.getGold();
        this.luck = character.getLuck();
        this.inventory = character.getInventory();
        this.skills = character.getSkills();
    }

    public AttackResponse attackEnemy(String skill, Enemy enemy) {
        return Action.attackEnemy(skill, enemy, this, skills);
    }

    public int takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        System.out.println("Current HP of Knight: " + hp);
        return hp;
    }

    public Player useItem(String item) {
        System.out.println("Using item: " + item);
        switch (item) {
            case "HealthPotion" -> Action.useHealthPotion( this);
            case "StrengthPotion" -> Action.useAttackPotion( this);
            case "MpPotion" -> Action.useMpPotion( this);
            default -> System.out.println("Item not found!");
        }
        return this;
    }

    public int getAttack() {
        return attack;
    }

    public int getBuff() {
        return buff;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public int getGold() {
        return gold;
    }

    public double getLuck() {
        return luck;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public Map<String,Skill> getSkills() {
        return skills;
    }

    public double changeLuck(double d) {
        this.luck += d;
        if (this.luck < 0) this.luck = 0;
        if (this.luck > 1) this.luck = 1;
        return luck;
    }
 
 
    public int changeAttack(int i) {
        this.attack += i;
        return this.attack;
    }

    public int changeMp(int i) {
        this.mp += i;
        return this.mp;
    }

    public int changeHp(int i) {
        this.hp = Math.max(0,this.hp+i);
        return this.hp;
    }

    public int changeGold(int g) {
        this.gold += g;
        return this.gold;
    }

    public int changeBuff(String item) {
        if (item.equals("StrengthPotion")) {
            this.buff += 5;
        }else{
            this.buff =0;
        }
        return this.buff;
    }
}

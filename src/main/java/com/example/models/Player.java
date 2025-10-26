package com.example.models;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface  Player {
    public void setUp();
    public int getAttack();
    public int getBuff();
    public int getHp();
    public int getMp(); 
    public int getGold();
    public double getLuck();
    public List<String> getInventory();
    public Map<String,Skill> getSkills();
    public AttackResponse  attackEnemy(String skill, Enemy enemy);
    public int takeDamage(int damage);
    public double changeLuck(double d);
    public int changeAttack(int i);
    public int changeMp(int i);
    public int changeHp(int i);
    public int changeGold(int g);
    public Player useItem(String item);
    public int changeBuff(String item);
}

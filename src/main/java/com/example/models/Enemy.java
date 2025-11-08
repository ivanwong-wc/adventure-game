package com.example.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class Enemy {

    private int attack, hp;

    public void setUp(int level,int attackMultiplier, int hpMultiplier) {
        this.attack = (10 + level) * (attackMultiplier + 1);
        this.hp = (25 + level) * (hpMultiplier + 1);
    }

    public Map<String, Integer> attackPlayer(Player player, Boolean evades) {
        Map<String, Integer> result = new HashMap<>();
        if (evades) {
            System.out.println("Player evades the attack!");
            result.put("evades", player.getHp());
            return result;
        } else {
            int ack = this.attack;
            int remainingHp = player.takeDamage(ack);
            System.out.println("Player takes " + ack + " damage! HP: " + remainingHp);
            if(remainingHp == 0){
                result.put("defeat", 0);
                System.out.println("Player defeated!");
                player.changeBuff("reset");
                player = null;
            } else {
                result.put("Player Hp", remainingHp);
            }
            return result;
        }
    }

    public int takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        System.out.println("New HP: " + hp);
        return hp;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }
}

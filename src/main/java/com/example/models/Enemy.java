package com.example.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;


@Component
public class Enemy {

    private int attack, hp;

    public void setUp(int level) {
        Random rand = new Random();
        this.attack = (10 + level) * (rand.nextInt(3) + 1);
        this.hp = (25 + level) * (rand.nextInt(3) + 1);
    }

    public Map<String, Integer> attackPlayer(Player player) {
        Random rand = new Random();
        Map<String, Integer> result = new HashMap<>();
        if (player.getLuck() * rand.nextDouble() > 0.5 * rand.nextDouble() + 0.1) {
            System.out.println("Player evades the attack!");
            result.put("evades", player.getHp());
            return result;
        } else {
            int ack = (int) Math.ceil(this.attack * (1 - player.getLuck() * rand.nextDouble()));
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

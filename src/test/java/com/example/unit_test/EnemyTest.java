package com.example.unit_test;
import com.example.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import com.example.models.Enemy;

public class EnemyTest {

     static class PlayerStub extends Player {
        private int hp = 100;

        @Override
        public int getHp() {
            return this.hp;
        }

        public void setHp(int h) {
            this.hp =h;
        }    
        @Override
        public int takeDamage(int i) {
            this.hp = Math.max(0, this.hp - i);
            return hp;
        }

        @Override
        public int changeBuff(String s) {
            return 0;
        }
    }

    @Test
    public void testEnemyCreation() {
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        assertEquals(104, enemy.getHp());
        assertEquals(33, enemy.getAttack());
    }

    @Test
    public void testTakeDamageStillAlive() {
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        enemy.takeDamage(20);
        assertEquals(84, enemy.getHp());
    }   

    @Test
    public void testTakeDamageDead() {
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        enemy.takeDamage(2000000000);
        assertEquals(0, enemy.getHp());
    }   

    @Test
    public void testAttackPlayerEvades() {
        Map<String, Integer> result;
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        PlayerStub player = new PlayerStub();
        result =enemy.attackPlayer(player, true);
        assertEquals(100, result.get("evades"));
    }

    @Test
    public void testAttackPlayerSuccessfulButNotKill() {
        Map<String, Integer> result;
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        PlayerStub player = new PlayerStub();
        result =enemy.attackPlayer(player, false);
        assertEquals(67, result.get("Player Hp"));
    }

    @Test
    public void testAttackPlayerSuccessfulKilled() {
        Map<String, Integer> result;
        Enemy enemy = new Enemy();
        enemy.setUp(1,2,3);
        PlayerStub player = new PlayerStub();
        player.setHp(1);
        result = enemy.attackPlayer(player, false);
        assertEquals(0, result.get("defeat"));
    }
}
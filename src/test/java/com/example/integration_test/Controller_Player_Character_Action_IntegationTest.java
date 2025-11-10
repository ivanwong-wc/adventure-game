package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.AttackResponse;
import com.example.models.Enemy;
import com.example.models.Player;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Controller_Player_Character_Action_IntegationTest {
    static class EnemyStub extends Enemy {
        private int hp = 16;

        @Override
        public int getHp() {
            return hp;
        }

        @Override
        public int takeDamage(int damage) {
            hp = Math.max(0, hp - damage);
            return hp;
        }

        @Override
        public Map<String, Integer> attackPlayer(Player player, Boolean evades) {
            player.changeHp(-10);
            return null;
        }

        public void setHp(int i){
            this.hp =i;
        }
    }

    private void injectField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Controller controller;
    private Player player;
    private EnemyStub enemy;

    @BeforeEach
    public void setUp() throws Exception {
        player = new Player();
        enemy = new EnemyStub();
        controller = new Controller();
        injectField(controller, "player", player);
        injectField(controller, "enemy", enemy);
    }

    @Test
    public void testGetPlayer() {
        player = controller.getPlayer("knight");
        assertEquals(100, player.getHp());
        assertEquals(60, player.getMp());
        assertEquals(5, player.getAttack());
        assertEquals(20, player.getGold());
        assertEquals(0.3, player.getLuck());
    }

    @Test
    public void testUseItem_ChangesPlayerHp() {
        controller.getPlayer("knight");
        player.getInventory().add("HealthPotion");
        player = controller.useItem("HealthPotion");
        assertEquals(120, player.getHp());
    }

    @Test
    public void testUseItem_ChangesPlayerMp() {
        controller.getPlayer("knight");
        player.getInventory().add("MpPotion");
        player = controller.useItem("MpPotion");
        assertEquals(75, player.getMp());
    }

    @Test
    public void testUseItem_ChangesPlayerStrength() {
        controller.getPlayer("knight");
        player.getInventory().add("StrengthPotion");
        player = controller.useItem("StrengthPotion");
        assertEquals(5, player.getBuff());
    }

    @Test
    public void testAttackEnemy_Win() {
        controller.getPlayer("knight");
        player.getInventory().add("StrengthPotion");
        controller.useItem("StrengthPotion");
        AttackResponse response = controller.attackEnemy("Power Strike");
        assertNotNull(response);
        assertTrue(response.isVictory());
        assertEquals(0, response.getEnemyHp());
        assertEquals(100, response.getPlayerHp());
        assertTrue(response.getGold() > 20);
        assertEquals(0, player.getBuff());
    }

    @Test
    public void testAttackEnemy_StillAlive() {
        controller.getPlayer("knight");
        AttackResponse response = controller.attackEnemy("Punch");
        assertNotNull(response);
        assertFalse(response.isVictory());
        assertEquals(11, response.getEnemyHp());
        assertEquals(90, response.getPlayerHp());
        assertEquals(20, response.getGold());
        assertEquals(0, player.getBuff());
    }

    @Test
    public void testAttackEnemy_PlayerDead() {
        controller.getPlayer("knight"); 
        enemy.setHp(10000);
        int originalEnemyHp = enemy.getHp();
        for(int i=0;i<9;++i){
            controller.attackEnemy("Punch");
        }
        AttackResponse response = controller.attackEnemy("Punch");
        assertNotNull(response);
        assertFalse(response.isVictory()); 
        assertFalse( originalEnemyHp <=  response.getEnemyHp()); 
        assertEquals( 0, response.getPlayerHp()); 
        assertEquals( 20, response.getGold()); 
        assertEquals(0, player.getBuff()); 
    }

}


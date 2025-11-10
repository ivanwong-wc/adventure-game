package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.AttackResponse;
import com.example.models.Player;

import java.lang.reflect.Field;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Controller_Player_Character_IntegationTest {

    static class PlayerStub extends Player {
        
        @Override
        public Player useItem(String s) {
            switch (s) {
                case "HealthPotion" -> changeHp(30);
                case "StrengthPotion" -> changeAttack(5);
                case "MpPotion" -> changeMp(30);
            }
            return this;
        }

    }

    private void injectField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Controller controller;
    private PlayerStub playerStub;

    @BeforeEach
    public void setUp() throws Exception {
        playerStub = new PlayerStub();
        controller = new Controller();
        injectField(controller, "player", playerStub);
    }

    @Test
    public void testGetPlayer() {
        Player result = controller.getPlayer("knight");
        assertSame(playerStub, result);
        assertEquals(100, playerStub.getHp());
        assertEquals(60, playerStub.getMp());
        assertEquals(5, playerStub.getAttack());
        assertEquals(20, playerStub.getGold());
        assertEquals(0.3, playerStub.getLuck());
    }

    @Test
    public void testUseItem_ChangesPlayerHp() {
        controller.getPlayer("knight");
        Player result = controller.useItem("HealthPotion");
        assertSame(playerStub, result);
        assertEquals(130, playerStub.getHp());
    }

    @Test
    public void testUseItem_ChangesPlayerMp() {
        controller.getPlayer("knight");
        Player result = controller.useItem("MpPotion");
        assertSame(playerStub, result);
        assertEquals(90, playerStub.getMp());
    }

    @Test
    public void testUseItem_ChangesPlayerStrength() {
        controller.getPlayer("knight");
        Player result = controller.useItem("StrengthPotion");
        assertSame(playerStub, result);
        assertEquals(10, playerStub.getAttack());
    }

    @Test
    public void testAttackEnemy() {
        controller.getPlayer("knight");
        AttackResponse response = controller.attackEnemy("Slash");
        assertNotNull(response);
    }


}
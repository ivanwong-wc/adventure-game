package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.AttackResponse;
import com.example.models.Player;
import com.example.models.Skill;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Controller_Player_IntegationTest {

    static class PlayerStub extends Player {
        private String characterType;

        public PlayerStub() {
            setPrivateField("hp", 100);
            setPrivateField("mp", 100);
            setPrivateField("attack", 10);
            setPrivateField("buff", 0);
            setPrivateField("gold", 10);
            setPrivateField("luck", 0.3);
            setPrivateField("inventory", new ArrayList<String>());
            setPrivateField("skills", new HashMap<String, Skill>() {
                {
                    put("Punch", new Skill(10, 10));
                }
            });
        }

        @Override
        public void setUpCharacter(String characterType) {
            this.characterType = characterType;
            if ("knight".equalsIgnoreCase(characterType)) {
                setPrivateField("hp", 99);
                setPrivateField("mp", 99);
                setPrivateField("attack", 9);
                setPrivateField("gold", 99);
            }
        }

        public String getCharacterType() {
            return characterType;
        }

        @Override
        public Player useItem(String s) {
            // original: (but it claims to use the static of Action class cannot not
            // override it, so rewrite here, in next files will combined it and test)
            // switch (s) {
            // case "HealthPotion" -> Action.useHealthPotion( this);
            // case "StrengthPotion" -> Action.useAttackPotion( this);
            // case "MpPotion" -> Action.useMpPotion( this);
            // default -> System.out.println("Item not found!");

            switch (s) {
                case "HealthPotion" -> changeHp(30);
                case "StrengthPotion" -> changeAttack(5);
                case "MpPotion" -> changeMp(30);
            }
            return this;
        }

        private void setPrivateField(String fieldName, Object value) {
            try {
                Field field = Player.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        assertEquals("knight", playerStub.getCharacterType());
        assertEquals(99, playerStub.getHp());
        assertEquals(99, playerStub.getMp());
        assertEquals(9, playerStub.getAttack());
        assertEquals(99, playerStub.getGold());
    }

    @Test
    public void testUseItem_ChangesPlayerHp() {
        controller.getPlayer("knight");
        Player result = controller.useItem("HealthPotion");
        assertSame(playerStub, result);
        assertEquals(129, playerStub.getHp());
    }

    @Test
    public void testUseItem_ChangesPlayerMp() {
        controller.getPlayer("knight");
        Player result = controller.useItem("MpPotion");
        assertSame(playerStub, result);
        assertEquals(129, playerStub.getMp());
    }

    @Test
    public void testUseItem_ChangesPlayerStrength() {
        controller.getPlayer("knight");
        Player result = controller.useItem("StrengthPotion");
        assertSame(playerStub, result);
        assertEquals(14, playerStub.getAttack());
    }

    @Test
    public void testAttackEnemy() {
        controller.getPlayer("knight");
        AttackResponse response = controller.attackEnemy("Slash");
        assertNotNull(response);
    }


}
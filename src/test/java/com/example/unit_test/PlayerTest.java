package com.example.unit_test;

import com.example.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import com.example.character.Character;
public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
        Character character = new CharacterStub();
        player.setUpData(character);
    }

    private void setPrivateField(String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = Player.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(player, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class CharacterStub implements Character {
        private int attack = 100;
        private int hp = 100;
        private int mp = 100;
        private int gold = 100;
        private double luck = 0.5;
        private List<String> inventory = new ArrayList<>();
        private Map<String, Skill> skills = new HashMap<>(){};

        @Override public int getAttack() { return attack; }
        @Override public int getHp() { return hp; }
        @Override public int getMp() { return mp; }
        @Override public int getGold() { return gold; }
        @Override public double getLuck() { return luck; }
        @Override public List<String> getInventory() { return inventory; }
        @Override public Map<String, Skill> getSkills() { return skills; }
        public CharacterStub() {
            inventory.add("StrengthPotion");
            skills.put("Slash", new Skill(10, 3));
        }
    }

    @Test
    public void testSetUpCharacter() {
        setPrivateField("attack", 999);
        player.setUpCharacter("knight");
        assertNotEquals(999, player.getAttack());
    }

    @Test
    public void testSetUpData() {
        Character character = new CharacterStub();
        player.setUpData(character);
        assertEquals(100, player.getAttack());
        assertEquals(100, player.getHp());
        assertEquals(100, player.getMp());
        assertEquals(100, player.getGold());
        assertEquals(0.5, player.getLuck());
        assertTrue(player.getInventory().contains("StrengthPotion"));
        assertTrue(player.getSkills().containsKey("Slash"));
    }

    static class EnemyStub extends Enemy {
        EnemyStub() {
        }
    }

    @Test
    public void testAttackEnemy() {
        EnemyStub enemy = new EnemyStub();
        AttackResponse response = player.attackEnemy("Slash", enemy);
        assertNotNull(response);
    }

    @Test
    public void testTakeDamage() {
        setPrivateField("hp", 100);
        player.takeDamage(10);
        assertEquals(90, player.getHp());
    }

    @Test
    public void testTakeDamageDead() {
        setPrivateField("hp", 100);
        player.takeDamage(1000);
        assertEquals(0, player.getHp());
    }

    @Test
    public void testCahngeLuck() {
        setPrivateField("luck", 0.5);
        player.changeLuck(0.1);
        assertEquals(0.6, player.getLuck());
    }

    @Test
    public void testCahngeLuckLargerThan1() {
        setPrivateField("luck", 0.5);
        player.changeLuck(1);
        assertEquals(1, player.getLuck());
    }

    @Test
    public void testCahngeLuckSmallerThan1() {
        setPrivateField("luck", 0.5);
        player.changeLuck(-0.6);
        assertEquals(0, player.getLuck());
    }

    @Test
    public void testCahngeAttack() {
        setPrivateField("attack", 15);
        player.changeAttack(1);
        assertEquals(16, player.getAttack());
    }

    @Test
    public void testChangeMp() {
        setPrivateField("mp", 15);
        player.changeMp(15);
        assertEquals(30, player.getMp());
    }

    @Test
    public void testChangeHp() {
        setPrivateField("hp", 100);
        player.changeHp(100);
        assertEquals(200, player.getHp());
    }

    @Test
    public void testChangeHpSmallerThan0() {
        setPrivateField("hp", 100);
        player.changeHp(-1000);
        assertEquals(0, player.getHp());
    }

    @Test
    public void testChangeGold() {
        setPrivateField("gold", 100);
        player.changeGold(100);
        assertEquals(200, player.getGold());
    }

    @Test
    public void testChangeGoldSmallerThan0() {
        setPrivateField("gold", 100);
        player.changeGold(-1000);
        assertEquals(0, player.getGold());
    }

    @Test
    public void testChangeBuff() {
        setPrivateField("buff", 0);
        player.useItem("StrengthPotion");
        assertEquals(5, player.getBuff());
    }

    @Test
    public void testChangeBuffNotSuccessful() {
        player.getInventory().remove(0);
        setPrivateField("buff", 0);
        player.useItem("StrengthPotion");
        assertNotEquals(5, player.getBuff());
    }

    @Test
    public void testUseItem_HealthPotion() {
        setPrivateField("hp", 100);
        player.getInventory().add("HealthPotion");
        player.useItem("HealthPotion");
        assertNotEquals(100, player.getHp());
    }

    @Test
    public void testUseItem_StrengthPotion() {
        setPrivateField("buff", 10);
        player.useItem("StrengthPotion");
        assertNotEquals(0, player.getBuff());
    }

    @Test
    public void testUseItem_MpPotion() {
        setPrivateField("mp", 50);
        player.getInventory().add("MpPotion");
        player.useItem("MpPotion");
        assertNotEquals(50, player.getMp());
    }

    @Test
    public void testUseItem_unknown() {
        setPrivateField("hp", 100);
        player.useItem("unknown");
        assertEquals(100, player.getHp());
    }
}
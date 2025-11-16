package com.example.unit_test;

import com.example.models.Action;
import com.example.models.AttackResponse;
import com.example.models.Enemy;
import com.example.models.Player;
import com.example.models.Skill;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    static class PlayerStub extends Player {
        public PlayerStub() {
            setPrivateField("hp", 100);
            setPrivateField("mp", 100);
            setPrivateField("attack", 10);
            setPrivateField("buff", 0);
            setPrivateField("gold", 10);
            setPrivateField("luck", 0.3);
            setPrivateField("inventory", new java.util.ArrayList<String>());
            setPrivateField("skills", new java.util.HashMap<String, Skill>() {{
                put("Punch", new Skill(10, 10));};
            });
        }

        private void setPrivateField(String fieldName, Object value) {
            try {
                java.lang.reflect.Field field = Player.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    static class EnemyStub  extends Enemy{
        public EnemyStub() {
            setPrivateField("hp", 80);
            setPrivateField("attack", 20);
        }

        private void setPrivateField(String fieldName, Object value) {
            try {
                java.lang.reflect.Field field = Enemy.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(this, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private PlayerStub playerStub = new PlayerStub();
    private EnemyStub enemyStub = new EnemyStub();
    @Test
    public void testCheckEvades() {
        Set<Boolean> result = new HashSet<>();
        while(result.size() < 2){
            result.add(Action.checkEvades(playerStub));
        }
        assertTrue(result.contains(true));
        assertTrue(result.contains(false));
    }

    @Test
    public void testExtraMoney() {
       Set<Integer> result = new HashSet<>();
        while(result.size() < 11){
            result.add(Action.extraMoney());
        }
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.contains(5));
        assertTrue(result.contains(6));
        assertTrue(result.contains(7));
        assertTrue(result.contains(8));
        assertTrue(result.contains(9));
        assertTrue(result.contains(10));
    }

    @Test
    public void testUseHealthPotionUnsuccessful() {
        Action.useHealthPotion(playerStub);
        assertEquals(-1, Action.useHealthPotion(playerStub));
    }

    @Test
    public void testUseHealthPotionSuccessful() {
        playerStub.getInventory().add("HealthPotion");
        Action.useHealthPotion(playerStub);
        assertEquals(120, playerStub.getHp());
    }

    @Test
    public void testUseAttackPotionUnsuccessful() {
        Action.useAttackPotion(playerStub);
        assertEquals(-1, Action.useAttackPotion(playerStub));
    }

    @Test
    public void testUseAttackPotionSuccessful() {
        playerStub.getInventory().add("StrengthPotion");
        Action.useAttackPotion(playerStub);
        assertEquals(15, (playerStub.getAttack()+ playerStub.getBuff()));
    }

    @Test
    public void testUseMpPotionUnsuccessful() {
        Action.useMpPotion(playerStub);
        assertEquals(-1, Action.useMpPotion(playerStub));
    }

    @Test
    public void testUseMpPotionSuccessful() {
        playerStub.getInventory().add("MpPotion");
        Action.useMpPotion(playerStub);
        assertEquals(115,playerStub.getMp());
    }


    @Test
    public void testAttackEnemySkillNotFound() {
        AttackResponse ar =  Action.attackEnemy("aaaaaaaaaaa", enemyStub, playerStub, playerStub.getSkills());
        assertEquals("Skill not found!", ar.getMessage()); 
    }

    @Test
    public void testAttackEnemyMpNotEnough() {
        playerStub.changeMp(-100);
        AttackResponse ar =  Action.attackEnemy("Punch", enemyStub, playerStub, playerStub.getSkills());
        assertEquals("Not enough MP!", ar.getMessage()); 
    }
    
    @Test
    public void testAttackEnemySuccessfulKill() {
        enemyStub.takeDamage(79); 
        playerStub.changeMp(100);
        int original_Player_Hp = playerStub.getHp();
        int original_gold = playerStub.getGold();
        AttackResponse ar =  Action.attackEnemy("Punch", enemyStub, playerStub, playerStub.getSkills());
        assertNull( ar.getMessage()); 
        assertEquals(original_Player_Hp, ar.getPlayerHp());
        assertEquals(0, ar.getEnemyHp()); 
        assertTrue(ar.isVictory());
        assertTrue(ar.getGold()>original_gold);
    }

    @Test
    public void testAttackEnemySuccessfulButNotKill() {
        playerStub.changeMp(1000);
        Set<String> messages = new HashSet<>();
        while (messages.size() < 2 ){
            int original_Player_Hp = playerStub.getHp();
            int original_gold = playerStub.getGold();
            AttackResponse ar = Action.attackEnemy("Punch", enemyStub, playerStub, playerStub.getSkills());

            assertTrue("success Evades".equals(ar.getMessage()) || "hurt".equals(ar.getMessage()));
            assertTrue((original_Player_Hp - enemyStub.getAttack()) == ar.getPlayerHp() || original_Player_Hp == ar.getPlayerHp());
            assertTrue(ar.getEnemyHp() > 0);
            assertFalse(ar.isVictory());
            assertEquals(original_gold, ar.getGold());

            messages.add(ar.getMessage());
        }
        assertTrue(messages.contains("success Evades"));
        assertTrue(messages.contains("hurt"));
    }
}
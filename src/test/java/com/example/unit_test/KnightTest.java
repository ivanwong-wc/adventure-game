package com.example.unit_test;

import com.example.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    @Test
    public void testKnight() {
        Player player = new Player();
        player.setUpCharacter("knight");

        assertEquals(5, player.getAttack(), "Knight attack should be 5");
        assertEquals(100, player.getHp(), "Knight HP should be 100");
        assertEquals(60, player.getMp(), "Knight MP should be 60");
        assertEquals(20, player.getGold(), "Knight gold should be 20");
        assertEquals(0.3, player.getLuck(), 1e-9, "Knight luck should be 0.3");
        assertTrue(player.getInventory().contains("StrengthPotion"), "Knight should have StrengthPotion");
        assertTrue(player.getSkills().containsKey("Punch"), "Knight should have Punch skill");
        assertTrue(player.getSkills().containsKey("Slash"), "Knight should have Slash skill");
        assertTrue(player.getSkills().containsKey("Power Strike"), "Knight should have Power Strike skill");
        assertTrue(player.getSkills().containsKey("Whirlwind"), "Knight should have Whirlwind skill");
        assertEquals(4, player.getSkills().size(), "Knight should have 4 skills");
    }

}
package com.example.unit_test;

import com.example.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    @Test
    public void knightSetup_setsPlayerStats() {
        Player player = new Player();
        player.setUpCharacter("knight");

        assertEquals(5, player.getAttack(), "Knight attack should be 5");
        assertEquals(100, player.getHp(), "Knight HP should be 100");
        assertEquals(60, player.getMp(), "Knight MP should be 60");
        assertEquals(20, player.getGold(), "Knight gold should be 20");
        assertEquals(0.3, player.getLuck(), 1e-9, "Knight luck should be 0.3");
        assertTrue(player.getInventory().contains("StrengthPotion"), "Knight should have StrengthPotion");
        assertTrue(player.getSkills().containsKey("Slash"), "Knight should have Slash skill");
        assertEquals(4, player.getSkills().size(), "Knight should have 4 skills");
    }

    @Test
    public void wizardSetup_setsPlayerStats() {
        Player player = new Player();
        player.setUpCharacter("wizard");

        assertEquals(3, player.getAttack(), "Wizard attack should be 3");
        assertEquals(60, player.getHp(), "Wizard HP should be 60");
        assertEquals(100, player.getMp(), "Wizard MP should be 100");
        assertEquals(20, player.getGold(), "Wizard gold should be 20");
        assertEquals(0.3, player.getLuck(), 1e-9, "Wizard luck should be 0.3");
        assertTrue(player.getInventory().contains("MpPotion"), "Wizard should have MpPotion");
        assertTrue(player.getSkills().containsKey("Avis"), "Wizard should have Avis skill");
        assertEquals(4, player.getSkills().size(), "Wizard should have 4 skills");
    }

    @Test
    public void villagerSetup_setsPlayerStats() {
        Player player = new Player();
        player.setUpCharacter("villager");

        assertEquals(4, player.getAttack(), "Villager attack should be 4");
        assertEquals(80, player.getHp(), "Villager HP should be 80");
        assertEquals(70, player.getMp(), "Villager MP should be 70");
        assertEquals(50, player.getGold(), "Villager gold should be 50");
        assertEquals(0.6, player.getLuck(), 1e-9, "Villager luck should be 0.6");
        assertTrue(player.getInventory().contains("HealthPotion"), "Villager should have HealthPotion");
        assertTrue(player.getInventory().contains("MpPotion"), "Villager should have MpPotion");
        assertTrue(player.getSkills().containsKey("Fiststorm"), "Villager should have Fiststorm skill");
        assertEquals(4, player.getSkills().size(), "Villager should have 4 skills");
    }

}
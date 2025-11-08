package com.example.unit_test;

import com.example.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WizardTest {

    @Test
    public void testWizard() {
        Player player = new Player();
        player.setUpCharacter("wizard");

        assertEquals(3, player.getAttack(), "Wizard attack should be 3");
        assertEquals(60, player.getHp(), "Wizard HP should be 60");
        assertEquals(100, player.getMp(), "Wizard MP should be 100");
        assertEquals(20, player.getGold(), "Wizard gold should be 20");
        assertEquals(0.3, player.getLuck(), 1e-9, "Wizard luck should be 0.3");
        assertTrue(player.getInventory().contains("MpPotion"), "Wizard should have MpPotion");
        assertTrue(player.getSkills().containsKey("Punch"), "Wizard should have Punch skill");
        assertTrue(player.getSkills().containsKey("Avis"), "Wizard should have Avis skill");
        assertTrue(player.getSkills().containsKey("Expelliarmus"), "Wizard should have Expelliarmus skill");
        assertTrue(player.getSkills().containsKey("Sectum Sempra"), "Wizard should have Sectum Sempra skill");
        assertEquals(4, player.getSkills().size(), "Wizard should have 4 skills");
    }

}
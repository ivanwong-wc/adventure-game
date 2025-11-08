
package com.example.unit_test;

import com.example.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VillagerTest {

    @Test
    public void testVillage() {
        Player player = new Player();
        player.setUpCharacter("villager");

        assertEquals(4, player.getAttack(), "Villager attack should be 4");
        assertEquals(80, player.getHp(), "Villager HP should be 80");
        assertEquals(70, player.getMp(), "Villager MP should be 70");
        assertEquals(50, player.getGold(), "Villager gold should be 50");
        assertEquals(0.6, player.getLuck(), 1e-9, "Villager luck should be 0.6");
        assertTrue(player.getInventory().contains("HealthPotion"), "Villager should have HealthPotion");
        assertTrue(player.getInventory().contains("MpPotion"), "Villager should have MpPotion");
        assertTrue(player.getSkills().containsKey("Punch"), "Villager should have Punch skill");
        assertTrue(player.getSkills().containsKey("Fiststorm"), "Villager should have Fiststorm skill");
        assertTrue(player.getSkills().containsKey("FuryBlow"), "Villager should have FuryBlow skill");
        assertTrue(player.getSkills().containsKey("Haymaker"), "Villager should have Haymaker skill");
        assertEquals(4, player.getSkills().size(), "Villager should have 4 skills");
    }

}
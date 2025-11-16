package com.example.unit_test;

import com.example.models.Player;
import com.example.character.CharacterSetUp;
import com.example.character.Character;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import com.example.character.Knight;
import com.example.character.Wizard;
import com.example.character.Villager;

public class CharacterSetUpTest {

    static class PlayerStub extends Player {
        private Character character = null;
        @Override
        public void setUpData(Character character) {
            super.setUpData(character);  
            this.character = character;
        }
    }
    @Test
    public void testCharacterSetUpKnight() {
        PlayerStub player = new PlayerStub();
        CharacterSetUp.setupPlayer(player, "knight");
        assertTrue(player.character instanceof Knight);
    }
    @Test
    public void testCharacterSetUpWizard() {
        PlayerStub player = new PlayerStub();
        CharacterSetUp.setupPlayer(player, "wizard");
        assertTrue(player.character instanceof Wizard);
    }
    @Test
    public void testCharacterSetUpVillager() {
        PlayerStub player = new PlayerStub();
        CharacterSetUp.setupPlayer(player, "villager");
        assertTrue(player.character instanceof Villager);
    }
    @Test
public void testCharacterSetUpUnknown() {
    PlayerStub player = new PlayerStub();
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> CharacterSetUp.setupPlayer(player, "unknown"),
        "Expected setupPlayer to throw IllegalArgumentException for unknown type"
    );
    assertEquals("Unknown character: unknown", exception.getMessage());
}
}
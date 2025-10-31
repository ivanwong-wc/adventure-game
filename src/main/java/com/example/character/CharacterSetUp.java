package com.example.character;

import com.example.models.Player;

public class CharacterSetUp {
    public static void setupPlayer(Player player, String characterType) {
        Character character = switch (characterType) {
            case "knight" -> new Knight();
            case "wizard" -> new Wizard();
            case "villager" -> new Villager();
            default -> throw new IllegalArgumentException("Unknown character: " + characterType);
        };
        player.setUpData(character);
    }
}
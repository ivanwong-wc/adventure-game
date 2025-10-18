package com.example.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Event {

    public static final Map<String, String> eventList = new HashMap<>();
    private static final Random rand = new Random();

    static {
        eventList.put("charity", "A child asks for 5 gold? Give it?");
        eventList.put("merchant", "Suspicious merchant ask you buy something. Accept?");
        eventList.put("treasure", "Have a old chest here. Open it?");
        eventList.put("fairy", "Find a HealthPotion on the ground. Take it?");
    }

    public static String getRandomEvent() {
        String[] keys = eventList.keySet().toArray(new String[0]);
        return keys[rand.nextInt(keys.length)];
    }

    public static Map<String, Object> chooseEvent(String eventKey, String choice, Player player) {
        Map<String, Object> result = new HashMap<>();
        result.put("event", eventList.get(eventKey));
        result.put("choice", choice);
        if (choice.equals("no") ) {
            result.put("success", false);
            return result;
        }
        result.put("success", switch (eventKey) {
            case "charity" ->
                handleCharity(player);
            case "merchant" ->
                handleMerchant(player);
            case "treasure" ->
                handleTreasure(player);
            case "fairy" ->
                handleFairy(player);
            default ->
                false;
        });
        return result;
    }

    private static String handleCharity(Player player) {
        if (player.getGold() < 5) {
            return "Not enough gold!";
        }
        player.changeGold(-5);
        if (rand.nextDouble() < 0.7) {
            player.changeLuck(0.1);
            return "+0.1 Luck!";
        } else {
            return "Child runs away...";
        }
    }

    private static String handleMerchant(Player player) {
        if (player.getGold() >= 10) {
            if (rand.nextDouble() < 0.3 && player.getGold() >= 10) {
                player.changeGold(-10);
                player.getInventory().add("HealthPotion");
                return "Got HealthPotion!";
            } else if (rand.nextDouble() < 0.7 && !player.getInventory().isEmpty()) {
                player.getInventory().remove(rand.nextInt(player.getInventory().size()));
                player.getInventory().add("MpPotion");
                return "Got MpPotion but lost an item!";
            } else if (rand.nextDouble() > 0.8 && player.getGold() >= 10) {
                player.changeGold(-10);
                player.changeAttack(2);
                return "Loss 10gold! Add Atkack 2!";
            } else {
                player.changeGold(-10);
                return "Fake! Lost 10g!";
            }
        }
        return "Not enough gold!";
    }

    private static String handleTreasure(Player player) {
        Random rand = new Random();
        if (rand.nextDouble() < 0.6) {
            player.changeGold(15);
            return "+15 Gold!";
        } else {
            player.takeDamage(5);
            return "-5 HP! Trap!";
        }
    }

    private static String handleFairy(Player player) {
        Random rand = new Random();
        if (rand.nextDouble() < 0.4 + player.getLuck() * 0.5) {
            player.getInventory().add("HealthPotion");
            return "Got HealthPotion!";
        } else {
            player.changeAttack(-2);
            return "Greedy Human! As a beacutiful fairy, -2 ATK permanent!";
        }
    }
}

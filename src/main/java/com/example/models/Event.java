package com.example.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Event {

    public static final Map<String, String> eventList = new HashMap<>();
    private static Random rand = new Random();

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

    public static EventResponse chooseEvent(String eventKey, String choice, Player player) {
        if (choice.equals("no") ) {
            return new EventResponse(null, player.getHp(), player.getAttack(), player.getInventory(), player.getGold());
        }
        double rateOfSuccess = rand.nextDouble();
        String result = switch (eventKey) {
            case "charity" ->
                handleCharity(player,rateOfSuccess);
            case "merchant" ->
                handleMerchant(player,rateOfSuccess);
            case "treasure" ->
                handleTreasure(player,rateOfSuccess);
            case "fairy" ->
                handleFairy(player,rateOfSuccess);
            default -> "Invalid event.";
        };
        return new EventResponse(result, player.getHp(), player.getAttack(), player.getInventory(), player.getGold());
    }

    private static String handleCharity(Player player, double rateOfSuccess) {
        if (player.getGold() < 5) {
            return "Not enough gold!";
        }
        player.changeGold(-5);
        if (rateOfSuccess < 0.4 + player.getLuck() * 0.5) {
            player.changeLuck(0.1);
            return "+0.1 Luck!";
        } else {
            return "Child runs away...";
        }
    }

    private static String handleMerchant(Player player, double rateOfSuccess) {
        if (player.getGold() >= 10) {
            if (rateOfSuccess < 0.2 ) {
                player.changeGold(-10);
                player.getInventory().add("HealthPotion");
                return "Got HealthPotion!";
            } else if (rateOfSuccess < 0.5 && !player.getInventory().isEmpty()) {
                player.getInventory().remove(rand.nextInt(player.getInventory().size()));
                player.getInventory().add("MpPotion");
                return "Got MpPotion but lost an item!";
            } else if (rateOfSuccess < 0.8) {
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

    private static String handleTreasure(Player player, double rateOfSuccess) {
        if (rateOfSuccess < 0.6) {
            player.changeGold(15);
            return "+15 Gold!";
        } else {
            player.takeDamage(5);
            return "-5 HP! Trap!";
        }
    }

    private static String handleFairy(Player player, double rateOfSuccess) {
        if (rateOfSuccess < 0.4 + player.getLuck() * 0.5) {
            player.getInventory().add("HealthPotion");
            return "Got HealthPotion!";
        } else {
            player.changeAttack(-2);
            return "Greedy Human! As a beacutiful fairy, -2 ATK permanent!";
        }
    }


    //Create for testing purpose
    public static void setRandom(Random random) {
        Event.rand = random;
    }
    public static void resetRandom() {
        Event.rand = new Random();
    }
}

package com.example.models;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class Shop {

    private Map<String, Integer> item = new HashMap<>();

    public void setItemInShop() {
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            switch (rand.nextInt(10)) {
                case 1,8 -> this.item.put("Attack2ATK", 20);
                case 3,7 -> this.item.put("HealthPotion", 10);
                case 4,5 -> this.item.put("StrengthPotion", 20);
                case 2,6 -> this.item.put("MpPotion", 15);
                default -> this.item.put("Health10HP", 20);
            }
        }
    }


    public Map<String, Integer> getItemList() {
        return item;
    }

    public void cleanShop() {
        this.item.clear();
    }

    public int buyItem(String itemName, Player player) {
        if (!item.containsKey(itemName)) {
            System.out.println("Item not found!");
            return -1;
        }
        int price = item.get(itemName);
        if (player.getGold() < price) {
            System.out.println("Not enough gold!");
            return -1;
        }
        player.changeGold(-price);
        player.getInventory().add(itemName);
        System.out.println("Bought " + itemName + "! Remaining Gold: " + player.getGold());
        return player.getGold();
    }
}

package com.example.models;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Shop {

    private Map<String, Integer> item = new HashMap<>();

    public void setItemInShop() {
        item.clear();
        while (item.size()<2){
            Random rand = new Random();
            switch (rand.nextInt(10)) {
                case 1,9 -> this.item.put("Raise2ATK", 20);
                case 0,8 -> this.item.put("Recover10MP", 15);
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

    public BuyResponse buyItem(String itemName, Player player) {
        if (!item.containsKey(itemName)) {
            return new BuyResponse("Item not found!", player);
        }
        int price = item.get(itemName);
        if (player.getGold() < price) {
            return new BuyResponse("Not enough gold!", player);
        }
        player.changeGold(-price);
        switch (itemName) {
            case "Raise2ATK" -> player.changeAttack(2);
            case "Health10HP" -> player.changeHp(10);
            case "Recover10MP" -> player.changeMp(10);
            default -> player.getInventory().add(itemName);
        }
        return new BuyResponse(null, player);
    }

}

package com.example.controller;

import com.example.models.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
public class Controller {

    private Player player;
    private Enemy enemy = new Enemy();
    private final Shop shop = new Shop();


    @PostMapping("/player/{character}")
    public Map<String, Object> getPlayer(@PathVariable String character) {
        player = switch (character.toLowerCase()) {
            case "knight" -> new Knight();
            default -> throw new IllegalArgumentException("Unknown character: " + character);
        };
        player.setUp();
        System.out.println("Player created: " + character);
        return Map.of(
            "attack", player.getAttack(),
            "hp", player.getHp(),
            "mp", player.getMp(),
            "gold", player.getGold(),
            "luck", player.getLuck(),
            "inventory", player.getInventory(),
            "skills", player.getSkills()
        );
    }

    @PostMapping("/enemy/{level}")
    public Map<String, Integer> createEnemy(@PathVariable int level) {
        enemy.setUp();
        System.out.println("Enemy created at level: " + level);
        return Map.of(
            "hp", enemy.getHp(),
             "attack", enemy.getAttack()
        );
    }

    @PostMapping("/attack/{skill}")
    public Map<String, Integer> attackEnemy(@PathVariable String skill) {
        return player.attackEnemy(skill, enemy);
    }

    @PostMapping("/items/{item}")
    public int useItem(@PathVariable String item) {
        return player.useItem(item);
    }
    
    @GetMapping("/shop")
    public Map<String, Integer> listItem(){
        shop.setItemInShop();
        return shop.getItemList();
    }

    @PostMapping("/shop/{item}")
    public int buyItem(@PathVariable String item) {
        int goldLeft = shop.buyItem(item, player); //-1 means not enough gold or item not found not successful
        return goldLeft;
    }

    @GetMapping("/event")
    public Map<String, String> showEvent() {
    String eventKey = Event.getRandomEvent();  
    return Map.of(
        "event", Event.eventList.get(eventKey),  
        "key", eventKey
    );
}

    @PostMapping("/event/{key}/{choice}")
    public Map<String, Object>makeChoice(@PathVariable String key, @PathVariable String choice) {
        return Event.chooseEvent(key,choice,player);
    }


    @PostMapping("/restart")
    public void restart() {
        enemy = null;
        player =null;
        System.out.println("Game restarted.");
    }

}
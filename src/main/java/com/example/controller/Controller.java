package com.example.controller;

import com.example.models.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") //link to front end 
public class Controller {

    private Player player = new Player();
    private Enemy enemy = new Enemy();
    private Shop shop = new Shop();


    @PostMapping("/player/{character}")
    public Player getPlayer(@PathVariable String character) {
        player.setUpCharacter(character);
        System.out.println("Player created: " + character);
        return player;
    }

    @GetMapping("/player/getCharacter")
    public Player getPlayer() {
        Player p = player.getPlayer();
        System.out.println("Player: " + p);
        return p;
    }


    @PostMapping("/enemy/{level}")
    public Enemy createEnemy(@PathVariable int level) {
        Random rand = new Random();
        enemy.setUp(level, rand.nextInt(3), rand.nextInt(3));
        System.out.println("Enemy created at level: " + level);
        return enemy;
    }

    @PostMapping("/attack/{skill}")
    public AttackResponse attackEnemy(@PathVariable String skill) {
        return player.attackEnemy(skill, enemy); 
    }

    @PostMapping("/items/{item}")
    public Player useItem(@PathVariable String item) {
        return player.useItem(item);
    }
    
    @GetMapping("/shop")
    public Map<String, Integer> listItem(){
        shop.setItemInShop();
        return shop.getItemList();
    }

    @PostMapping("/shop/{item}")
    public BuyResponse buyItem(@PathVariable String item) {
        return shop.buyItem(item, player);
    }

    @GetMapping("/event")
    public Map<String, String> showEvent() {
    String eventKey = Event.getRandomEvent();  
    return Map.of(
        "key", eventKey,
        "event", Event.eventList.get(eventKey)
    );
}

    @PostMapping("/event/{key}/{choice}")
    public EventResponse makeChoice(@PathVariable String key, @PathVariable String choice) {
        return Event.chooseEvent(key,choice,player);
    }

}
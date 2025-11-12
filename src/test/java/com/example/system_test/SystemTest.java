package com.example.system_test;

import com.example.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.example.adventure_game.AdventureGameApplication.class)
public class SystemTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    public void testFullGameFlow() {
        //create player
        ResponseEntity<Player> playerResp = restTemplate.postForEntity( baseUrl() + "/player/knight", null, Player.class);
        assertEquals(HttpStatus.OK, playerResp.getStatusCode());
        Player player = playerResp.getBody();
        assertNotNull(player);
        assertEquals(100, player.getHp());

        ResponseEntity<Enemy> enemyResp = restTemplate.postForEntity(
                baseUrl() + "/enemy/1", null, Enemy.class);
        assertEquals(HttpStatus.OK, enemyResp.getStatusCode());
        Enemy enemy = enemyResp.getBody();
        assertTrue(enemy.getAttack() > 1);

        //attackEnemy
        int originalPlayerHp = player.getHp();
        int originalEnemyHp = enemy.getHp();
        ResponseEntity<AttackResponse> attackResp = restTemplate.postForEntity(baseUrl() + "/attack/Slash", null, AttackResponse.class);
        assertEquals(HttpStatus.OK, attackResp.getStatusCode());
        AttackResponse attack = attackResp.getBody();
        assertNotNull(attack);
        assertTrue(attack.getEnemyHp() < originalEnemyHp);
        if (attack.getMessage().equals("hurt")) {
            assertTrue(attack.getPlayerHp() < originalPlayerHp);
        } else {
            assertEquals(attack.getPlayerHp(), originalPlayerHp);
        }

        //useItem
        int originalBuff = player.getBuff();
        ResponseEntity<Player> useResp = restTemplate.postForEntity(baseUrl() + "/items/StrengthPotion", null, Player.class);
        assertEquals(HttpStatus.OK, useResp.getStatusCode());
        player = useResp.getBody();
        assertTrue(player.getBuff() > originalBuff);

        //ListShop
        ResponseEntity<Map> shopResp = restTemplate.getForEntity( baseUrl() + "/shop", Map.class);
        assertEquals(HttpStatus.OK, shopResp.getStatusCode());
        Map<String, Integer> shop = shopResp.getBody();
        assertEquals(2, shop.size());

        //Buy in shop
        ResponseEntity<BuyResponse> buyResp = restTemplate.postForEntity(baseUrl() + "/shop/HealthPotion", null, BuyResponse.class);
        assertEquals(HttpStatus.OK, buyResp.getStatusCode());
        BuyResponse response = buyResp.getBody();
        if(response.getMessage()!=null){
            while (response.getMessage().equals("Item not found!")) {
             buyResp = restTemplate.postForEntity(
                    baseUrl() + "/shop/HealthPotion", null, BuyResponse.class);
            assertEquals(HttpStatus.OK, buyResp.getStatusCode());
            response = buyResp.getBody();
        }
        }
        assertNull(response.getMessage());
        player = response.getPlayer();
        assertTrue(player.getInventory().contains("HealthPotion"));

        //Show the event occur 
        ResponseEntity<Map> eventResp = restTemplate.getForEntity(baseUrl() + "/event", Map.class);
        assertEquals(HttpStatus.OK, eventResp.getStatusCode());
        Map<String, String> event = eventResp.getBody();
        assertNotNull(event);
        assertTrue(Event.eventList.containsKey(event.get("key")));



        //( for futher action let make it fixed to fairy)
        while(!"fairy".equals(event.get("key"))){
            eventResp = restTemplate.getForEntity(baseUrl() + "/event", Map.class);
            assertEquals(HttpStatus.OK, eventResp.getStatusCode());
            event = eventResp.getBody();
            assertNotNull(event);
            assertTrue(Event.eventList.containsKey(event.get("key")));
        }
        
        //Trigger the event 
        ResponseEntity<EventResponse> choiceResp = restTemplate.postForEntity(
                baseUrl() + "/event/" + event.get("key") + "/yes", null, EventResponse.class);
        assertEquals(HttpStatus.OK, choiceResp.getStatusCode());
        EventResponse eventResponse = choiceResp.getBody();
        if(eventResponse.getMessage().equals("Got HealthPotion!")){
            assertTrue(eventResponse.getInventory().contains("HealthPotion"));
        }else{
            assertFalse(eventResponse.getInventory().contains("HealthPotion"));
        }
    }
}

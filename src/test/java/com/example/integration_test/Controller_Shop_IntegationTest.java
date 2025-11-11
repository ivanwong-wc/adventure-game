package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.BuyResponse;
import com.example.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Controller_Shop_IntegationTest {

    private void injectField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Controller controller;
    private Player player;

    @BeforeEach
    public void setUp() throws Exception {
        player = new Player();
        controller = new Controller();
        injectField(controller, "player", player);
    }

    @Test
    public void testListItem() {
        Map<String, Integer> items = controller.listItem();
        assertEquals(3,items.size()); 
    }

    @Test
    public void testBuyItem_Success() {
        controller.getPlayer("knight"); 
        player.changeGold(100); 
        int originalGold = player.getGold();
        controller.useItem("HealthPotion");
        Map<String, Integer> items = controller.listItem();
        while(!items.containsKey("HealthPotion")){
            controller.listItem(); 
        }
        BuyResponse response = controller.buyItem("HealthPotion");
        assertNull(response.getMessage());
        assertTrue(player.getInventory().contains("HealthPotion"));
        assertEquals(originalGold-10,player.getGold() ); 
    }

    @Test
    public void testBuyItem_Buff_Success() {
        controller.getPlayer("wizard"); 
        player.changeGold(100); 
        int originalGold = player.getGold();
        int originalHp = player.getHp();
        Map<String, Integer> items = controller.listItem(); 
        while(!items.containsKey("Health10HP")){
            controller.listItem(); 
        }
        BuyResponse response = controller.buyItem("Health10HP");
        assertNull(response.getMessage());
        assertEquals(originalGold-20,player.getGold() ); 
        assertEquals(originalHp+10,player.getHp() ); 
    }

    @Test
    public void testBuyItem_NotEnoughMoney() {
        controller.getPlayer("knight");
        player.changeGold(-99); 
        int originalGold = player.getGold();
        int originalAtk = player.getAttack();
        Map<String, Integer> items = controller.listItem(); 
        while(!items.containsKey("Raise2ATK")){
            controller.listItem(); 
        }
        BuyResponse response = controller.buyItem("Raise2ATK");
        assertEquals("Not enough gold!", response.getMessage());
        assertEquals(originalGold,player.getGold()); 
        assertEquals(originalAtk,player.getAttack()); 
    }

    @Test
    public void testBuyItem_WrongItem() {
        controller.getPlayer("knight");
        player.changeGold(100);
        int originalGold = player.getGold();
        controller.listItem();
        BuyResponse response = controller.buyItem("unknown");
        assertEquals("Item not found!", response.getMessage());
        assertFalse(player.getInventory().contains("unknown"));
        assertEquals(originalGold,player.getGold()); 
    }

}
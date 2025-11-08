package com.example.integration_test;
import com.example.models.Shop;
import com.example.models.Player;
import com.example.models.BuyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class playerShop {
    

    private Shop shop;
    private Player player;

    @BeforeEach
    public void setUp() {
        shop = new Shop();
        player = new Player();
    }

    @Test
    public void testBuyItemSuccess() {
        shop.cleanShop();
        player.changeGold(100);
        shop.getItemList().put("Health10HP", 20);
        shop.getItemList().put("Raise2ATK", 20);
        shop.getItemList().put("Recover10MP", 15);
        BuyResponse response = shop.buyItem("Health10HP", player);
        assertNull(response.getMessage(), "Purchase should be successful");
        assertEquals(80, player.getGold(), "Player's gold should decrease by item price");
        assertEquals(10, player.getHp(), "Player's HP should increase by 10");

        BuyResponse responseATK = shop.buyItem("Raise2ATK", player);
        assertNull(responseATK.getMessage(), "Purchase should be successful");
        assertEquals(60, player.getGold(), "Player's gold should decrease by item price");
        assertEquals(2, player.getAttack(), "Player's ATK should increase by 2");

        BuyResponse responseMP = shop.buyItem("Recover10MP", player);
        assertNull(responseMP.getMessage(), "Purchase should be successful");
        assertEquals(45, player.getGold(), "Player's gold should decrease by item price");
        assertEquals(10, player.getMp(), "Player's MP should increase by 10");
        
    }

    @Test
    public void testBuyItemNotFound() {
        shop.cleanShop();
        BuyResponse response = shop.buyItem("NonExistentItem", player);
        assertEquals("Item not found!", response.getMessage());
    }

    @Test
    public void testBuyItemNotEnoughGold() {
        shop.cleanShop();
        shop.getItemList().put("Raise2ATK", 200); 
        BuyResponse response = shop.buyItem("Raise2ATK", player);
        assertEquals("Not enough gold!", response.getMessage());
    }
}
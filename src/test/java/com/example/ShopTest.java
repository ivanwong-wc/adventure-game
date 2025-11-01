package com.example;

import com.example.models.Shop;
import com.example.models.Player;
import com.example.models.BuyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;
    private Player player;

    @BeforeEach
    public void setUp() {
        shop = new Shop();
        player = new Player();
    }

    @Test
    public void testSetItemInShop() {
        shop.setItemInShop();
        Map<String, Integer> items = shop.getItemList();
        assertFalse(items.isEmpty(), "Shop should have items after setup");
        assertTrue(items.size() <= 3, "Shop should have at most 3 items");
    }

    @Test
    public void testCleanShop() {
        shop.setItemInShop();
        shop.cleanShop();
        assertTrue(shop.getItemList().isEmpty(), "Shop should be empty after cleaning");
    }

    @Test
    public void testBuyItemSuccess() {
        shop.cleanShop();
        player.changeGold(100);
        shop.getItemList().put("Health10HP", 20);
        BuyResponse response = shop.buyItem("Health10HP", player);
        assertNull(response.getMessage(), "Purchase should be successful");
        assertEquals(80, player.getGold(), "Player's gold should decrease by item price");
        assertEquals(10, player.getHp(), "Player's HP should increase by 10");
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
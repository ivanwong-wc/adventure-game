package com.example.unit_test;
import com.example.models.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;
    @BeforeEach
    public void setUp() {
        shop = new Shop();
    }

    @Test
    public void testSetItemInShop() {
        shop.setItemInShop();
        Map<String, Integer> items = shop.getItemList();
        assertFalse(items.isEmpty(), "Shop should have items after setup");
        assertTrue(items.size() <= 3, "Shop should have 3 items");
    }

    @Test
    public void testSetItemInShopAllExistOnce() {
        Set<String> items = new HashSet<>();
        while (items.size() < 6) {
            shop.setItemInShop();
            items.addAll(shop.getItemList().keySet());
        }
        assertTrue(items.size() == 6, "Shop should have 6 items");
    }

    @Test
    public void testCleanShop() {
        shop.setItemInShop();
        shop.cleanShop();
        assertTrue(shop.getItemList().isEmpty(), "Shop should be empty after cleaning");
    }

}
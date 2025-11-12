package com.example.unit_test;
import com.example.models.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
        assertTrue(items.size() == 2, "Shop should have 3 items");
    }


    @Test
    public void testCleanShop() {
        shop.setItemInShop();
        shop.cleanShop();
        assertTrue(shop.getItemList().isEmpty(), "Shop should be empty after cleaning");
    }

}
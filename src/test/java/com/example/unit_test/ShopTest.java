package com.example.unit_test;

import com.example.models.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.models.Player;
import com.example.models.BuyResponse;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    private Set<String> itemSet = new HashSet<>();
    private Shop shop;
    
    
    public static class PlayerStub extends Player {
        private int gold;
        private int attack;
        private int hp;
        private int mp;
        private List<String> inventory = new ArrayList<>();

        public PlayerStub(int gold) {
            this.gold = gold;
        }

        @Override
        public int getGold() {
            return gold;
        }

        @Override
        public int changeGold(int amount) {
            this.gold += amount;
            return this.gold;
        }

        @Override
        public int changeAttack(int amount) {
            this.attack += amount;
            return this.attack;
        }

        @Override
        public int changeHp(int amount) {
            this.hp += amount;
            return this.hp;
        }

        @Override
        public int changeMp(int amount) {
            this.mp += amount;
            return this.mp;
        }

        @Override
        public List<String> getInventory() {
            return inventory;
        }
        
        public int getAttact() {
			return attack;
		}

        public int getHp() {
			return hp;
		}
        
        public int getMp() {
			return mp;
		}
    }

    @BeforeEach
    public void setUp() {
        shop = new Shop();
    }

    @Test
    public void testSetItemInShop() {
        shop.setItemInShop();
        Map<String, Integer> items = shop.getItemList();
        assertFalse(items.isEmpty(), "Shop should have items after setup");
        assertEquals(2, items.size(), "Shop should have 2 items");
    }

    @Test
    public void testCleanShop() {
        shop.setItemInShop();
        shop.cleanShop();
        assertTrue(shop.getItemList().isEmpty(), "Shop should be empty after cleaning");
    }

    @Test
    public void testAllItemExist() {
        do {
            shop.setItemInShop();
            Map<String, Integer> items = shop.getItemList();
            assertEquals(2, items.size(), "Shop should have 2 items");
            itemSet.addAll(items.keySet());
        } while (itemSet.size() < 6);

        assertEquals(6, itemSet.size(), "All 6 possible items should appear across multiple setups");
    }
    
    @Test
    public void testBuyItemSuccess_effectATK() {
    	Set<String> itemS = new HashSet<>();
    	while (!itemS.contains("Raise2ATK")) {
        	 shop.setItemInShop();
             Map<String, Integer> items = shop.getItemList();
             itemS.addAll(items.keySet());
        }
        PlayerStub player = new PlayerStub(100); 
        int oldAttack = player.getAttact();
        shop.buyItem("Raise2ATK", player);
        assertTrue(oldAttack + 2 == player.getAttact(), "Player attack should increase by 2");
        assertTrue(player.getGold() <100, "Player gold should decrease by 20");
    }
    
    @Test
    public void testBuyItemSuccess_effectMP() {
        PlayerStub player = new PlayerStub(100); 
        int oldMp = player.getMp();
        Set<String> itemS = new HashSet<>();
        while (!itemS.contains("Recover10MP")) {
       	 shop.setItemInShop();
            Map<String, Integer> items = shop.getItemList();
            itemS.addAll(items.keySet());
       }
        shop.buyItem("Recover10MP", player);
        assertTrue(oldMp + 10 == player.getMp(), "Player mp should increase by 10");
        assertTrue(player.getGold() <100, "Player gold should decrease by 15");
    }
    
    @Test
    public void testBuyItemSuccess_effectHP() {
        PlayerStub player = new PlayerStub(100); 
        Set<String> itemS = new HashSet<>();
        while (!itemS.contains("Health10HP")) {
       	 shop.setItemInShop();
            Map<String, Integer> items = shop.getItemList();
            itemS.addAll(items.keySet());
       }
        int oldHp = player.getHp();
        shop.buyItem("Health10HP", player);
        assertTrue(oldHp + 10 == player.getHp(), "Player hp should increase by 10");
        assertTrue(player.getGold() <100, "Player gold should decrease by 20");
    }
    
    @Test
    public void testBuyItemSuccess_itemMpPotion() {
        PlayerStub player = new PlayerStub(100);
        Set<String> itemS = new HashSet<>();
        while (!itemS.contains("MpPotion")) {
       	 shop.setItemInShop();
            Map<String, Integer> items = shop.getItemList();
            itemS.addAll(items.keySet());
       }
        shop.buyItem("MpPotion", player);
        assertTrue(player.getInventory().contains("MpPotion"), "Player inventory should contain MpPotion");
        assertTrue(player.getGold() <100, "Player gold should decrease by 15");
        
    }
    

    @Test
    public void testBuyItemNotEnoughGold() {
    	PlayerStub player = new PlayerStub(1); 
    	 Set<String> itemS = new HashSet<>();
         while (!itemS.contains("Raise2ATK")) {
        	 shop.setItemInShop();
             Map<String, Integer> items = shop.getItemList();
             itemS.addAll(items.keySet());
        }
        int oldAttack = player.getAttact();
        BuyResponse response = shop.buyItem("Raise2ATK", player);
        assertTrue(oldAttack == player.getAttact(), "Player attack should unchanged");
        assertTrue(player.getGold() == 1, "Player gold should unchanged");
        assertEquals("Not enough gold!", response.getMessage());
    }

    @Test
    public void testBuyItemNotFound() {
        PlayerStub player = new PlayerStub(100);
        BuyResponse response = shop.buyItem("unknown", player);

        assertEquals("Item not found!", response.getMessage());
    }
}

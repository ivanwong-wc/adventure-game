package com.example.unit_test;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.models.Player;

import com.example.models.Event;
import com.example.models.EventResponse;
import java.util.Random;
public class EventTest {

    static class PlayerEventStub extends Player{
        private  int hp = 100;
        private int attack = 10;
        private int gold = 100;
        private double luck = 0.5;
        private final List<String> inventory = new ArrayList<>();
        @Override
        public int getHp() { return hp; }
        @Override
        public int getAttack() { return attack; }
        @Override
        public int getGold() { return gold; }
        @Override
        public double getLuck() { return luck; }
        @Override
        public List<String> getInventory() { return inventory; }
        @Override
        public int changeGold(int g) { gold += g; return gold; }
        @Override
        public double changeLuck(double d) { luck = d; return luck; }
        @Override
        public int changeAttack(int i) { attack += i; return attack; }
        @Override
        public int takeDamage(int d) { hp = Math.max(0, hp - d); return hp; }
    }
    

    @Test
    public void testEventCreate() {
        Set<String> result = new HashSet<>();
        while (result.size() < 4) {
            result.add(Event.getRandomEvent());
        }
        assertEquals(4, result.size());
        assertTrue(result.contains("charity"));
        assertTrue(result.contains("merchant"));
        assertTrue(result.contains("treasure"));
        assertTrue(result.contains("fairy"));
    }

    @Test
    public void testChooseEventNoDoesNothing() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        EventResponse response = Event.chooseEvent("charity", "no", player);

        assertNull(response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals(oldGold, response.getGold());
    }

    @Test
    public void testCharityNotEnoughGold() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        player.changeGold(-100000);
        int oldGold = player.getGold();
        EventResponse response = Event.chooseEvent("charity", "yes", player);
        assertEquals("Not enough gold!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals(oldGold, response.getGold());
    }

    @Test
    public void testCharityHaveMoneyGoodEvent() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = Event.chooseEvent("charity", "yes", player);
        assertEquals("+0.1 Luck!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold-5), response.getGold());
    }

    @Test
    public void testCharityHaveMoneyBadEvent() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(0);
        EventResponse response = Event.chooseEvent("charity", "yes", player);
        assertEquals("Child runs away...", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold-5), response.getGold());
    }

    @Test
    public void testMerchantNotEnoughGold() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        player.changeGold(-100000);
        int oldGold = player.getGold();
        EventResponse response = Event.chooseEvent("merchant", "yes", player);
        assertEquals("Not enough gold!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals(oldGold, response.getGold());
    }

    @Test
    public void testMerchantHaveMoney_Event1() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.1; } 
        });
        response = Event.chooseEvent("merchant", "yes", player);
        assertEquals("Got HealthPotion!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        oldInventory.add("HealthPotion");
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold-10), response.getGold());
    }

    @Test
    public void testMerchantHaveMoney_Event2() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.4; } 
        });
        response = Event.chooseEvent("merchant", "yes", player);
        oldInventory.add("HealthPotion");
        assertEquals("Got MpPotion but lost an item!", response.getMessage());
        oldInventory.remove("HealthPotion");
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        oldInventory.add("MpPotion");
        assertEquals(oldInventory, response.getInventory());
        assertFalse(response.getInventory().contains("HealthPotion"));
        assertEquals((oldGold-10), response.getGold());
    }

    @Test
    public void testMerchantHaveMoney_Event3() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.6; } 
        });
        response = Event.chooseEvent("merchant", "yes", player);
        assertEquals("Loss 10gold! Add Atkack 2!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals((oldAttack+2), response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold-10), response.getGold());
    }

    @Test
    public void testMerchantHaveMoney_Event4() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.9; } 
        });
        response = Event.chooseEvent("merchant", "yes", player);
        assertEquals("Fake! Lost 10g!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold-10), response.getGold());
    }

    @Test
    public void testTreasureGood() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.5; } 
        });
        response = Event.chooseEvent("treasure", "yes", player);
        assertEquals("+15 Gold!", response.getMessage());
        assertEquals(oldHp, response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals((oldGold+15), response.getGold());
    }

    @Test
    public void testTreasureBad() {
        PlayerEventStub player = new PlayerEventStub();
        int oldHp = player.getHp();
        int oldAttack = player.getAttack();
        List<String> oldInventory = new ArrayList<>(player.inventory);
        int oldGold = player.getGold();
        player.changeLuck(100);
        EventResponse response = null;
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.9; } 
        });
        response = Event.chooseEvent("treasure", "yes", player);
        assertEquals("-5 HP! Trap!", response.getMessage());
        assertEquals((oldHp-5), response.getPlayerHp());
        assertEquals(oldAttack, response.getPlayerAttack());
        assertEquals(oldInventory, response.getInventory());
        assertEquals(oldGold, response.getGold());
    }



}

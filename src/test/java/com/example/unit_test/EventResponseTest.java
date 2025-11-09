
package com.example.unit_test;

import java.util.List;

import com.example.models.EventResponse;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class EventResponseTest {
    
    @Test
    public void testEventResponse() {
        List<String> inventory = List.of("StrengthPotion");
        EventResponse eventResponse = new EventResponse("test", 1, 2,inventory,4);
        assertEquals("test", eventResponse.getMessage());
        assertEquals(1, eventResponse.getPlayerHp());
        assertEquals(2, eventResponse.getPlayerAttack());
        assertEquals(inventory, eventResponse.getInventory());
        assertEquals(4, eventResponse.getGold());
    }

    @Test
    public void testEventResponseEmptyInventory() {
        List<String> inventory = List.of();
        EventResponse eventResponse = new EventResponse("", -1, -2,inventory,-3);
        assertEquals("", eventResponse.getMessage());
        assertEquals(-1, eventResponse.getPlayerHp());
        assertEquals(-2, eventResponse.getPlayerAttack());
        assertEquals(inventory,eventResponse.getInventory());
        assertEquals(-3, eventResponse.getGold());
    }


}
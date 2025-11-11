package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.Event;
import com.example.models.EventResponse;
import com.example.models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Controller_Event_IntegationTest {

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
        Event.resetRandom(); 
    }

    @AfterEach
    public void tearDown() {
        Event.resetRandom(); 
    }

    @Test
    public void testShowEvent() {
        Map<String, String> event = controller.showEvent();
        assertNotNull(event.get("key"));
        assertNotNull(event.get("event"));
        assertTrue(Event.eventList.containsKey(event.get("key")));
    }

    @Test
    public void testMakeChoice_Charity_Good()  {
        controller.getPlayer("villager");
        player.changeGold(10);
        int originalGold = player.getGold();
        double originalLuck = player.getLuck();
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.3; } 
        });
        EventResponse response = controller.makeChoice("charity","yes");
        assertNotNull(response.getMessage());
        assertEquals((originalGold-5), player.getGold()); 
        assertEquals((originalLuck+0.1), player.getLuck());
    }

    @Test
    public void testMakeChoice_Charity_Bad()  {
        controller.getPlayer("villager");
        player.changeGold(10);
        int originalGold = player.getGold();
        double originalLuck = player.getLuck();
        Event.setRandom(new Random() {
            @Override 
            public double nextDouble() { return 0.9999999999; } 
        });
        EventResponse response = controller.makeChoice("charity","yes");
        assertNotNull(response.getMessage());
        assertEquals((originalGold-5), player.getGold()); 
        assertEquals(originalLuck, player.getLuck());
    }

    @Test
    public void testMakeChoice_NoEffect() {
        controller.getPlayer("wizard");
        int originalGold = player.getGold();
        double originalLuck = player.getLuck();
        EventResponse response = controller.makeChoice("charity","no");
        assertNull(response.getMessage());
        assertEquals(originalGold, player.getGold());
        assertEquals(originalLuck, player.getLuck());
    }

}
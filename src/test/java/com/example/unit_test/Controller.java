package com.example.unit_test;


import com.example.controller.Controller;
import com.example.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ControllerTest {


   private MockMvc mockMvc;
   private Player player;
   private Enemy enemy;
   private Shop shop;


   private void injectField(Object target, String fieldName, Object value) throws Exception {
       Field field = target.getClass().getDeclaredField(fieldName);
       field.setAccessible(true);
       field.set(target, value);
   }


   @BeforeEach
   void setup() throws Exception {
       player = Mockito.mock(Player.class);
       enemy = Mockito.mock(Enemy.class);
       shop = Mockito.mock(Shop.class);
       Controller controller = new Controller();
       injectField(controller, "player", player);
       injectField(controller, "enemy", enemy);
       injectField(controller, "shop", shop);
       mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
   }


   @Test
   void testCreatePlayer() throws Exception {
       Player mockPlayer = new Player();
       doNothing().when(player).setUpCharacter("knight");
       when(player.getPlayer()).thenReturn(mockPlayer);
       mockMvc.perform(post("/api/player/knight"))
              .andExpect(status().isOk());
   }


   @Test
   void testGetPlayer() throws Exception {
       Player mockPlayer = new Player();
       when(player.getPlayer()).thenReturn(mockPlayer);
       mockMvc.perform(get("/api/player/getCharacter"))
              .andExpect(status().isOk());
   }


   @Test
   void testAttackEnemy() throws Exception {
       AttackResponse response = new AttackResponse("error", 1, 2, 3, false, 10);
       when(player.attackEnemy("slash", enemy)).thenReturn(response);
       mockMvc.perform(post("/api/attack/slash"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.message").value("error"))
              .andExpect(jsonPath("$.playerHp").value(1));
   }


   @Test
   void testListShopItems() throws Exception {
       when(shop.getItemList()).thenReturn(Map.of("Potion", 50));
       mockMvc.perform(get("/api/shop"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.Potion").value(50));
   }


   @Test
   void testBuyItem() throws Exception {
       BuyResponse response = new BuyResponse("Item not found!", player);
       when(shop.buyItem("Potion", player)).thenReturn(response);
       mockMvc.perform(post("/api/shop/Potion"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.message").value("Item not found!"));
   }


   @Test
   void testGetPlayerEndpoint() throws Exception {
       Player mockPlayer = new Player();
       when(player.getPlayer()).thenReturn(mockPlayer);
       mockMvc.perform(get("/api/player/getCharacter"))
           .andExpect(status().isOk());
   }

    @Test
    void testUseItems() throws Exception {
        Player mockPlayer = new Player();
        mockPlayer.changeHp(120);
        mockPlayer.changeAttack(50);
        mockPlayer.changeGold(200);

        when(player.useItem("HealthPotion")).thenReturn(mockPlayer);

        mockMvc.perform(post("/api/items/HealthPotion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hp").value(120))
                .andExpect(jsonPath("$.attack").value(50))
                .andExpect(jsonPath("$.gold").value(200));
    }

    @Test
    void testCreateEnemy() throws Exception {

        when(enemy.getHp()).thenReturn(100);
        when(enemy.getAttack()).thenReturn(30);

        doNothing().when(enemy).setUp(Mockito.eq(5), Mockito.anyInt(), Mockito.anyInt());

        mockMvc.perform(post("/api/enemy/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hp").value(100))
                .andExpect(jsonPath("$.attack").value(30));
    }

    @Test
    void testShowEvent() throws Exception {
        try (MockedStatic<Event> mockedEvent = Mockito.mockStatic(Event.class)) {
            mockedEvent.when(Event::getRandomEvent).thenReturn("fairy");

            mockMvc.perform(get("/api/event"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.key").value("fairy"))
                    .andExpect(jsonPath("$.event").value("Find a HealthPotion on the ground. Take it?"));
        }
    }

    @Test
    void testMakeChoice() throws Exception {
        EventResponse mockResponse = new EventResponse(
                "Got HealthPotion!",
                100,
                50,
                List.of("HealthPotion"),
                200
        );

        try (MockedStatic<Event> mockedEvent = Mockito.mockStatic(Event.class)) {
            mockedEvent.when(() -> Event.chooseEvent("fairy", "yes", player))
                    .thenReturn(mockResponse);

            mockMvc.perform(post("/api/event/fairy/yes"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Got HealthPotion!"))
                    .andExpect(jsonPath("$.playerHp").value(100))
                    .andExpect(jsonPath("$.playerAttack").value(50))
                    .andExpect(jsonPath("$.inventory[0]").value("HealthPotion"))
                    .andExpect(jsonPath("$.gold").value(200));
        }
    }
}


package com.example.unit_test;

import com.example.models.BuyResponse;
import com.example.models.Player;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BuyResponseTest {
    
    @Test
    public void testCreateAttackResponseWithError() {
        BuyResponse buyResponse = new BuyResponse("error",null);
        assertEquals("error", buyResponse.getMessage());
        assertNull(buyResponse.getPlayer());
    }

    @Test
    public void testCreateAttackResponseWithSucces() {
        BuyResponse buyResponse = new BuyResponse(null,new Player());
        assertNull(buyResponse.getMessage());
        assertNotNull(buyResponse.getPlayer());
    }



}
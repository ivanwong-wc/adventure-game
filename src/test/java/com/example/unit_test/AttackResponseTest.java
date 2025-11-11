
package com.example.unit_test;

import com.example.models.AttackResponse;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AttackResponseTest {
    
    @Test
    public void testCreateAttackResponseWithError() {
        AttackResponse attackResponse = new AttackResponse("error",1,2,3,false, 10);
        assertEquals("error", attackResponse.getMessage());
        assertEquals(1, attackResponse.getPlayerHp());
        assertEquals(2, attackResponse.getPlayerMp());   
        assertEquals(3, attackResponse.getEnemyHp());
        assertFalse(attackResponse.isVictory());
        assertEquals(10, attackResponse.getGold());
    }

    @Test
    public void testCreateAttackResponseWithSucces() {
        AttackResponse attackResponse = new AttackResponse(null,1,2,0,true, 10);
        assertNull(attackResponse.getMessage());
        assertEquals(1, attackResponse.getPlayerHp());
        assertEquals(2, attackResponse.getPlayerMp());   
        assertEquals(0, attackResponse.getEnemyHp());
        assertTrue(attackResponse.isVictory());
        assertEquals(10, attackResponse.getGold());
    }



}

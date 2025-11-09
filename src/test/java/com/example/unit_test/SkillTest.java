
package com.example.unit_test;

import com.example.models.Skill;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {
    
    @Test
    public void testCreateAttackResponse() {
        Skill skill = new Skill(50, 20);
        assertEquals(50, skill.getDamage());
        assertEquals(20, skill.getMpCost());
    }
}
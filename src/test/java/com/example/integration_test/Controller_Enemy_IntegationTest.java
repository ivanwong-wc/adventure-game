package com.example.integration_test;

import com.example.controller.Controller;
import com.example.models.Enemy;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Controller_Enemy_IntegationTest {
    private Controller controller;
    @BeforeEach
    public void setUp() throws Exception {
        controller = new Controller();
    }

    @Test
    public void testCreateEnemy_GeneratesValidStats() {
        Enemy enemy = controller.createEnemy(10);
        assertTrue(enemy.getAttack() > 1);
        assertTrue(enemy.getHp() > 0);
    }

}
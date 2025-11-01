package com.example.models;

import java.util.Map;
import java.util.Random;

public class Action {

    public static AttackResponse attackEnemy(String skill, Enemy enemy, Player player, Map<String, Skill> skills) {
        if (!skills.containsKey(skill)) {
            System.out.println("Skill not found!");
            //AttackResponse(String error, int playerHp, int playerMp, int enemyHp, boolean victory, int gold, )
            return new AttackResponse("Skill not found!", player.getHp(), player.getMp(), enemy.getHp(), false, player.getGold());
        }
        if (player.getMp() < skills.get(skill).getMpCost()) {
            return new AttackResponse("Not enough MP!", player.getHp(), player.getMp(), enemy.getHp(), false, player.getGold());
        }
        int damage = skills.get(skill).getDamage() + player.getAttack() + player.getBuff();
        player.changeMp(-(skills.get(skill).getMpCost()));
        enemy.takeDamage(damage);
        System.out.println("Enemy takes " + damage + " damage! HP: " + enemy.getHp());
        if (enemy.getHp() == 0) {
            Random rand = new Random();
            int goldGet = 5 + rand.nextInt(11);
            player.changeGold(goldGet); // Reward between 5 to 15 gold
            System.out.println("Victory");
            player.changeBuff("reset");
            return new AttackResponse(null, player.getHp(), player.getMp(), 0, true, player.getGold());
        } else {
            //enemy.attackPlayer(player);
            enemy.attackPlayer(player, checkEvades(player));
            return new AttackResponse(null, player.getHp(), player.getMp(), enemy.getHp(), false, player.getGold());
        }
    }

    public static Boolean checkEvades(Player player) {
        Random rand = new Random();
        return player.getLuck() * rand.nextDouble() > 0.5 * rand.nextDouble() + 0.1;
    }

    public static int useHealthPotion(Player player) {
        if (player.getInventory().contains("HealthPotion")) {
            player.changeHp(20);
            player.getInventory().remove("HealthPotion");
            System.out.println("Now HP: " + player.getHp());
            return player.getHp();
        } else {
            System.out.println("NO HealthPotion");
            return -1;
        }
    }

    public static int useAttackPotion(Player player) {
        System.out.println("Using StrengthPotion");
        if (player.getInventory().contains("StrengthPotion")) {
            player.changeBuff("StrengthPotion");
            player.getInventory().remove("StrengthPotion");
            System.out.println("Now ATK: " + player.getAttack() + " Buff: " + player.getBuff());
            return player.getAttack() + player.getBuff();
        } else {
            System.out.println("NO StrengthPotion");
            return -1;
        }
    }

    public static int useMpPotion(Player player) {
        if (player.getInventory().contains("MpPotion")) {
            player.changeMp(15);
            player.getInventory().remove("MpPotion");
            System.out.println("Now Mp: " + player.getMp());
            return player.getMp();
        } else {
            System.out.println("NO MpPotion");
            return -1;
        }
    }
}

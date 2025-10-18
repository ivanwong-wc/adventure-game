package com.example.models;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
public class Action {

    public static Map<String, Integer> attackEnemy(String skill, Enemy enemy, Player player, Map<String, Skill> skills) {
        Map<String, Integer> result = new HashMap<>();
        if (!skills.containsKey(skill)) {
            System.out.println("Skill not found!");
            result.put("Skill not found!", -1);
            return result;
        }
        if (skills.get(skill).getMaxUses() <= 0) {
            System.out.println("No uses left for this skill!");
            result.put("No uses left for this skill!", -1);
            return result;
        }
        if (player.getMp() < skills.get(skill).getMpCost()) {
            System.out.println("Not enough MP!");
            result.put("Not enough MP!", -1);
            return result;
        }
        int damage = skills.get(skill).getDamage()+player.getAttack()+player.getBuff();
        player.changeMp(skills.get(skill).getMpCost());
        skills.get(skill).setMaxUses(skills.get(skill).getMaxUses() - 1);
        enemy.takeDamage(damage);
        System.out.println("Enemy takes " + damage + " damage! HP: " + enemy.getHp());
        if (enemy.getHp() == 0) {
            Random rand = new Random();
            player.changeGold(5 + rand.nextInt(11)); // Reward between 5 to 15 gold
            result.put("victory! Gold Get", player.getGold());
            System.out.println("Enemy defeated!");
            player.changeBuff("reset");
        } else {
            result.put("Enemy Hp", enemy.getHp());
            result.put("mp", player.getMp());
            result.put("MaxUse", skills.get(skill).getMaxUses());
            result.putAll(enemy.attackPlayer(player));
        }

        return result;
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

    public static int  useAttackPotion(Player player) {
        if (player.getInventory().contains("StrengthPotion")) {
            player.changeBuff("StrengthPotion");
            player.getInventory().remove("StrengthPotion");
            System.out.println("Now ATK: " + player.getAttack() + " Buff: " + player.getBuff());
            return player.getAttack()+ player.getBuff();
        } else {
            System.out.println("NO StrengthPotion");
            return -1;
        }
    }
    public static int  useMpPotion(Player player) {
        if (player.getInventory().contains("MpPotion")) {
            player.changeMp(5);
            player.getInventory().remove("MpPotion");
            System.out.println("Now Mp: " + player.getMp());
            return player.getMp();
        } else {
            System.out.println("NO MpPotion");
            return -1;
        }
    }
}

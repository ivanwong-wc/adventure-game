package com.example.models;
import java.io.Console;
import java.util.Map;
import java.util.Random;
public class Action {

    public static AttackResponse attackEnemy(String skill, Enemy enemy, Player player, Map<String, Skill> skills) {
        if (!skills.containsKey(skill)) {
            System.out.println("Skill not found!");
            //AttackResponse(String error, int playerHp, int playerMp, int enemyHp, int maxUse, boolean victory, int gold, )
            return new AttackResponse("Skill not found!",player.getHp(), player.getMp(), enemy.getHp(), 0, false, player.getGold());
        }
        if (skills.get(skill).getMaxUses() <= 0) {
            return new AttackResponse("No uses left for this skill!",player.getHp(), player.getMp(), enemy.getHp(), 0, false, player.getGold());
        }
        if (player.getMp() < skills.get(skill).getMpCost()) {
            return new AttackResponse("Not enough MP!",player.getHp(), player.getMp(), enemy.getHp(), skills.get(skill).getMaxUses(), false, player.getGold());
        }
        int damage = skills.get(skill).getDamage()+player.getAttack()+player.getBuff();
        player.changeMp(-(skills.get(skill).getMpCost()));
        skills.get(skill).setMaxUses(skills.get(skill).getMaxUses() - 1);
        enemy.takeDamage(damage);
        System.out.println("Enemy takes " + damage + " damage! HP: " + enemy.getHp());
        if (enemy.getHp() == 0) {
            Random rand = new Random();
            int goldGet = 5 + rand.nextInt(11);
            player.changeGold(goldGet); // Reward between 5 to 15 gold
            System.out.println("Victory");
            player.changeBuff("reset");
            return new AttackResponse(null,player.getHp(), player.getMp(), 0, skills.get(skill).getMaxUses(), true ,player.getGold());
        } else {
            enemy.attackPlayer(player);
            return new AttackResponse(null,player.getHp(), player.getMp(), enemy.getHp(), skills.get(skill).getMaxUses(), false,player.getGold());
        }
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
        System.out.println("Using StrengthPotion");
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

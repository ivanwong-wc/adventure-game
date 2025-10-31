package com.example.character;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.example.models.Skill;

@Component
public interface Character {
    public int getAttack();
    public int getHp();
    public int getMp(); 
    public int getGold();
    public double getLuck();
    public List<String> getInventory();
    public Map<String,Skill> getSkills();
}

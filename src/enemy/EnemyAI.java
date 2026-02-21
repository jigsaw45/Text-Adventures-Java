package enemy;

import player.Player;
import skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class EnemyAI {
    public Skill botSkillChoice(Enemy enemy) {
        Skill highestDamage = new Skill();
        Skill lowestStaminaCost = new Skill("high stamina", 0, 9999);
        List<Skill> affordableSkills = new ArrayList<>();

        //puts all skill enemy can afford into an arrayList
        for (int i = 0; i < 4; i++) {
            Skill tempSkill = enemy.getSkillLoadout().getEquipped()[i];

            if (tempSkill.getStaminaCost() <= enemy.getStats().getCurrentStamina()) {
                affordableSkills.add(tempSkill);
            }
        }

        for (Skill affordableSkill : affordableSkills) {
            //checks for highest damage skill
            if (affordableSkill.getBasePower() > highestDamage.getBasePower()) {
                highestDamage = affordableSkill;
            }
        }
        //if enemy has max stamina do the highest damaging Skills else do a random one
        if(enemy.getStats().getCurrentStamina()==enemy.getStats().getMaxStamina()){
            return highestDamage;
        } else{
            return(affordableSkills.get((int)(Math.random()*((affordableSkills.size()-1)+1))));
        }
    }
}

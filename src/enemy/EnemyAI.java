package enemy;

import combat.Stance;
import skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class EnemyAI {
    //picks a skill the enemy can actually pay for, then leans on its role
    public Skill botSkillChoice(Enemy enemy) {
        List<Skill> affordableSkills = new ArrayList<>();

        for (Skill skill : enemy.getSkillLoadout().getEquipped()) {
            if (skill == null) {
                continue;
            }
            if (enemy.getStats().canAffordStamina(skill.getStaminaCost())
                    && enemy.getStats().canAffordQi(skill.getQiCost())
                    && enemy.getStats().getCurrentHP() > skill.getHpCost()) {
                affordableSkills.add(skill);
            }
        }
        //never crash on an empty list, fall back to a plain punch
        if (affordableSkills.isEmpty()) {
            return new Skill();
        }

        switch (enemy.getRole()) {
            case BERSERKER:
                return highestPower(affordableSkills);
            case CASTER:
                List<Skill> elemental = elementalOnly(affordableSkills);
                return elemental.isEmpty() ? highestPower(affordableSkills) : highestPower(elemental);
            case TANK:
                return cheapest(affordableSkills);
            case DUELIST:
            default:
                return affordableSkills.get((int) (Math.random() * affordableSkills.size()));
        }
    }

    //stance follows role, with a couple of readable exceptions
    public Stance botStanceChoice(Enemy enemy){
        int hpPercent = (enemy.getStats().getCurrentHP() * 100) / enemy.getStats().getMaxHP();
        if(hpPercent < 30 && enemy.getRole() != EnemyRole.TANK){
            return Stance.AGGRESSIVE;
        }
        if(hpPercent > 70 && enemy.getRole() == EnemyRole.CASTER){
            return Stance.FOCUSED;
        }
        return enemy.getRole().getPreferredStance();
    }

    private Skill highestPower(List<Skill> skills){
        Skill highest = skills.get(0);
        for(Skill skill : skills){
            if(skill.getBasePower() > highest.getBasePower()){
                highest = skill;
            }
        }
        return highest;
    }

    private Skill cheapest(List<Skill> skills){
        Skill cheapest = skills.get(0);
        for(Skill skill : skills){
            if(skill.getStaminaCost() < cheapest.getStaminaCost()){
                cheapest = skill;
            }
        }
        return cheapest;
    }

    private List<Skill> elementalOnly(List<Skill> skills){
        List<Skill> elemental = new ArrayList<>();
        for(Skill skill : skills){
            if(skill.getSkillType() == 2){
                elemental.add(skill);
            }
        }
        return elemental;
    }
}

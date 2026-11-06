package enemy;

import combat.Stats;
import skills.SkillLoadout;

public class Enemy{
    private String name;
    private Stats stats;
    private SkillLoadout skillLoadout;


    public Enemy(String name, int vitality, int strength, int agility, int focus, int stamina, int mastery, int level, SkillLoadout skillLoadout){
        this.name = name;
        this.stats = new Stats(vitality, strength, agility, focus, stamina, mastery, level);
        this.skillLoadout = skillLoadout;
    }
    public String getName(){
        return this.name;
    }
    public Stats getStats(){
        return this.stats;
    }
    public SkillLoadout getSkillLoadout() {
        return skillLoadout;
    }

}

package enemy;

import combat.Stats;
import player.Inventory;
import player.Realm;
import skills.SkillLoadout;
import world.Room;

public class Enemy{
    private String name;
    private Stats stats;
    private int currentHP;
    private SkillLoadout skillLoadout;


    public Enemy(String name, int vitality, int strength, int agility, int focus, int stamina, int mastery, Realm realm, int stage, SkillLoadout skillLoadout){
        this.name = name;
        this.stats = new Stats(vitality, strength, agility, focus, stamina, mastery, realm, stage);
        this.currentHP = this.stats.getMaxHP();
        this.skillLoadout = skillLoadout;
    }
    public String getName(){
        return this.name;
    }
    public Stats getStats(){
        return this.stats;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public SkillLoadout getSkillLoadout() {
        return skillLoadout;
    }

}

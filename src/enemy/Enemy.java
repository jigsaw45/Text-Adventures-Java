package enemy;

import combat.Stats;
import player.Inventory;
import world.Room;

public class Enemy{
    private String name;
    private Stats stats;
    private int currentHP;


    public Enemy(String name, int vitality, int strength, int agility, int focus, int stamina, int mastery, int level){
        this.name = name;
        this.stats = new Stats(vitality, strength, agility, focus, stamina, mastery, level);
        this.currentHP = this.stats.getMaxHP();
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
}

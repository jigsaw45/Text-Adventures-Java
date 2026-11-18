package enemy;

import combat.Element;
import combat.Stats;
import player.Realm;
import skills.SkillLoadout;

public class Enemy{
    private String name;
    private Stats stats;
    private SkillLoadout skillLoadout;
    private EnemyRole role;
    private int xpReward;
    private int stoneReward;
    private String deathText;

    public Enemy(String name, int vitality, int strength, int agility, int focus, int stamina, Realm realm, int stage,
                 SkillLoadout skillLoadout, EnemyRole role, Element element, int xpReward, int stoneReward){
        this.name = name;
        this.stats = new Stats(vitality, strength, agility, focus, stamina, realm, stage);
        this.stats.setElement(element);
        this.skillLoadout = skillLoadout;
        this.role = role;
        this.xpReward = xpReward;
        this.stoneReward = stoneReward;
        this.deathText = name + " falls, and the mountain does not care.";
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
    public EnemyRole getRole(){
        return role;
    }
    public int getXpReward(){
        return xpReward;
    }
    public int getStoneReward(){
        return stoneReward;
    }
    public String getDeathText(){
        return deathText;
    }
    public void setDeathText(String deathText){
        this.deathText = deathText;
    }
}

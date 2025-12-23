package combat;

public class Stats {
    private int vitality;
    private int strength;
    private int agility;
    private int focus;
    private int stamina;
    private int mastery;
    private int level;
    private int xp;
    private int points;

    public Stats(int vitality, int strength, int agility, int focus, int stamina, int mastery, int level){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.mastery = mastery;
        this.level = level;
        this.xp = 0;
        this.points = 0;
    }

    public int getMaxHP(){
        return (this.vitality*10) + (getLevel()*5);
    }
    public int getPhysicalDamage(){
        return (this.strength*2)+Math.toIntExact(Math.round(0.6*(this.agility)));
    }
    public int getDodgeChance(){
        return Math.toIntExact((Math.round(this.agility * 1.5) + getLevel()));
    }
    public int getElementalDamage(){
        return (this.focus*3)+Math.toIntExact(Math.round(0.3*(this.agility)));
    }
    public int getMaxStamina(){
        return (this.stamina*3)+(getLevel()*2);
    }
    public int getCritChance(){
        return this.agility+15;
    }
    public int damageResistance(){
        return this.vitality+10;
    }

    public int getVitality(){
        return this.vitality;
    }
    public int getStrength(){
        return this.strength;
    }
    public int getAgility(){
        return this.agility;
    }
    public int getFocus(){
        return this.focus;
    }
    public int getStamina(){
        return this.stamina;
    }
    public int getMastery(){
        return this.mastery;
    }
    public int getLevel(){
        return this.level;
    }
    public int getXp(){
        return this.xp;
    }
    public int getPoints(){
        return this.points;
    }

    public void increaseVitality(int amount){
        this.vitality+=amount;
    }
    public void increaseStrength(int amount){
        this.strength+=amount;
    }
    public void increaseAgility(int amount){
        this.agility+=amount;
    }
    public void increaseFocus(int amount){
        this.focus+=amount;
    }
    public void increaseStamina(int amount){
        this.stamina+=amount;
    }
    public void increaseMastery(int amount){
        this.mastery+=amount;
    }
    public void increaseLevel(int amount){
        this.level+=amount;
    }
    public void increaseXp(int amount){
        this.xp+=amount;
    }
    public void increasepoints(){
        this.points+=points;
    }

}

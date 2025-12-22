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

    public Stats(int vitality, int strength, int agility, int focus, int stamina, int mastery, int level, int xp){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.mastery = mastery;
        this.level = level;
        this.xp = xp;
    }

    public int getMaxHP(){
        return (this.vitality*10) + (getLevel()*5);
    }
    public int getPhysicalDamage(){
        return (this.strength*2)+(getLevel());
    }
    public int getDodgeChance(){
        return Math.toIntExact((Math.round(this.agility * 1.5) + getLevel()));
    }
    public int getElementalDamage(){
        return (this.focus*2)+(getLevel());
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

    public void setVitality(int amount){
        this.vitality=amount;
    }
    public void setStrength(int amount){
        this.strength=amount;
    }
    public void setAgility(int amount){
        this.agility=amount;
    }
    public void setFocus(int amount){
        this.focus=amount;
    }
    public void setStamina(int amount){
        this.stamina=amount;
    }
    public void setMastery(int amount){
        this.mastery=amount;
    }
    public void setLevel(int amount){
        this.level=amount;
    }
    public void setXp(int amount){
        this.xp=amount;
    }

}

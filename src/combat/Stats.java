package combat;

public class Stats {
    private int vitality;
    private int strength;
    private int agility;
    private int focus;
    private int stamina;
    private int mastery;
    private int momentum;
    private int level;
    private int xp;
    private int points;
    private int currentHP;
    private int currentStamina;

    public Stats(int vitality, int strength, int agility, int focus, int stamina, int mastery,  int level){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.mastery = mastery;
        this.momentum = 0;
        this.level = level;
        this.xp = 0;
        this.points = 0;
        this.currentHP = getMaxHP();
        this.currentStamina = getMaxStamina();
    }

    //derived values
    public int getMaxHP(){
        return (this.vitality*10) + (getLevel()*5);
    }
    public int getMaxStamina(){
        return (this.stamina*15)+(getLevel()*5);
    }
    public int getPhysicalDamage(){
        return (this.strength*2)+Math.toIntExact(Math.round(0.6*(this.agility)));
    }
    public int getDodgeChance(){
        if(((int) (this.agility * .5) + getLevel()* .2 )>90){
            return 90;
        }
        else{
            return (int) ((this.agility * .5) + getLevel()* .2);
        }
    }
    public int getElementalDamage(){
        return (this.focus*3)+Math.toIntExact(Math.round(0.3*(this.agility)));
    }
    public int getCritChance(){
        if(this.agility>=85){
            return 100;
        }
        else{
            return this.agility+15;
        }
    }
    public int getDefence(){
        return this.vitality+10;
    }

    //base stats
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

    //increase base stats
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

    //Level, XP, & points
    public int getLevel(){
        return this.level;
    }
    public int getXp(){
        return this.xp;
    }
    public int getPoints(){
        return this.points;
    }
    public void increaseLevel(int amount){
        this.level+=amount;
    }
    public void increaseXp(int amount){
        this.xp+=amount;
    }
    public void increasePoints(int amount){
        this.points+=amount;
    }

    //Combat engine/damage calculation components
    public int getMomentum(){
        return this.momentum;
    }
    public void changeMomentum(int amount){
        this.momentum+=amount;
        if(this.momentum<0){
            this.momentum = 0;
        }
        else if(this.momentum>100){
            this.momentum=100;
        }
    }

    //currentHP altercation
    public int getCurrentHP() {
        return currentHP;
    }
    public void recoverHP(){
            this.currentHP = getMaxHP();
    }
    public void changeCurrentHP(int amount){
        this.currentHP += amount;
    }

    //currentStamina altercation
    public int getCurrentStamina() {
        return currentStamina;
    }
    public void recoverStamina(){
        this.currentStamina = getStamina();
    }
    public void spendStamina(int cost){
        if(this.canAffordStamina(cost)) {
            this.currentStamina -= cost;
        }
    }
    public boolean canAffordStamina(int cost){
        return cost <= this.currentStamina;
    }



}

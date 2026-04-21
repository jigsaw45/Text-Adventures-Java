package combat;

import player.Realm;
import player.RealmSystem;

public class Stats {
    private int vitality;
    private int strength;
    private int agility;
    private int focus;
    private int stamina;
    private int mastery;
    private int momentum;
    private Realm currentRealm;
    private int currentStage;
    private int points;
    private int currentHP;
    private int currentStamina;
    private RealmSystem realmSystem;
    private int currentxp;

    public Stats(int vitality, int strength, int agility, int focus, int stamina, int mastery, Realm currentRealm, int currentStage){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.mastery = mastery;
        this.momentum = 0;
        this.currentRealm = currentRealm;
        this.currentStage = currentStage;
        this.points = 0;
        this.currentHP = getMaxHP();
        this.currentStamina = getMaxStamina();
        this.realmSystem = new RealmSystem();
    }


    //when enemy dies add player.getStats().addXP(enemy.getStats().getXPReward());



    //derived values
    public int getMaxHP(){
        return (this.vitality*10) + (getTotalStages()*5);
    }
    public int getMaxStamina(){
        return (this.stamina*15)+(getTotalStages()*5);
    }
    public int getPhysicalDamage(){
        return (this.strength*2)+Math.toIntExact(Math.round(0.6*(this.agility)));
    }
    public int getDodgeChance(){
        if(((int) (this.agility * .5) + getTotalStages()* .2 )>90){
            return 90;
        }
        else{
            return (int) ((this.agility * .5) + getTotalStages()* .2);
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
    public int getRequiredXP() {
        return 100 + (getTotalStages() * 50);
    }
    public int getXPReward() {
        return 20 + (getTotalStages() * 10);
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

    //Realms, stages, & points
    public Realm getcurrentRealm(){
        return this.currentRealm;
    }
    public int getCurrentStage(){
        return this.currentStage;
    }
    public int getTotalStages(){
        return realmSystem.findRealmIndex(this.currentRealm)*9 + this.currentStage;
    }
    public int getCurrentXP() { return this.currentxp; }
    public int getPoints(){
        return this.points;
    }
    public void increaseRealm(){this.currentRealm = RealmSystem.getREALMS()[realmSystem.findRealmIndex(currentRealm)+1];
    }
    public void increaseStage(){
        this.currentStage+=1;
        increasePoints(5);
        if(currentStage==9){
            increaseRealm();
            currentStage=1;
        }
    }
    public void increasePoints(int amount){
        this.points+=amount;
    }
    public void increaseXP(int amount) {
        this.currentxp += amount;
        if (this.currentxp >= getRequiredXP()) {
            this.currentxp -= getRequiredXP();
            increaseStage();
        }
    }



    //Combat engine/damage calculation components
    public int getMomentum(){
        return this.momentum;
    }
    public void setMomentum(int amount){
        this.momentum = amount;
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
    public void setCurrentHP(int amount){this.currentHP = amount;}
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
    public void setCurrentStamina(int amount){this.currentStamina = amount;}
    public void recoverStamina(){
        this.currentStamina = getMaxStamina();
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

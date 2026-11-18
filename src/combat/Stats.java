package combat;

import player.Realm;
import player.RealmSystem;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private int vitality;
    private int strength;
    private int agility;
    private int focus;
    private int stamina;
    private int momentum;
    private Realm currentRealm;
    private int currentStage;
    private int points;
    private int currentHP;
    private int currentStamina;
    private int currentQi;
    private int currentXP;
    private Element element;
    private final RealmSystem realmSystem;
    private final List<StatusEffect> statuses;

    public Stats(int vitality, int strength, int agility, int focus, int stamina, Realm currentRealm, int currentStage){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.momentum = 0;
        this.realmSystem = new RealmSystem();
        this.currentRealm = currentRealm;
        this.currentStage = currentStage;
        this.element = Element.NONE;
        this.statuses = new ArrayList<>();
        this.points = 0;
        this.currentXP = 0;
        this.currentHP = getMaxHP();
        this.currentStamina = getMaxStamina();
        this.currentQi = getMaxQi();
    }

    //when enemy dies add player.getStats().increaseXp(enemy.getXpReward());

    //derived values
    public int getMaxHP(){
        return (this.vitality*10) + (getTotalStages()*5);
    }
    public int getMaxStamina(){
        return (this.stamina*15) + (getTotalStages()*5);
    }
    public int getMaxQi(){
        return (this.focus*6) + (getTotalStages()*3);
    }
    public int getPhysicalDamage(){
        return (this.strength*2) + Math.toIntExact(Math.round(0.6*(this.agility)));
    }
    public int getDodgeChance(){
        int chance = (int) ((this.agility * .5) + (getTotalStages() * .2));
        if(chance > 60){
            return 60;
        }
        return chance;
    }
    public int getElementalDamage(){
        return (this.focus*3) + Math.toIntExact(Math.round(0.3*(this.agility)));
    }
    public int getCritChance(){
        int chance = 5 + (this.agility/2);
        if(chance > 50){
            return 50;
        }
        return chance;
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
    public Element getElement(){
        return this.element;
    }
    public void setElement(Element element){
        this.element = element;
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

    //Realms, stages, & points
    public Realm getCurrentRealm(){
        return this.currentRealm;
    }
    public int getCurrentStage(){
        return this.currentStage;
    }
    public int getTotalStages(){
        return realmSystem.findRealmIndex(this.currentRealm)*9 + this.currentStage;
    }
    public int getCurrentXP() { return this.currentXP; }
    public int getPoints(){
        return this.points;
    }
    public int getRealmIndex(){
        return realmSystem.findRealmIndex(this.currentRealm);
    }
    public boolean canBreakthrough(){
        return this.currentStage >= 9 && this.currentXP >= getRequiredXP();
    }
    public boolean isMaxRealm(){
        return getRealmIndex() >= RealmSystem.getREALMS().length-1;
    }

    //stages happen on their own, realms have to be attempted
    public void increaseStage(){
        if(this.currentStage < 9){
            this.currentStage++;
            increasePoints(5 + getRealmIndex());
        }
    }
    public void increaseRealm(){
        int next = realmSystem.findRealmIndex(this.currentRealm)+1;
        if(next < RealmSystem.getREALMS().length){
            this.currentRealm = RealmSystem.getREALMS()[next];
        }
    }
    public void increasePoints(int amount){
        this.points+=amount;
    }
    public void increaseXp(int amount) {
        if(amount < 0){
            loseXp(-amount);
            return;
        }
        this.currentXP += amount;
        while(this.currentXP >= getRequiredXP() && this.currentStage < 9){
            this.currentXP -= getRequiredXP();
            increaseStage();
        }
        //at stage 9 the bar fills and waits for a breakthrough
        if(this.currentStage >= 9 && this.currentXP > getRequiredXP()){
            this.currentXP = getRequiredXP();
        }
    }
    public void loseXp(int amount){
        this.currentXP = Math.max(0, this.currentXP - amount);
    }
    public void breakthroughSucceeded(){
        increaseRealm();
        this.currentStage = 1;
        this.currentXP = 0;
        increasePoints(5 + (getRealmIndex()*2));
        recoverHP();
        recoverStamina();
        recoverQi();
    }
    public void breakthroughFailed(){
        this.currentXP = this.currentXP/2;
        this.currentHP = Math.max(1, this.currentHP/2);
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
    public boolean spendMomentum(int amount){
        if(this.momentum >= amount){
            this.momentum -= amount;
            return true;
        }
        return false;
    }

    //currentHP altercation
    public int getCurrentHP() {
        return currentHP;
    }
    public void setCurrentHP(int amount){
        this.currentHP = amount;
        if(this.currentHP < 0){
            this.currentHP = 0;
        }
    }
    public void recoverHP(){
            this.currentHP = getMaxHP();
    }
    public void changeCurrentHP(int amount){
        this.currentHP += amount;
        if(this.currentHP < 0){
            this.currentHP = 0;
        }
        if(this.currentHP > getMaxHP()){
            this.currentHP = getMaxHP();
        }
    }
    public void heal(int amount){
        changeCurrentHP(amount);
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

    //currentQi altercation
    public int getCurrentQi(){
        return currentQi;
    }
    public void setCurrentQi(int amount){this.currentQi = amount;}
    public void recoverQi(){
        this.currentQi = getMaxQi();
    }
    public void spendQi(int cost){
        if(this.canAffordQi(cost)){
            this.currentQi -= cost;
        }
    }
    public boolean canAffordQi(int cost){
        return cost <= this.currentQi;
    }

    //status effects
    public List<StatusEffect> getStatuses(){
        return this.statuses;
    }
    public void addStatus(StatusType type, int turns, int damage){
        for(StatusEffect effect : statuses){
            if(effect.getType() == type){
                //one stack only, refresh the duration instead
                statuses.remove(effect);
                break;
            }
        }
        statuses.add(new StatusEffect(type, turns, damage));
    }
    public boolean isStunned(){
        for(StatusEffect effect : statuses){
            if(effect.isStun()){
                return true;
            }
        }
        return false;
    }
    //a stun is spent when it costs you a turn, otherwise it would tick away before it ever mattered
    public boolean consumeStun(){
        for(int i = statuses.size()-1; i >= 0; i--){
            if(statuses.get(i).isStun()){
                statuses.remove(i);
                return true;
            }
        }
        return false;
    }
    public int tickStatuses(){
        int total = 0;
        for(int i = statuses.size()-1; i >= 0; i--){
            StatusEffect effect = statuses.get(i);
            total += effect.tick();
            if(effect.isExpired()){
                statuses.remove(i);
            }
        }
        return total;
    }
    public void clearStatuses(){
        statuses.clear();
    }
    public String getStatusText(){
        if(statuses.isEmpty()){
            return "none";
        }
        String text = "";
        for(StatusEffect effect : statuses){
            text += effect.toString() + " ";
        }
        return text.trim();
    }

    //save/load support
    public void applyState(int vitality, int strength, int agility, int focus, int stamina, int points,
                           int currentXP, Realm realm, int stage, int currentHP, int currentStamina, int currentQi, int momentum,
                           Element element){
        this.vitality = vitality;
        this.strength = strength;
        this.agility = agility;
        this.focus = focus;
        this.stamina = stamina;
        this.points = points;
        this.currentXP = currentXP;
        this.currentRealm = realm;
        this.currentStage = stage;
        this.currentHP = currentHP;
        this.currentStamina = currentStamina;
        this.currentQi = currentQi;
        this.momentum = momentum;
        this.element = element;
    }
}

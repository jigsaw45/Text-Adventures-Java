package skills;

public class Skill {
    private String name;
    private int basePower;
    private int staminaCost;
    private int skillType;
    private boolean owned;

    public Skill(){
        this.name = "Hopeful punch";
        this.basePower = 5;
        this.staminaCost = 5;
        this.skillType = 1;
        this.owned = false;
    }
    public Skill(String name, int basePower, int staminaCost, int skillType){
        this.name = name;
        this.basePower=basePower;
        this.staminaCost = staminaCost;
        this.skillType = skillType;
        this.owned = false;
    }

    public String getName() {
        return name;
    }
    public int getBasePower() {
        return basePower;
    }
    public int getStaminaCost(){
        return staminaCost;
    }
    public int getSkillType(){
        return skillType;
    }
    public boolean getOwned(){return owned;}

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }
    public void setOwned(boolean isOwned){
        this.owned = isOwned;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.basePower + " dmg, " + this.staminaCost + " stamina)";
    }
}

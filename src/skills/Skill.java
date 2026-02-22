package skills;

public class Skill {
    private String name;
    private int basePower;
    private int staminaCost;
    private int skillType;

    public Skill(){
        this.name = "empty";
        this.basePower = 0;
        this.staminaCost = 0;
        this.skillType = 1;
    }
    public Skill(String name, int basePower, int staminaCost, int skillType){
        this.name = name;
        this.basePower=basePower;
        this.staminaCost = staminaCost;
        this.skillType = skillType;
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
}

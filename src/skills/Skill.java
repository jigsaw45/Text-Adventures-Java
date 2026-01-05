package skills;

public class Skill {
    private String name;
    private int basePower;
    private int staminaCost;

    public Skill(String name, int basePower, int staminaCost){
        this.name = name;
        this.basePower=basePower;
        this.staminaCost = staminaCost;
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
}

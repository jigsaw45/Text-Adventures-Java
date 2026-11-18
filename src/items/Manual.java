package items;

public class Manual extends Item {
    private int skillIndex;

    public Manual(String name, int skillIndex, String description, int value) {
        super(name, 1, description, value);
        this.skillIndex = skillIndex;
    }

    //-1 means the manual teaches an affinity instead of a single technique
    public int getSkillIndex() {
        return skillIndex;
    }

    public void displayInfo() {
        System.out.println("Manual: " + getName() + ", " + getDescription());
    }
}

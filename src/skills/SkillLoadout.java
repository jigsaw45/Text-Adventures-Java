package skills;

public class SkillLoadout {
    private static final int MAX_SLOTS = 4;
    private Skill[] equipped = new Skill[MAX_SLOTS];

    public SkillLoadout(){
        equipped[0] = new Skill();
        equipped[1] = new Skill();
        equipped[2] = new Skill();
        equipped[3] = new Skill();
    }
    public SkillLoadout(Skill one, Skill two, Skill three, Skill four){
        equipped[0] = one;
        equipped[1] = two;
        equipped[2] = three;
        equipped[3] = four;
    }

    public Skill[] getEquipped(){
        return equipped;
    }

    public void SetEquipped(Skill skill, int index){
        this.equipped[index] = skill;
    }


}


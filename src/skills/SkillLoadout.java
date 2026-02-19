package skills;

public class SkillLoadout {
    private static final int MAX_SLOTS = 4;
    private Skill[] equipped = new Skill[MAX_SLOTS];

    public SkillLoadout(Skill one, Skill two, Skill three, Skill four){
        equipped[0] = one;
        equipped[1] = two;
        equipped[2] = three;
        equipped[3] = four;
    }

    public Skill[] getEquipped(){
        return equipped;
    }

    public void SetEquipped(Skill[] equipped){
        this.equipped = equipped;
    }


}


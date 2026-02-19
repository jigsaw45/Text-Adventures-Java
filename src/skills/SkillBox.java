package skills;

import java.util.ArrayList;
import java.util.List;

public class SkillBox {

    private Skill[] ownedSkills = new Skill[4];


    public SkillBox(){
        ownedSkills[0] = new Skill("Light Punch", 10,10);
        ownedSkills[1] = new Skill("Heavy Punch", 20,30);
        ownedSkills[2] = new Skill("Light Kick", 15, 20);
        ownedSkills[3] = new Skill("Heavy Kick", 30, 40);
    }

    public Skill[] getOwnedSkills() {
        return ownedSkills;
    }

    public void setOwnedSkills(Skill[] ownedSkills) {
        this.ownedSkills = ownedSkills;
    }
}

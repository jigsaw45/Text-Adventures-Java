package skills;

import java.util.ArrayList;
import java.util.List;

public class SkillBox {

    private static final Skill[] SKILLINDEX = new Skill[8];


    public SkillBox(){
        SKILLINDEX[0] = new Skill("Light Punch", 10,10);
        SKILLINDEX[1] = new Skill("Heavy Punch", 20,30);
        SKILLINDEX[2] = new Skill("Light Kick", 15, 20);
        SKILLINDEX[3] = new Skill("Heavy Kick", 30, 40);
        SKILLINDEX[4] = new Skill("attackOne", 10,10);
        SKILLINDEX[5] = new Skill("attackTwo", 20,30);
        SKILLINDEX[6] = new Skill("attackThree", 15, 20);
        SKILLINDEX[7] = new Skill("attackFour", 30, 40);
    }

    public Skill[] getSkillIndex() {
        return SKILLINDEX;
    }
}

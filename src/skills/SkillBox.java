package skills;

import java.util.ArrayList;
import java.util.List;

public class SkillBox {

    private static final Skill[] SKILLINDEX = new Skill[100];


    public SkillBox(){
        //Physical Techniques

        //Iron Mountain Fist Manual (Heavy, powerful style)
        SKILLINDEX[0] = new Skill("Mountain Breaking Palm",28,35,1);
        SKILLINDEX[1] = new Skill("Stone Crushing Blow",35,45,1);
        SKILLINDEX[2] = new Skill("Iron Body Strike",22,28,1);
        //Flowing Serpent Style (Light, quick style):
        SKILLINDEX[3] = new Skill("Serpent's Coil Strike",30, 40,1);
        SKILLINDEX[4] = new Skill("River Current Palm",10,10,1);
        SKILLINDEX[5] = new Skill("Viper's Fang Jab",20,30,1);

        //Elemental Techniques

        //Blazing Phoenix Manual(Fire - aggressive, high damage)
        SKILLINDEX[6] = new Skill("Flickering Flame Palm",24,32, 2);
        SKILLINDEX[7] = new Skill("Ember Burst Technique",30,40,2);
        SKILLINDEX[8] = new Skill("Phoenix Feather Strike",18,24,2);
        //Earthen Tortoise Manual (Earth - steady, efficient)
        SKILLINDEX[9] = new Skill("Earthen Spike Technique",20,25,2);
        SKILLINDEX[10] = new Skill("Stone Wall Palm",25,32,2);
        //Storm Dragon Manual (Lightning/Wind - fast, precise):
        SKILLINDEX[11] = new Skill("Lightning Finger Jab",14,18,2);
        SKILLINDEX[12] = new Skill("Wind Cutting Blade",22,28,2);
        SKILLINDEX[13] = new Skill("Thunder Crack Strike",26,35,2);




    }

    public Skill[] getSkillIndex() {
        return SKILLINDEX;
    }
}

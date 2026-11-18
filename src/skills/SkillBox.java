package skills;

import combat.Element;

public class SkillBox {

    private static final Skill[] SKILLINDEX = new Skill[16];


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
        SKILLINDEX[6] = new Skill("Flickering Flame Palm",24,10,2, Element.FIRE,8,0);
        SKILLINDEX[7] = new Skill("Ember Burst Technique",30,12,2, Element.FIRE,12,0);
        SKILLINDEX[8] = new Skill("Phoenix Feather Strike",18,8,2, Element.FIRE,6,0);
        //Earthen Tortoise Manual (Earth - steady, efficient)
        SKILLINDEX[9] = new Skill("Earthen Spike Technique",20,9,2, Element.EARTH,8,0);
        SKILLINDEX[10] = new Skill("Stone Wall Palm",25,10,2, Element.EARTH,10,0);
        //Storm Dragon Manual (Lightning/Wind - fast, precise):
        SKILLINDEX[11] = new Skill("Lightning Finger Jab",14,6,2, Element.LIGHTNING,5,0);
        SKILLINDEX[12] = new Skill("Wind Cutting Blade",22,10,2, Element.LIGHTNING,9,0);
        SKILLINDEX[13] = new Skill("Thunder Crack Strike",26,12,2, Element.LIGHTNING,12,0);

        //Blood Arts (cost HP, hit far above their weight)
        SKILLINDEX[14] = new Skill("Blood Igniting Palm",40,10,1, Element.NONE,0,15);
        SKILLINDEX[15] = new Skill("Crimson Sacrifice",45,5,2, Element.FIRE,0,25);
    }
    public Skill[] getSkillIndex() {
        return SKILLINDEX;
    }

}

package skills;

import combat.Element;

public class Skill {
    private String name;
    private int basePower;
    private int staminaCost;
    private int qiCost;
    private int hpCost;
    private int skillType;
    private Element element;
    private boolean owned;

    public Skill(){
        this.name = "Hopeful punch";
        this.basePower = 5;
        this.staminaCost = 5;
        this.qiCost = 0;
        this.hpCost = 0;
        this.skillType = 1;
        this.element = Element.NONE;
        this.owned = false;
    }
    public Skill(String name, int basePower, int staminaCost, int skillType){
        this(name, basePower, staminaCost, skillType, Element.NONE, 0, 0);
    }
    public Skill(String name, int basePower, int staminaCost, int skillType, Element element, int qiCost, int hpCost){
        this.name = name;
        this.basePower=basePower;
        this.staminaCost = staminaCost;
        this.qiCost = qiCost;
        this.hpCost = hpCost;
        this.skillType = skillType;
        this.element = element;
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
    public int getQiCost(){
        return qiCost;
    }
    public int getHpCost(){
        return hpCost;
    }
    public int getSkillType(){
        return skillType;
    }
    public Element getElement(){
        return element;
    }
    public boolean getOwned(){return owned;}

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }
    public void setOwned(boolean isOwned){
        this.owned = isOwned;
    }
    public void setElement(Element element){
        this.element = element;
    }

    public String costText(){
        String text = "";
        if(this.staminaCost>0){
            text += this.staminaCost + " sta";
        }
        if(this.qiCost>0){
            text += (text.isEmpty() ? "" : " ") + this.qiCost + " qi";
        }
        if(this.hpCost>0){
            text += (text.isEmpty() ? "" : " ") + this.hpCost + " hp";
        }
        return text.isEmpty() ? "free" : text;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.basePower + " pwr, " + costText() + ")";
    }
}

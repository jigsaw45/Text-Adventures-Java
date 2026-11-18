package Npc;

public class QuestObjective {
    private QuestType type;
    private String target;
    private int required;
    private int current;

    public QuestObjective(QuestType type, String target, int required){
        this.type = type;
        this.target = target;
        this.required = required;
        this.current = 0;
    }

    public QuestType getType(){
        return type;
    }
    public String getTarget(){
        return target;
    }
    public int getRequired(){
        return required;
    }
    public int getCurrent(){
        return current;
    }
    public void setCurrent(int current){
        this.current = current;
    }

    public boolean matches(QuestType type, String target){
        return this.type == type && this.target.equalsIgnoreCase(target);
    }

    public void addProgress(int amount){
        current = Math.min(required, current + amount);
    }

    public boolean isComplete(){
        return current >= required;
    }

    public String getText(){
        return "  - " + type.getAction() + " " + target + " (" + current + "/" + required + ")";
    }
}

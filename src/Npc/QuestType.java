package Npc;

public enum QuestType {
    KILL("defeat"),
    FETCH("gather"),
    ESCORT("travel to"),
    INVESTIGATE("investigate"),
    DUEL("duel"),
    STORY("story");

    private final String action;

    QuestType(String action){
        this.action = action;
    }

    public String getAction(){
        return this.action;
    }
}

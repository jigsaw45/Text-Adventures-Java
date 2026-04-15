package Npc;

public class Quest {
    private String questName;
    private String questDescription;
    private boolean questStarted;
    private boolean questComplete;

    public Quest(String questName, String questDescription, boolean questStarted, boolean questComplete){
        this.questName = questName;
        this.questDescription = questDescription;
        this.questStarted = false;
        this.questComplete = false;
    }
    public void start(){
        this.questStarted = true;
    }
    public void setQuestComplete(){
        this.questComplete = true;
    }

    public boolean isQuestComplete() {
        return questComplete;
    }

    public boolean isQuestStarted() {
        return questStarted;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public String getQuestName() {
        return questName;
    }

}


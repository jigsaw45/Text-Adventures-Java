package NPCs;

public class Quest {
    private String questName;
    private String questDescription;
    private boolean questComplete;
    private String type;

    public Quest(String questName, String questDescription, boolean questComplete, String type){
        this.questName = questName;
        this.questDescription = questDescription;
        this.questComplete = questComplete;
        this.type = type;
    }

    public void displayQuest(){
        System.out.print("("+questName+")"+"("+questDescription+")");
    }

    public boolean isQuestComplete() {
        return questComplete;
    }

    public void setQuestComplete(boolean questComplete) {
        this.questComplete = questComplete;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

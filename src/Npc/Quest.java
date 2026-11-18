package Npc;

import java.util.ArrayList;

public class Quest {
    private String id;
    private String questName;
    private String questDescription;
    private QuestType type;
    private QuestState state;
    private ArrayList<QuestObjective> objectives;
    private ArrayList<String> itemRewards;
    private int xpReward;
    private int stoneReward;
    private String prerequisite;

    public Quest(String id, String questName, String questDescription, QuestType type, String prerequisite, int xpReward, int stoneReward){
        this.id = id;
        this.questName = questName;
        this.questDescription = questDescription;
        this.type = type;
        this.state = QuestState.LOCKED;
        this.objectives = new ArrayList<>();
        this.itemRewards = new ArrayList<>();
        this.xpReward = xpReward;
        this.stoneReward = stoneReward;
        this.prerequisite = prerequisite;
    }

    public void start(){
        this.state = QuestState.ACTIVE;
    }
    public void complete(){
        this.state = QuestState.COMPLETE;
    }
    public void setQuestComplete(){
        this.state = QuestState.COMPLETE;
    }
    public void addObjective(QuestObjective objective){
        objectives.add(objective);
    }
    public void addItemReward(String itemName){
        itemRewards.add(itemName);
    }

    public boolean isQuestComplete() {
        return state == QuestState.COMPLETE;
    }
    public boolean isQuestStarted() {
        return state == QuestState.ACTIVE || state == QuestState.COMPLETE;
    }
    public boolean isActive(){
        return state == QuestState.ACTIVE;
    }
    public boolean isAvailable(){
        return state == QuestState.AVAILABLE;
    }
    public boolean objectivesComplete(){
        for(QuestObjective objective : objectives){
            if(!objective.isComplete()){
                return false;
            }
        }
        return true;
    }
    public void addProgress(QuestType type, String target){
        for(QuestObjective objective : objectives){
            if(objective.matches(type, target)){
                objective.addProgress(1);
            }
        }
    }
    //reverse of addProgress, used when an item is handed in
    public void removeProgress(QuestType type, String target, int amount){
        for(QuestObjective objective : objectives){
            if(objective.matches(type, target)){
                objective.setCurrent(Math.max(0, objective.getCurrent()-amount));
            }
        }
    }

    public String getObjectiveText(){
        String text = "";
        for(QuestObjective objective : objectives){
            text += objective.getText() + "\n";
        }
        return text;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public String getQuestName() {
        return questName;
    }
    public String getId(){
        return id;
    }
    public QuestType getType(){
        return type;
    }
    public QuestState getState(){
        return state;
    }
    public void setState(QuestState state){
        this.state = state;
    }
    public String getPrerequisite(){
        return prerequisite;
    }
    public int getXpReward(){
        return xpReward;
    }
    public int getStoneReward(){
        return stoneReward;
    }
    public ArrayList<String> getItemRewards(){
        return itemRewards;
    }
    public ArrayList<QuestObjective> getObjectives(){
        return objectives;
    }

    @Override
    public String toString(){
        return "[" + state + "] " + questName;
    }
}

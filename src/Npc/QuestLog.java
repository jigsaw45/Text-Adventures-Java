package Npc;

import java.util.ArrayList;

public class QuestLog {
    private ArrayList<Quest> quests;

    public QuestLog(){
        quests = new ArrayList<>();
    }

    public void add(Quest quest){
        quests.add(quest);
    }

    public Quest get(String id){
        for(Quest quest : quests){
            if(quest.getId().equalsIgnoreCase(id)){
                return quest;
            }
        }
        return null;
    }

    public ArrayList<Quest> getQuests(){
        return quests;
    }

    public ArrayList<Quest> getActive(){
        ArrayList<Quest> active = new ArrayList<>();
        for(Quest quest : quests){
            if(quest.isActive()){
                active.add(quest);
            }
        }
        return active;
    }

    public boolean hasCompleted(String id){
        Quest quest = get(id);
        return quest != null && quest.isQuestComplete();
    }

    //a quest becomes available once the one before it is done
    public void refresh(){
        for(Quest quest : quests){
            if(quest.getState() == QuestState.LOCKED){
                String prerequisite = quest.getPrerequisite();
                if(prerequisite == null || prerequisite.isEmpty() || hasCompleted(prerequisite)){
                    quest.setState(QuestState.AVAILABLE);
                }
            }
        }
    }

    //pushes progress into every active quest that cares about this type/target
    public ArrayList<Quest> addProgress(QuestType type, String target){
        ArrayList<Quest> touched = new ArrayList<>();
        for(Quest quest : getActive()){
            boolean matched = false;
            for(QuestObjective objective : quest.getObjectives()){
                if(objective.matches(type, target)){
                    objective.addProgress(1);
                    matched = true;
                }
            }
            if(matched){
                touched.add(quest);
            }
        }
        return touched;
    }

    public void display(){
        System.out.println("--- Quest Journal ---");
        if(getActive().isEmpty()){
            System.out.println("Nothing active. Try talking to someone with a pulse.");
        }
        for(Quest quest : getActive()){
            System.out.println("\n[Active] " + quest.getQuestName() + " (" + quest.getType().name().toLowerCase() + ")");
            System.out.println("  " + quest.getQuestDescription());
            System.out.print(quest.getObjectiveText());
        }
        for(Quest quest : quests){
            if(quest.isQuestComplete()){
                System.out.println("\n[Complete] " + quest.getQuestName());
            }
        }
    }
}

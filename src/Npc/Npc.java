package Npc;

import java.util.ArrayList;

public class Npc {
    private String name;
    private String title;
    private String description;
    private ArrayList<String> dialogue;
    private ArrayList<String> questIds;
    private boolean shopkeeper;

    public Npc(String name, String title, String description){
        this.name = name;
        this.title = title;
        this.description = description;
        this.dialogue = new ArrayList<>();
        this.questIds = new ArrayList<>();
        this.shopkeeper = false;
    }

    public String getName(){
        return name;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public ArrayList<String> getDialogue(){
        return dialogue;
    }
    public ArrayList<String> getQuestIds(){
        return questIds;
    }
    public boolean isShopkeeper(){
        return shopkeeper;
    }
    public void setShopkeeper(boolean shopkeeper){
        this.shopkeeper = shopkeeper;
    }

    //every line is deliberately one or two sentences
    public void addLine(String line){
        dialogue.add(line);
    }
    public void addQuestId(String id){
        questIds.add(id);
    }
    public String getLine(int index){
        if(dialogue.isEmpty()){
            return "...";
        }
        return dialogue.get(index % dialogue.size());
    }
    public int lineCount(){
        return dialogue.size();
    }

    @Override
    public String toString(){
        return name + ", " + title;
    }
}

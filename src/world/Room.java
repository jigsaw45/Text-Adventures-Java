package world;

import Npc.Npc;
import items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private ArrayList<Npc> npcs;
    private String enemyName;
    private boolean cleared;
    private boolean visited;

    public Room(String name, String description){
        this.name=name;
        this.description=description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.cleared = false;
        this.visited = false;
    }

    public String getRoomName(){
        return name;
    }
    public String getDescription(){
        return description;
    }

    //exits
    public void addExit(String direction, Room room){
        exits.put(direction.toLowerCase(), room);
    }
    public Room getExit(String direction){
        if(direction == null){
            return null;
        }
        return exits.get(direction.toLowerCase());
    }
    public String getExitText(){
        if(exits.isEmpty()){
            return "none";
        }
        String text = "";
        for(String direction : exits.keySet()){
            text += direction + " ";
        }
        return text.trim();
    }

    //floor items
    public void addItem(Item item){
        items.add(item);
    }
    public ArrayList<Item> getItems(){
        return items;
    }
    public Item takeItem(String itemName){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equalsIgnoreCase(itemName)){
                return items.remove(i);
            }
        }
        return null;
    }

    //occupants
    public void addNpc(Npc npc){
        npcs.add(npc);
    }
    public ArrayList<Npc> getNpcs(){
        return npcs;
    }
    public Npc getNpc(){
        return npcs.isEmpty() ? null : npcs.get(0);
    }
    public Npc getNpc(String npcName){
        for(Npc npc : npcs){
            if(npc.getName().equalsIgnoreCase(npcName)){
                return npc;
            }
        }
        //let partial names work, nobody types "Tang Seol-Hwa" every time
        for(Npc npc : npcs){
            if(npc.getName().toLowerCase().contains(npcName.toLowerCase())){
                return npc;
            }
        }
        return null;
    }
    public String getEnemyName(){
        return enemyName;
    }
    public void setEnemyName(String enemyName){
        this.enemyName = enemyName;
    }
    public boolean isCleared(){
        return cleared;
    }
    public void setCleared(boolean cleared){
        this.cleared = cleared;
    }
    public boolean isVisited(){
        return visited;
    }
    public void setVisited(boolean visited){
        this.visited = visited;
    }

    @Override
    public String toString(){
        return name;
    }
}

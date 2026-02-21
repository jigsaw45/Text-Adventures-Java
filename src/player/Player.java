package player;

import combat.Stats;
import game.Game;
import skills.SkillLoadout;
import world.Room;

public class Player {
    private String name;
    private world.Room currentRoom;
    private Stats playerStats;
    Inventory inventory = new Inventory();
    private int currentHP;
    private SkillLoadout skillLoadout;

    public Player(String name, Room startingRoom){
        this.name = name;
        this.currentRoom = startingRoom;
        this.playerStats = new Stats(10,10,10,10,100,10,1);
        this.skillLoadout = new SkillLoadout();
    }
    
    public world.Room getCurrentRoom(){
        return currentRoom;
    }
    public void setCurrentRoom(world.Room room){
        this.currentRoom = room;
    }
    public Stats getStats(){
        return this.playerStats;
    }
    public String getName(){
        return this.name;
    }
    public void levelUp(){
        this.playerStats.increaseLevel(1);
        this.playerStats.increasePoints(5);
    }
    public SkillLoadout getSkillLoadout(){
        return skillLoadout;
    }

}

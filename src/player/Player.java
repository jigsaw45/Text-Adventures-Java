package player;

import game.Game;
import world.Room;

public class Player {
    private String name;
    private world.Room currentRoom;
    private int health;
    Inventory inventory = new Inventory();

    public Player(String name, Room startingRoom){
        this.name=name;
        this.currentRoom=startingRoom;
    }
    public world.Room getCurrentRoom(){
        return currentRoom;
    }
    public void setCurrentRoom(world.Room room){
        this.currentRoom = room;
    }
}

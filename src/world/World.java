package world;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private HashMap<String, Room> rooms;
    private ArrayList<Room> roomOrder;
    private Room startRoom;

    public World(){
        this.rooms = new HashMap<>();
        this.roomOrder = new ArrayList<>();
        this.startRoom = null;
    }

    public void addRoom(Room room){
        rooms.put(room.getRoomName().toLowerCase(), room);
        roomOrder.add(room);
        if(startRoom == null){
            startRoom = room;
        }
    }

    public Room getRoom(String name){
        if(name == null){
            return null;
        }
        return rooms.get(name.toLowerCase());
    }

    public Room getStartRoom(){
        return startRoom;
    }

    public ArrayList<Room> getRooms(){
        return roomOrder;
    }

    //two way link so I only have to write each path once
    public void link(String from, String direction, String to){
        Room a = getRoom(from);
        Room b = getRoom(to);
        if(a == null || b == null){
            return;
        }
        a.addExit(direction, b);
        b.addExit(opposite(direction), a);
    }

    private String opposite(String direction){
        switch(direction.toLowerCase()){
            case "north": return "south";
            case "south": return "north";
            case "east": return "west";
            case "west": return "east";
            case "up": return "down";
            case "down": return "up";
            default: return direction;
        }
    }
}

public class Player {
    private String name;
    private Room currentRoom;
    Inventory inventory = new Inventory();

    public Player(String name, Room startingRoom){
        this.name=name;
        this.currentRoom=startingRoom;
    }
    public Room getCurrentRoom(){
        return currentRoom;
    }
    public void setCurrentRoom(Room room){
        this.currentRoom = room;
    }
}

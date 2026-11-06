package world;

public class Room {
    private String name;
    private String description;

    public Room(String name, String description){
        this.name=name;
        this.description=description;
    }

    public String getRoomName(){
        return name;
    }

    @Override
    public String toString(){
        return name + ": " + description;
    }
}

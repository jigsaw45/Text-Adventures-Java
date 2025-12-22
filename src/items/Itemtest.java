package items;

import player.Player;

public class Itemtest {
    private String name;
    private String description;

    public Itemtest(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public void use(Player player){
        System.out.println("You cant use the " + name + "right now");
    }
}

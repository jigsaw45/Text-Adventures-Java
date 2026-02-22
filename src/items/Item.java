package items;

public abstract class Item {
    private String name;
    private int quantity;
    private String description;
    //sets private variables for item class

    public Item(String name, int quantity, String description) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        //makes item and adds item template
    }
    public String getName() {
        return this.name;
        //gets the items name
    }

    public int getQuantity() {
        return this.quantity;
        //gets items quantity
    }
    public String getDescription(){
        return this.description;
    }

    abstract public void displayInfo();
}
//implementing displayInfo onto subclasses
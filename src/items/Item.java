package items;

public abstract class Item {
    private String name;
    private int quantity;
    private String description;
    private int value;
    //sets private variables for item class

    public Item(String name, int quantity, String description) {
        this(name, quantity, description, 0);
    }
    public Item(String name, int quantity, String description, int value) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.value = value;
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
    public int getValue(){
        return this.value;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void addQuantity(int amount){
        this.quantity += amount;
    }
    //shop price, items are worth half when sold
    public int getSellValue(){
        return this.value/2;
    }

    abstract public void displayInfo();
}
//implementing displayInfo onto subclasses

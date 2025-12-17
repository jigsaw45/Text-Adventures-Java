public abstract class Item {
    private String name;
    private int quantity;
    //sets private variables for item class

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        //makes item and adds item template
    }
    public String getName() {
        return name;
        //gets the items name
    }

    public int getQuantity() {
        return quantity;
        //gets items quantity
    }

    abstract public void displayInfo();
}
//implementing displayInfo onto subc
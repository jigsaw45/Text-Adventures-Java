package items;

public class Fruit extends Item {

    private String type;
    //adds new value of type
    public Fruit(String name, int quantity, String type, String description) {
        this(name, quantity, type, description, 0);
    }
    public Fruit(String name, int quantity, String type, String description, int value) {
        super(name, quantity, description, value);
        this.type = type;
        //subclass of items.Item
    }
    public String getType() {
        return type;
    }


    public void displayInfo(){
        System.out.println("Fruit: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + type);
    }
}

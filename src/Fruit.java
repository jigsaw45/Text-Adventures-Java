public class Fruit extends Item {

    private String type;
    //adds new value of type
    public Fruit(String name, int quantity, String type) {
        super(name, quantity);
        this.type = type;
        //subclass of Item
    }
    public String getType() {
        return type;
    }


    public void displayInfo(){
        System.out.println("Fruit: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + type);
    }
    //@overide creates a diffrent variable for each class its in
}


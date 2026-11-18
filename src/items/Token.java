package items;

public class Token extends Item {
    private String type;

    public Token(String name, int quantity, String type, String description, int value) {
        super(name, quantity, description, value);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void displayInfo() {
        System.out.println("Token: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + type);
    }
}

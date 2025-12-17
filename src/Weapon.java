public class Weapon extends Item {
    // extends item makes it an item subclass so it shares private variables
    private int damage;
    private String type;

    public Weapon(String name, int quantity, int damage, String type) {
        super(name, quantity);
        // super imports values
        this.damage = damage;
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }

    public void displayInfo() {
        System.out.println("Weapon: " + getName() + ", Quantity: " + getQuantity() + ", Damage: " + getDamage() + ", Type: " + getType());
    }
}


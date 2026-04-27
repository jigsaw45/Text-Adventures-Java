package items;

public class Pill extends Item{
    private int energyAmount;
    public Pill(String name, int quantity, int energyAmount, String description) {
        super(name, quantity, description);
        this.energyAmount = energyAmount;
    }
    public int getEnergyAmount() {
        return energyAmount;
    }
    public void displayInfo(){
        System.out.println("items.Pill: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + energyAmount);
    }
}

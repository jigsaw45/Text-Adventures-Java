package items;

public class Pill extends Item{
    private int energyAmount;
    private PillType pillType;

    public Pill(String name, int quantity, int energyAmount, String description) {
        this(name, quantity, energyAmount, PillType.CULTIVATION, description, 0);
    }
    public Pill(String name, int quantity, int energyAmount, PillType pillType, String description, int value) {
        super(name, quantity, description, value);
        this.energyAmount = energyAmount;
        this.pillType = pillType;
    }
    public int getEnergyAmount() {
        return energyAmount;
    }
    public PillType getPillType(){
        return pillType;
    }
    public void displayInfo(){
        System.out.println("Pill: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + pillType.getName() + ", Power: " + energyAmount);
    }
}

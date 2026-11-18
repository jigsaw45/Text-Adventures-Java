package NPCs;

import items.Item;

public class ItemQuest extends Quest{
    private int targetAmount;
    private int currentAmount;
    private Item item;


    public ItemQuest(String questName, String questDescription, boolean questComplete, String type, int targetAmount, int currentAmount, Item item) {
        super(questName, questDescription, questComplete, type);
        this.setType("Item Quest");
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.item = item;
    }
    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void increaseCurrentAmount(int Amount) {
        this.currentAmount += Amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

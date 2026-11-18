package NPCs;

public class KillQuest extends Quest{

    private int targetAmount;
    private int currentAmount;

    public KillQuest(String questName, String questDescription, boolean questComplete, String type, int targetAmount, int currentAmount) {
        super(questName, questDescription, questComplete, type);
        this.setType("Kill Quest");
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
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
}

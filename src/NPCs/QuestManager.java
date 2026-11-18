package NPCs;

import items.Item;
import player.Player;

import java.util.ArrayList;

public class QuestManager {
    private ArrayList<Quest> activeQuests = new ArrayList<>();
    private ArrayList<Quest> completedQuests = new ArrayList<>();


    public void updateCollectionStatus(Player player, ItemQuest quest){
        if(player.getInventory().itemAmount(quest.getItem()) >= quest.getTargetAmount()){
            quest.setQuestComplete(true);
        }
    }
    public void updateTalkStatus(Quest quest) {
        quest.setQuestComplete(true);
    }
    public void updateKillStatus(KillQuest quest){
        if(quest.getCurrentAmount() >= quest.getTargetAmount()){
            quest.setQuestComplete(true);
        }
    }
}

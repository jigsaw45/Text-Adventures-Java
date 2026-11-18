package io;

import combat.Stats;
import enemy.Enemy;
import items.Item;
import Npc.Npc;
import player.Player;
import skills.Skill;
import skills.SkillBox;
import skills.SkillLoadout;
import world.Room;

public class TextUI {
    public TextUI(){
    }

    public void print(String text){
        System.out.println(text);
    }
    public void banner(String text){
        System.out.println("\n--- " + text + " ---");
    }

    public void displayPlayerStats(Player player){
        Stats stats = player.getStats();
        System.out.println("Name: " + player.getName() +
                           "\nSect rank: " + player.getSectRank() +
                           "\nAffinity: " + player.getAffinity().getName() +
                           "\nRealm: " + stats.getCurrentRealm().getName() +
                           "\nStage: " + stats.getCurrentStage() +
                           "\nXP: " + stats.getCurrentXP() + "/" + stats.getRequiredXP() +
                           "\nHP: " + stats.getCurrentHP() + "/" + stats.getMaxHP() +
                           "\nStamina: " + stats.getCurrentStamina() + "/" + stats.getMaxStamina() +
                           "\nQi: " + stats.getCurrentQi() + "/" + stats.getMaxQi() +
                           "\nSpirit stones: " + player.getSpiritStones() +
                           "\nAvailable points: " + stats.getPoints() +
                           "\n(1) Vitality: "+ stats.getVitality() +
                           "\n(2) Stamina: "+ stats.getStamina() +
                           "\n(3) Strength: " + stats.getStrength() +
                           "\n(4) Focus: "+ stats.getFocus() +
                           "\n(5) Agility: " + stats.getAgility() +
                           "\n(6) Exit");
    }

    public void pointAmount(int points){
        System.out.println("You currently have " + points + " points" +
                           "\nHow many points do you want to distribute into the stat?");
    }
    public void pointInvested(String stat, int amount){
        System.out.println("You have put "+amount+" points into " + stat);
    }
    public void pointAmountError(){
        System.out.println("Error! You don't have that many points!");
    }

    public void commandLook(Player player){
        Room room = player.getCurrentRoom();
        System.out.println("\n== " + room.getRoomName() + " ==");
        System.out.println(room.getDescription());
        System.out.println("Exits: " + room.getExitText());

        if(!room.getItems().isEmpty()){
            String floor = "";
            for(Item item : room.getItems()){
                floor += item.getName() + ", ";
            }
            System.out.println("On the ground: " + floor.substring(0, floor.length()-2));
        }
        if(!room.getNpcs().isEmpty()){
            String present = "";
            for(Npc npc : room.getNpcs()){
                present += npc.getName() + " (" + npc.getTitle() + "), ";
            }
            System.out.println("Present: " + present.substring(0, present.length()-2));
        }
        if(room.getEnemyName() != null && !room.isCleared()){
            System.out.println("Threat: " + room.getEnemyName() + " is here. Type 'fight' to engage.");
        }
    }

    public void encounterText(Enemy enemy){
        System.out.println("\nYou Have encountered " + enemy.getName() + " (" + enemy.getRole().getName() + ")");
    }
    public void playerHpAmountText(Player player){
        System.out.println("\nyou have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP()+ "HP");
    }
    public void playerStanceOptionText(){
        System.out.println("\nSelect your stance for this turn-\n(1)Aggressive\n(2)Balanced\n(3)Defensive\n(4)Focused");
    }
    public void playerSkillOptionText(Player player){
        Skill[] equipped = player.getSkillLoadout().getEquipped();
        System.out.println("\nSelect your action for this turn-" +
                "\n(1)" + equipped[0].toString() +
                "\n(2)" + equipped[1].toString() +
                "\n(3)" + equipped[2].toString() +
                "\n(4)" + equipped[3].toString() +
                "\n(5)Parry (30 momentum)" +
                "\n(6)Guard (10 stamina, builds momentum)");
    }
    public void displayCombatStatus(Player player, Enemy enemy){
        Stats stats = player.getStats();
        System.out.println("\n" + enemy.getName() + " HP: " + enemy.getStats().getCurrentHP() + "/" + enemy.getStats().getMaxHP() +
                "\n" + player.getName() + " HP: " + stats.getCurrentHP() + "/" + stats.getMaxHP() +
                "\nStamina: " + stats.getCurrentStamina() + "/" + stats.getMaxStamina() +
                "\nQi: " + stats.getCurrentQi() + "/" + stats.getMaxQi() +
                "\nMomentum: " + stats.getMomentum() +
                "\nYour status: " + stats.getStatusText());
    }
    public void fightEnd(Enemy enemy){
        System.out.println("\nYou have defeated " + enemy.getName() + "!!!");
        System.out.println(enemy.getDeathText());
    }
    public void playerDeath(){
        System.out.println("\nYour vision goes black. The mountain keeps its opinion to itself.");
    }
    public void invalidCmd(){
        System.out.println("\ninvalid command!");
    }

    public void playerDamageDealt(Player player, Enemy enemy, int damage, boolean crit){
        System.out.println("\n" + (crit ? "A clean strike! " : "") + "you did "+damage+" damage to " + enemy.getName() +
            "\n"+enemy.getName() + "'s HP: " + enemy.getStats().getCurrentHP() + "/" +enemy.getStats().getMaxHP() +
            "\nStamina: " + player.getStats().getCurrentStamina() + "/"+player.getStats().getMaxStamina() +
            "\nMomentum: " + player.getStats().getMomentum());
    }
    public void enemyDamageDealt(Player player, Enemy enemy, int damage, boolean crit) {
        System.out.println(
                "\n" + (crit ? "That one landed badly. " : "") + enemy.getName() + " did " + damage + " damage" +
                        "\nYou have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP() + " HP\n" +
                        enemy.getName() + "'s momentum: " + enemy.getStats().getMomentum());
    }

    public void skillBookUnlockMenu(SkillBox skillBox){
        System.out.println("--- Techniques you know ---");
        System.out.println("Physical Skills:");
        for(int i = 0; i < skillBox.getSkillIndex().length; i++){
            Skill skill = skillBox.getSkillIndex()[i];
            if(skill.getOwned() && skill.getSkillType()==1){
                System.out.println("[" + i + "] " + skill);
            }
        }
        System.out.println("Elemental Skills:");
        for(int i = 0; i < skillBox.getSkillIndex().length; i++){
            Skill skill = skillBox.getSkillIndex()[i];
            if(skill.getOwned() && skill.getSkillType()==2){
                System.out.println("[" + i + "] " + skill);
            }
        }
    }

    public void displayLoadout(Player player){
        SkillLoadout loadout = player.getSkillLoadout();
        banner("Equipped Techniques");
        for(int i = 0; i < loadout.getEquipped().length; i++){
            System.out.println("(" + (i+1) + ") " + loadout.getEquipped()[i].toString());
        }
    }

    public void questComplete(String questName, int xp, int stones){
        System.out.println("\n[Quest complete] " + questName + " - " + xp + " XP, " + stones + " spirit stones.");
    }

    public void itemGained(Item item){
        System.out.println("Obtained: " + item.getName() + " x" + item.getQuantity());
    }
}

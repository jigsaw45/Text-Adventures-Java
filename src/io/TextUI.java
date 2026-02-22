package io;

import combat.Stats;
import enemy.Enemy;
import player.Player;

public class TextUI {
    public TextUI(){
    }
    public void displayPlayerStats(Player player){
        System.out.println("Name: " + player.getName() +
                           "\nLevel: " + player.getStats().getLevel() +
                           "\nMaxHP: " + player.getStats().getMaxHP() +
                           "\nMaxStamina: "+ player.getStats().getMaxStamina() +
                           "\nAvailable points: " + player.getStats().getPoints() +
                           "\n(1) Vitality: "+ player.getStats().getVitality() +
                           "\n(2) Stamina: "+ player.getStats().getStamina() +
                           "\n(3) Strength: " + player.getStats().getStrength() +
                           "\n(4) Focus: "+ player.getStats().getFocus() +
                           "\n(5) Agility: " + player.getStats().getAgility() +
                           "\n(6) Mastery: "+ player.getStats().getMastery() +
                           "\n(7) Exit");
    }

    public void pointAmount(int points){
        System.out.println("You currently have " + points + " points+" +
                           "\nHow many points do you want to distribute into the stat?");
    }
    public void pointInvested(String stat, int amount){
        System.out.println("You have put "+amount+" points into " + stat);
    }
    public void pointAmountError(){
        System.out.println("Error! You don't have that many points!");
    }
    public void commandLook(Player player){
        System.out.println(player.getCurrentRoom());
    }
    public void encounterText(Enemy enemy){
        System.out.println("\nYou Have encountered " + enemy.getName());
    }
    public void playerHpAmountText(Player player){
        System.out.println("\nyou have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP()+ "HP");
    }
    public void playerStanceOptionText(){
        System.out.println("\nSelect your stance for this turn-\n(1)Aggressive\n(2)Balanced\n(3)Defensive\n(4)Focused");
    }
    public void playerSkillOptionText(){
        System.out.println("\nSelect your skill for this turn-\n(1)Light Punch\n(2)Heavy Punch\n(3)light Kick\n(4)Heavy Kick");
    }
    public void fightEnd(Enemy enemy){
        System.out.println("\nYou have defeated " + enemy.getName() + "!!!");
    }
    public void playerDeath(){
        System.out.println("\nYou have died!");
    }
    public void invalidCmd(){
        System.out.println("\ninvalid command!);");
    }
    public void playerDamageDealt(Player player, Enemy enemy, int damage){System.out.println(
            "\nyou did "+damage+" damage to " + enemy.getName() +
            "\n"+enemy.getName() + "'s HP: " + enemy.getStats().getCurrentHP() + "/" +enemy.getStats().getMaxHP() +
            "\nStamina: " + player.getStats().getCurrentStamina() + "/"+player.getStats().getMaxStamina() +
            "\nMomentum: " + player.getStats().getMomentum());
    }
    public void enemyDamageDealt(Player player, Enemy enemy, int damage) {
        System.out.println(
                "\n" + enemy.getName() + " did " + damage + " damage" +
                        "\nYou have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP() + " HP\n" +
                        enemy.getName() + "'s momentum: " + enemy.getStats().getMomentum());
    }

}


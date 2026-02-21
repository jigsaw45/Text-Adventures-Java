package io;

import combat.Stats;
import enemy.Enemy;
import player.Player;

public class TextUI {
    public TextUI(){
    }
    public void displayPlayerStats(Player player){
        System.out.println("Name: " + player.getName() + "\n" +
                           "Level: " + player.getStats().getLevel() + "\n" +
                           "MaxHP: " + player.getStats().getMaxHP() + "\n" +
                           "Vitality: "+ player.getStats().getVitality() + "\n" +
                           "MaxStamina: "+ player.getStats().getMaxStamina() + "\n" +
                           "Stamina: "+ player.getStats().getStamina() + "\n" +
                           "Strength: " + player.getStats().getStrength() + "\n" +
                           "Focus: "+ player.getStats().getFocus() + "\n" +
                           "Agility: " + player.getStats().getAgility() + "\n" +
                           "Mastery: "+ player.getStats().getMastery() + "\n");
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
        System.out.println("\nYou have defeated "+enemy.getName()+"!!!");
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
            "\nStamina: "+player.getStats().getCurrentStamina()+
            "\nMomentum: "+player.getStats().getMomentum());
    }
    public void enemyDamageDealt(Player player, Enemy enemy, int damage) {
        System.out.println(
                "\n" + enemy.getName() + " did " + damage + " damage" +
                        "\nYou have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP() + " HP\n" +
                        enemy.getName() + "'s momentum: " + enemy.getStats().getMomentum());
    }

}


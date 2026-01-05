package io;

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
        System.out.println("You Have encountered " + enemy.getName());
    }
    public void hpAmountText(Player player){
        System.out.println("you have " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP()+ "HP");
    }
    public void stanceOptionText(){
        System.out.println("Select your stance for this turn-\n(1)Aggressive\n(2)Balanced\n(3)Defensive\n(4)Focused");
    }
    public void skillOptionText(){
        System.out.println("Select your skill for this turn-\n(1)Light Punch\n(2)Heavy Punch\n(3)light Kick\n(4)Heavy Kick");
    }
    public void fightEnd(Enemy enemy){
        System.out.println("You have defeated "+enemy.getName()+"!!!");
    }
}

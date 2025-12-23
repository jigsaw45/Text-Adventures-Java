package io;

import player.Player;
import java.util.Scanner;
public class TextOutput {
    public TextOutput(){
    }
    public void displayPlayerStats(Player player){
        System.out.println("MaxHP: " + player.getStats().getMaxHP() + "\n" +
                           "Vitality: "+ player.getStats().getVitality() + "\n" +
                           "MaxStamina: "+ player.getStats().getMaxStamina() + "\n" +
                           "Stamina: "+ player.getStats().getStamina() + "\n" +
                           "Strength: " + player.getStats().getStrength() + "\n" +
                           "Focus: "+ player.getStats().getFocus() + "\n" +
                           "Agility: " + player.getStats().getAgility() + "\n" +
                           "Mastery: "+ player.getStats().getMastery() + "\n");
    }
}

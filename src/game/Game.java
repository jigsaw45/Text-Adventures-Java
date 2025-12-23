package game;

import enemy.Enemy;
import io.Command;
import io.CommandParser;
import io.TextUI;
import player.Player;
import world.Room;

import java.util.Scanner;

public class Game {
    private Player player;
    private Enemy enemy1;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);
    private TextUI textUI;

    public Game(){
        setupworld();
    }

    private void setupworld(){
        textUI = new TextUI();
        world.Room r1 = new Room("room 1", "this is room 1");
        world.Room r2 = new Room("room 2", "this is room 2");
        world.Room r3 = new Room("room 3", "this is room 3");
        player = new Player("player", r1);
        //enemy1 is Gorlock
        enemy1 = new Enemy("Gorlock",10,10,10,10,10,10,10);
    }

    public void start() {
        while(running){
            String input = scanner.nextLine();
            Command cmd = CommandParser.parse(input);
            handleCommand(cmd);
        }
    }
    public void handleCommand(Command cmd){
        // Basic dispatcher: look, go, take, drop, inventory, help, quit
        switch(cmd.getVerb()){
            case "look":
                textUI.commandLook(player);
                break;
            case "go":
                break;
            case "move":
                if(cmd.hasNoun()){
                    Room next = player.getCurrentRoom();
                    if(next != null){
                        player.setCurrentRoom(next);
                        System.out.println(player.getCurrentRoom());
                    }else{
                        System.out.println("You cant go that way!");
                    }
                }else{
                    System.out.println("Go where?");
                }
                break;
            case "quit":
                running = false;
                System.out.println("Goodbye.");
                break;
            case "stats":
                textUI.displayPlayerStats(player);
                break;
            default:
                System.out.println("invalid command!");
        }
    }
}

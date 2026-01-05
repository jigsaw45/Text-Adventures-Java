package game;

import combat.CombatEngine;
import combat.Stats;
import enemy.Enemy;
import io.Command;
import io.CommandParser;
import io.TextUI;
import player.Player;
import skills.Skill;
import world.Room;
import java.util.Scanner;

public class Game {
    private Player player;
    private Enemy enemy1;
    private Skill lightPunch;
    private Skill heavyPunch;
    private Skill lightKick;
    private Skill heavyKick;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);
    private CombatEngine combatEngine;
    private TextUI textUI;

    public Game(){
        setupworld();
    }

    private void setupworld(){
        //misc setups
        textUI = new TextUI();
        combatEngine = new CombatEngine();
        //rooms
        world.Room r1 = new Room("room 1", "this is room 1");
        world.Room r2 = new Room("room 2", "this is room 2");
        world.Room r3 = new Room("room 3", "this is room 3");
        //player
        player = new Player("player", r1);
        //enemy
        enemy1 = new Enemy("Gorlock",10,10,10,10,10,10,10);
        //skills
        lightPunch = new Skill("Light Punch", 10,10);
        heavyPunch = new Skill("Heavy Punch", 20,30);
        lightKick = new Skill("Light Kick", 15, 20);
        heavyKick = new Skill("Heavy Kick", 30, 40);
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
                combatTurn(player.getStats(),enemy1.getStats());
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

    //Combat Methods
    public Skill skillChoice(Player player){
        while(true) {
            textUI.skillOptionText();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    return lightPunch;
                case "2":
                    return heavyPunch;
                case "3":
                    return lightKick;
                case "4":
                    return heavyKick;
                default:
                    System.out.println("invalid command!");
                    break;
            }
        }
    }
    public void combatTurn(Stats playerStats, Stats enemyStats){
        while(true){
            Skill skill = skillChoice(player);
            if(playerStats.canAffordStamina(skill.getStaminaCost())) {
                int damage = combatEngine.calculateDamage(playerStats, skill, enemyStats);
                combatEngine.applyDamage(enemyStats, damage);
                playerStats.spendStamina(skill.getStaminaCost());
                playerStats.changeMomentum(5);
                enemyStats.changeMomentum(-10);
                System.out.println("you did "+damage+" damage\nEnemy has "+enemyStats.getCurrentHP()+"/"+enemyStats.getMaxHP()+ " HP\nStamina: "+playerStats.getCurrentStamina()+"\nMomentum: "+playerStats.getMomentum());

                if(combatEngine.isDead(enemyStats)){System.out.print("they have died");}
            }
            else{
                System.out.println("cannot afford this skill") ;
            }
        }
    }
    public void combatTurnAI(Stats playerStats, Stats enemyStats){
        while(true){
            Skill skill = skillChoice(player);
            if(playerStats.canAffordStamina(skill.getStaminaCost())) {
                int damage = combatEngine.calculateDamage(playerStats, skill, enemyStats);
                combatEngine.applyDamage(enemyStats, damage);
                playerStats.spendStamina(skill.getStaminaCost());
                playerStats.changeMomentum(5);
                enemyStats.changeMomentum(-10);

                if(combatEngine.isDead(enemyStats)){System.out.print("they have died");}
            }
            else{
                System.out.println("cannot afford this skill") ;
            }
        }
    }
}

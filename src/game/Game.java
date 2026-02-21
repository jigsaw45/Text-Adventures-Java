package game;

import combat.CombatEngine;
import combat.Stats;
import enemy.Enemy;
import enemy.EnemyAI;
import io.Command;
import io.CommandParser;
import io.TextUI;
import player.Player;
import skills.Skill;
import skills.SkillBox;
import skills.SkillLoadout;
import world.Room;

import java.util.Arrays;
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
    private SkillBox skillBox;
    private SkillLoadout playerSkillLoadout;
    private SkillLoadout gorlockSkillLoadout;
    private EnemyAI enemyAI;

    public Game(){
        setupworld();
    }

    //Setup and creation
    private void setupworld(){
        //misc setups
        textUI = new TextUI();
        combatEngine = new CombatEngine();
        //rooms
        world.Room r1 = new Room("room 1", "this is room 1");
        world.Room r2 = new Room("room 2", "this is room 2");
        world.Room r3 = new Room("room 3", "this is room 3");
        //skill loadouts
        skillBox = new SkillBox();
        playerSkillLoadout = new SkillLoadout(
                skillBox.getSkillIndex()[0],
                skillBox.getSkillIndex()[1],
                skillBox.getSkillIndex()[2],
                skillBox.getSkillIndex()[3]);
        gorlockSkillLoadout = new SkillLoadout(
                skillBox.getSkillIndex()[4],
                skillBox.getSkillIndex()[5],
                skillBox.getSkillIndex()[6],
                skillBox.getSkillIndex()[7]);
        //player
        player = new Player("player", r1);
        //enemy
        enemyAI = new EnemyAI();
        enemy1 = new Enemy("Gorlock",10,10,10,10,10,10,10, gorlockSkillLoadout);

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
                combatTurn(player, enemy1);
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
                textUI.invalidCmd();
        }
    }

    //Misc methods
    public void sleep(double seconds){
        try{
            Thread.sleep((long) (seconds*1000));
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    //Combat Methods

    //menu for players skill selection
    public Skill playerSkillChoice(SkillLoadout playerSkillLoadout){
        while(true) {
            textUI.playerSkillOptionText();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    return playerSkillLoadout.getEquipped()[0];
                case "2":
                    return playerSkillLoadout.getEquipped()[1];
                case "3":
                    return playerSkillLoadout.getEquipped()[2];
                case "4":
                    return playerSkillLoadout.getEquipped()[3];
                default:
                    textUI.invalidCmd();
                    break;
            }
        }
    }

    //player half of turn
    public void playerCombatTurn(Player player, Enemy enemy){
        Skill skill = playerSkillChoice(playerSkillLoadout);
        if(player.getStats().canAffordStamina(skill.getStaminaCost())) {
            int damage = combatEngine.calculateDamage(player.getStats(), skill, enemy.getStats());
            combatEngine.applyDamage(enemy.getStats(), damage);
            player.getStats().spendStamina(skill.getStaminaCost());
            player.getStats().changeMomentum(5);
            enemy.getStats().changeMomentum(-10);
            textUI.playerDamageDealt(player, enemy, damage);
        }
        else{
            System.out.println("cannot afford this skill") ;
        }
    }

    //bot half of turn
    public void botCombatTurn(Player player, Enemy enemy){
        Skill skill = enemyAI.botSkillChoice(enemy);
        int damage = combatEngine.calculateDamage(enemy.getStats(), skill, player.getStats());
        combatEngine.applyDamage(player.getStats(), damage);
        enemy.getStats().changeMomentum(5);
        player.getStats().changeMomentum(-10);
        textUI.enemyDamageDealt(player, enemy, damage);
    }

    //full turn
    public void combatTurn(Player player, Enemy enemy){
        if(player.getStats().getAgility()>enemy.getStats().getAgility()){
            while(!(combatEngine.isDead(player.getStats()) || combatEngine.isDead(enemy.getStats()))){
                playerCombatTurn(player, enemy);
                if(combatEngine.isDead(enemy.getStats())){
                    textUI.fightEnd(enemy);
                    break;
                }

                //wait 3 seconds
                sleep(1);

                botCombatTurn(player, enemy);

                //wait 3 seconds
                sleep(1);
            }
        }
        else{
            while(!(combatEngine.isDead(player.getStats()) || combatEngine.isDead(enemy.getStats()))){
                botCombatTurn(player, enemy);
                if(combatEngine.isDead(player.getStats())){
                    textUI.playerDeath();
                    break;
                }

                //wait 3 seconds
                sleep(1);

                playerCombatTurn(player, enemy);

                //wait 3 seconds
                sleep(1);
            }
        }




    }
}

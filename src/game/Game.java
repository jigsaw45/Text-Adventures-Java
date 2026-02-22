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

    //Setup and creation -----------------------------------------------------------------------------------------------
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
            case "stats":
                statMenu();
                break;
            case "help":
                System.out.println("Look, go, move, stats, help, quit");
            case "quit":
                running = false;
                System.out.println("Goodbye.");
                break;
            default:
                textUI.invalidCmd();
        }
    }

    //Misc methods -----------------------------------------------------------------------------------------------------
    public void sleep(double seconds){
        try{
            Thread.sleep((long) (seconds*1000));
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    //Menu methods -----------------------------------------------------------------------------------------------------

    //Stat investment Methods
    public boolean investInStat(int statChoice){
        int amount = Integer.MAX_VALUE;
        int playerPoints = player.getStats().getPoints();
        String stat = "";
        scanner.nextLine();
        textUI.pointAmount(playerPoints);
        amount = scanner.nextInt();
        scanner.nextLine();
        if(amount<=playerPoints){
            switch(statChoice){
                case 1:
                    stat="Vitality";
                    player.getStats().increaseVitality(amount);
                    break;
                case 2:
                    stat="Stamina";
                    player.getStats().increaseStamina(amount);
                    break;
                case 3:
                    stat="Strength";
                    player.getStats().increaseStrength(amount);
                    break;
                case 4:
                    stat="Focus";
                    player.getStats().increaseFocus(amount);
                    break;
                case 5:
                    stat="Agility";
                    player.getStats().increaseAgility(amount);
                    break;
                case 6:
                    stat = "Mastery";
                    player.getStats().increaseMastery(amount);
                    break;
            }
            textUI.pointInvested(stat, amount);
            player.getStats().increasePoints(-amount);
            return false;
        }else{
            textUI.pointAmountError();
            return true;
        }
    }

    public void statMenu() {
        boolean menuRun = true;
        while(menuRun) {
            textUI.displayPlayerStats(player);

            switch (scanner.nextInt()) {
                case 1:
                    menuRun = investInStat(1);
                    break;
                case 2:
                    menuRun = investInStat(2);
                    break;
                case 3:
                    menuRun = investInStat(3);
                    break;
                case 4:
                    menuRun = investInStat(4);
                    break;
                case 5:
                    menuRun = investInStat(5);
                    break;
                case 6:
                    menuRun = investInStat(6);
                    break;
                case 7:
                    menuRun = false;
                    break;
                default:
                    textUI.invalidCmd();
                    break;

            }
        }
    }


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
    //Combat Methods ---------------------------------------------------------------------------------------------------

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

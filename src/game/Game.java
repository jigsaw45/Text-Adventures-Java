package game;

import combat.CombatEngine;
import combat.Stats;
import enemy.Enemy;
import io.Command;
import io.CommandParser;
import io.TextUI;
import player.Player;
import skills.Skill;
import skills.SkillBox;
import skills.SkillLoadout;
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
    private SkillBox skillBox;
    private SkillLoadout playerSkillLoadout;

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
        //Skills
        skillBox = new SkillBox();
        playerSkillLoadout = new SkillLoadout(
                skillBox.getOwnedSkills()[0],
                skillBox.getOwnedSkills()[1],
                skillBox.getOwnedSkills()[2],
                skillBox.getOwnedSkills()[3]);
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
                combatTurn(player.getStats(), enemy1.getStats());
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

    //Combat Methods
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
    public void playerCombatTurn(Stats attackerStats, Stats defenderStats){
        Skill skill = playerSkillChoice(playerSkillLoadout);
        if(attackerStats.canAffordStamina(skill.getStaminaCost())) {
            int damage = combatEngine.calculateDamage(attackerStats, skill, defenderStats);
            combatEngine.applyDamage(defenderStats, damage);
            attackerStats.spendStamina(skill.getStaminaCost());
            attackerStats.changeMomentum(5);
            defenderStats.changeMomentum(-10);
            textUI.playerDamageDealt(attackerStats, defenderStats, damage);
        }
        else{
            System.out.println("cannot afford this skill") ;
        }
    }
    public void botCombatTurn(Stats player, Stats enemy){
        Skill skill = botSkillChoice();
        //botSkillChoice non-existent right now
        int damage = combatEngine.calculateDamage(enemy, skill, player);
        combatEngine.applyDamage(player, damage);
        player.changeMomentum(5);
        enemy.changeMomentum(-10);
    }

    public void combatTurn(Stats player, Stats enemy){
        if(player.getAgility()>enemy.getAgility()){
            while(!(combatEngine.isDead(player) || combatEngine.isDead(enemy))){
                playerCombatTurn(player, enemy);
                if(combatEngine.isDead(enemy)){
                    break;
                }
                botCombatTurn(player, enemy);
            }
        }
        else{
            while(!(combatEngine.isDead(player) || combatEngine.isDead(enemy))){
                botCombatTurn(player, enemy);
                if(combatEngine.isDead(player)){
                    break;
                }
                playerCombatTurn(player, enemy);
            }
        }




    }
}

package game;

import combat.CombatEngine;
import combat.Element;
import combat.Stance;
import combat.Stats;
import combat.StatusType;
import enemy.Enemy;
import enemy.EnemyAI;
import io.TextUI;
import player.Player;
import skills.Skill;

import java.util.Scanner;

public class CombatController {
    private CombatEngine combatEngine;
    private EnemyAI enemyAI;
    private TextUI textUI;
    private Scanner scanner;

    private Stance playerStance;
    private Stance enemyStance;
    private boolean parrying;
    private boolean guarding;

    public CombatController(CombatEngine combatEngine, EnemyAI enemyAI, TextUI textUI, Scanner scanner){
        this.combatEngine = combatEngine;
        this.enemyAI = enemyAI;
        this.textUI = textUI;
        this.scanner = scanner;
    }

    //speed meter fight: agility fills the gauge, whoever hits 100 acts
    public boolean fight(Player player, Enemy enemy){
        textUI.encounterText(enemy);

        int playerGauge = 0;
        int enemyGauge = 0;
        playerStance = Stance.BALANCED;
        enemyStance = enemyAI.botStanceChoice(enemy);
        parrying = false;
        guarding = false;
        player.getStats().clearStatuses();
        enemy.getStats().clearStatuses();

        while(true){
            playerGauge += playerSpeed(player);
            enemyGauge += enemySpeed(enemy);

            if(playerGauge >= 100){
                playerGauge -= 100;
                playerTurn(player, enemy);
            }
            if(!combatEngine.isDead(enemy.getStats()) && enemyGauge >= 100){
                enemyGauge -= 100;
                enemyTurn(player, enemy);
            }

            if(combatEngine.isDead(enemy.getStats())){
                textUI.fightEnd(enemy);
                rewardPlayer(player, enemy);
                healUp(player);
                return true;
            }
            if(combatEngine.isDead(player.getStats())){
                textUI.playerDeath();
                return false;
            }
        }
    }

    private int playerSpeed(Player player){
        return 12 + (player.getStats().getAgility()/2);
    }
    private int enemySpeed(Enemy enemy){
        return 8 + (enemy.getStats().getAgility()/3);
    }

    //player half of turn ---------------------------------------------------------------------------------------------
    private void playerTurn(Player player, Enemy enemy){
        Stats stats = player.getStats();

        if(stats.consumeStun()){
            textUI.print("You are stunned and lose the turn. This is not your finest moment.");
            return;
        }

        int statusDamage = stats.tickStatuses();
        if(statusDamage > 0){
            combatEngine.applyDamage(stats, statusDamage);
            textUI.print("Status effects tear at you for " + statusDamage + " damage.");
        }
        if(combatEngine.isDead(stats)){
            return;
        }

        textUI.displayCombatStatus(player, enemy);
        textUI.playerStanceOptionText();
        playerStance = readStance();

        textUI.playerSkillOptionText(player);
        int choice = readInt();
        switch(choice){
            case 5:
                tryParry(player);
                break;
            case 6:
                guard(player);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                useSkill(player, enemy, player.getSkillLoadout().getEquipped()[choice-1]);
                break;
            default:
                textUI.invalidCmd();
                break;
        }
    }

    private void useSkill(Player player, Enemy enemy, Skill skill){
        Stats stats = player.getStats();

        if(!stats.canAffordStamina(skill.getStaminaCost())
                || !stats.canAffordQi(skill.getQiCost())
                || stats.getCurrentHP() <= skill.getHpCost()){
            textUI.print("You cannot pay for " + skill.getName() + ".");
            return;
        }

        stats.spendStamina(skill.getStaminaCost());
        stats.spendQi(skill.getQiCost());
        stats.changeCurrentHP(-skill.getHpCost());
        stats.changeMomentum((int)(8 * playerStance.getMomentumMultiplier()));

        if(combatEngine.isDodged(enemy.getStats())){
            textUI.print(enemy.getName() + " slips aside. Your technique finds only air.");
            return;
        }

        boolean crit = combatEngine.isCritical(stats);
        int damage = combatEngine.calculateDamage(stats, skill, enemy.getStats(), playerStance, enemyStance,
                player.getWeaponDamage() + player.getDamageBuff());
        if(crit){
            damage = combatEngine.applyCrit(damage);
        }
        combatEngine.applyDamage(enemy.getStats(), damage);
        textUI.playerDamageDealt(player, enemy, damage, crit);
        applyElementStatus(skill.getElement() == Element.NONE ? stats.getElement() : skill.getElement(), enemy.getStats(), crit);
    }

    private void tryParry(Player player){
        if(player.getStats().spendMomentum(30)){
            parrying = true;
            textUI.print("You read the rhythm and wait to turn their next blow.");
        }else{
            textUI.print("Not enough momentum. You need 30, you have " + player.getStats().getMomentum() + ".");
        }
    }

    private void guard(Player player){
        if(!player.getStats().canAffordStamina(10)){
            textUI.print("Not enough stamina to guard.");
            return;
        }
        player.getStats().spendStamina(10);
        player.getStats().changeMomentum((int)(20 * playerStance.getMomentumMultiplier()));
        guarding = true;
        textUI.print("You set your feet and breathe. Momentum " + player.getStats().getMomentum() + ".");
    }

    //enemy half of turn ----------------------------------------------------------------------------------------------
    private void enemyTurn(Player player, Enemy enemy){
        Stats stats = enemy.getStats();

        if(stats.consumeStun()){
            textUI.print(enemy.getName() + " is stunned and loses the turn.");
            return;
        }

        int statusDamage = stats.tickStatuses();
        if(statusDamage > 0){
            combatEngine.applyDamage(stats, statusDamage);
            textUI.print(enemy.getName() + " takes " + statusDamage + " damage from its wounds.");
        }
        if(combatEngine.isDead(stats)){
            return;
        }

        enemyStance = enemyAI.botStanceChoice(enemy);
        Skill skill = enemyAI.botSkillChoice(enemy);
        stats.spendStamina(skill.getStaminaCost());
        stats.spendQi(skill.getQiCost());
        stats.changeCurrentHP(-skill.getHpCost());
        stats.changeMomentum((int)(5 * enemyStance.getMomentumMultiplier()));

        if(combatEngine.isDodged(player.getStats())){
            textUI.print("You slip aside. " + enemy.getName() + " hits nothing but your reputation.");
            return;
        }

        boolean crit = combatEngine.isCritical(stats);
        int damage = combatEngine.calculateDamage(stats, skill, player.getStats(), enemyStance, playerStance, 0);
        if(crit){
            damage = combatEngine.applyCrit(damage);
        }

        if(parrying){
            parrying = false;
            textUI.print("You turn the blow aside and answer it. " + enemy.getName() + " takes " + (damage/2) + " damage.");
            combatEngine.applyDamage(enemy.getStats(), damage/2);
            return;
        }

        if(guarding){
            guarding = false;
            damage = Math.max(1, damage/2);
            textUI.print("You take the blow on your guard.");
        }

        combatEngine.applyDamage(player.getStats(), damage);
        textUI.enemyDamageDealt(player, enemy, damage, crit);
        applyElementStatus(skill.getElement() == Element.NONE ? stats.getElement() : skill.getElement(), player.getStats(), crit);
    }

    //status riders from elements and crits ----------------------------------------------------------------------------
    private void applyElementStatus(Element element, Stats target, boolean crit){
        int power = 4 + target.getTotalStages();
        if(element == Element.FIRE && Math.random() < 0.30){
            target.addStatus(StatusType.BURN, 3, power);
            textUI.print("The wound keeps burning.");
        }else if(element == Element.EARTH && Math.random() < 0.25){
            target.addStatus(StatusType.BLEED, 3, power);
            textUI.print("Stone shards leave a bleeding gash.");
        }else if(element == Element.LIGHTNING && Math.random() < 0.20){
            target.addStatus(StatusType.STUN, 1, 0);
            textUI.print("Lightning locks their limbs. They will lose a turn.");
        }
        if(crit && Math.random() < 0.5){
            target.addStatus(StatusType.BLEED, 2, power);
        }
    }

    private void rewardPlayer(Player player, Enemy enemy){
        player.getStats().increaseXp(enemy.getXpReward());
        player.changeSpiritStones(enemy.getStoneReward());
        player.updateSectRank();
        textUI.print("+" + enemy.getXpReward() + " XP and +" + enemy.getStoneReward() + " spirit stones.");
    }

    //auto heal after combat so every fight starts fresh
    private void healUp(Player player){
        player.getStats().recoverHP();
        player.getStats().recoverStamina();
        player.getStats().recoverQi();
        player.getStats().clearStatuses();
        player.getStats().setMomentum(0);
        player.consumeDamageBuff();
        textUI.print("You bind your wounds. HP, stamina and Qi restored.");
    }

    //input helpers
    private Stance readStance(){
        int choice = readInt();
        switch(choice){
            case 1: return Stance.AGGRESSIVE;
            case 3: return Stance.DEFENSIVE;
            case 4: return Stance.FOCUSED;
            default: return Stance.BALANCED;
        }
    }

    private int readInt(){
        String input = scanner.nextLine();
        try{
            return Integer.parseInt(input.trim());
        }catch(NumberFormatException ex){
            return -1;
        }
    }
}

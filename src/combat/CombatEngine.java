package combat;

import skills.Skill;

public class CombatEngine {
    private int damage;
    public CombatEngine(){
    }

    public int calculateDamage(Stats attacker, Skill attackerSkill, Stats defender){
        return ((int) ((attackerSkill.getBasePower()+ (double) attacker.getMomentum() /10) * ((double) attacker.getStrength() / (attacker.getStrength() + defender.getDefence()))));
    }
    public void applyDamage(Stats defender, int damage){
        defender.changeCurrentHP(-damage);
    }
    public boolean isDead(Stats target){
        return target.getCurrentHP() <= 0;
    }
}

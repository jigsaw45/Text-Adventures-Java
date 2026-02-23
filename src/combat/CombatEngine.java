package combat;

import skills.Skill;

public class CombatEngine {
    public CombatEngine(){
    }

    public int calculateDamage(Stats attacker, Skill attackerSkill, Stats defender){
        if(attackerSkill.getSkillType() == 1){
            return ((int) ((attackerSkill.getBasePower()+ (double) attacker.getMomentum() /10) * ((double) attacker.getStrength() / (attacker.getStrength() + defender.getDefence()))));
        }else{
            return ((int) ((attackerSkill.getBasePower()+ (double) attacker.getMomentum() /10) * ((double) attacker.getFocus() / (attacker.getFocus() + defender.getDefence()))));
        }

    }
    public void applyDamage(Stats defender, int damage){
        defender.changeCurrentHP(-damage);
        if(defender.getCurrentHP()<0){defender.setCurrentHP(0);
        }
    }
    public boolean isDead(Stats target){
        return target.getCurrentHP() <= 0;
    }
}

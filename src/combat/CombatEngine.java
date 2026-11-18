package combat;

import skills.Skill;

public class CombatEngine {
    public CombatEngine(){
    }

    //hybrid damage: physical scales strength, elemental scales focus, defence cuts a percentage then chips flat
    public int calculateDamage(Stats attacker, Skill attackerSkill, Stats defender, Stance attackerStance, Stance defenderStance, int bonusDamage){
        boolean elemental = attackerSkill.getSkillType() == 2;
        double scaling;
        if(elemental){
            scaling = (attacker.getFocus()*1.5) + (attacker.getAgility()*0.2);
        }else{
            scaling = (attacker.getStrength()*1.2) + (attacker.getAgility()*0.3);
        }

        double raw = (attackerSkill.getBasePower() + scaling + bonusDamage)
                   * attackerStance.getDamageMultiplier(elemental);

        double defence = defender.getDefence() * defenderStance.getDefenceMultiplier();
        double damage = raw * (100.0/(100.0+defence));
        damage -= defence * 0.15;

        Element attackElement = attackerSkill.getElement();
        if(attackElement == Element.NONE){
            attackElement = attacker.getElement();
        }
        damage *= attackElement.multiplierAgainst(defender.getElement());

        if(damage < 1){
            damage = 1;
        }
        return (int) damage;
    }

    public boolean isDodged(Stats defender){
        return (int)(Math.random()*100) < defender.getDodgeChance();
    }

    public boolean isCritical(Stats attacker){
        return (int)(Math.random()*100) < attacker.getCritChance();
    }

    public int applyCrit(int damage){
        return (int)(damage * 1.5);
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

package enemy;

import combat.Element;
import combat.Stance;

public enum EnemyRole {
    BERSERKER("Berserker", Stance.AGGRESSIVE),
    DUELIST("Duelist", Stance.BALANCED),
    CASTER("Caster", Stance.FOCUSED),
    TANK("Tank", Stance.DEFENSIVE);

    private final String name;
    private final Stance preferredStance;

    EnemyRole(String name, Stance preferredStance){
        this.name = name;
        this.preferredStance = preferredStance;
    }

    public String getName(){
        return this.name;
    }

    public Stance getPreferredStance(){
        return this.preferredStance;
    }
}

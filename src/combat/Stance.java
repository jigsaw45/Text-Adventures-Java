package combat;

public enum Stance {
    AGGRESSIVE("Aggressive", 1.25, 1.15, 0.85, 2.0),
    BALANCED("Balanced", 1.0, 1.0, 1.0, 1.0),
    DEFENSIVE("Defensive", 0.8, 0.8, 1.4, 3.0),
    FOCUSED("Focused", 0.9, 1.3, 1.0, 1.5);

    private final String name;
    private final double physicalMultiplier;
    private final double elementalMultiplier;
    private final double defenceMultiplier;
    private final double momentumMultiplier;

    Stance(String name, double physicalMultiplier, double elementalMultiplier, double defenceMultiplier, double momentumMultiplier){
        this.name = name;
        this.physicalMultiplier = physicalMultiplier;
        this.elementalMultiplier = elementalMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        this.momentumMultiplier = momentumMultiplier;
    }

    public String getName(){
        return this.name;
    }

    //physical skills and elemental skills scale differently by stance
    public double getDamageMultiplier(boolean elemental){
        return elemental ? this.elementalMultiplier : this.physicalMultiplier;
    }

    public double getDefenceMultiplier(){
        return this.defenceMultiplier;
    }

    public double getMomentumMultiplier(){
        return this.momentumMultiplier;
    }
}

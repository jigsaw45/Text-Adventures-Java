package combat;

public class StatusEffect {
    private StatusType type;
    private int turnsLeft;
    private int damagePerTurn;

    public StatusEffect(StatusType type, int turnsLeft, int damagePerTurn){
        this.type = type;
        this.turnsLeft = turnsLeft;
        this.damagePerTurn = damagePerTurn;
    }

    public StatusType getType(){
        return this.type;
    }

    public int getTurnsLeft(){
        return this.turnsLeft;
    }

    public int getDamagePerTurn(){
        return this.damagePerTurn;
    }

    public boolean isStun(){
        return this.type.isControl();
    }

    //ticks one turn and hands back the damage this turn should take
    public int tick(){
        this.turnsLeft--;
        return this.damagePerTurn;
    }

    public boolean isExpired(){
        return this.turnsLeft <= 0;
    }

    @Override
    public String toString(){
        return this.type.getName() + "(" + this.turnsLeft + ")";
    }
}

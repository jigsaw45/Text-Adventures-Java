package combat;

public enum StatusType {
    BURN("burn"),
    BLEED("bleed"),
    POISON("poison"),
    STUN("stunned");

    private final String name;

    StatusType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public boolean isControl(){
        return this == STUN;
    }
}

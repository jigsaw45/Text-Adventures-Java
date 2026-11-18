package combat;

public enum Element {
    NONE("none"),
    FIRE("fire"),
    EARTH("earth"),
    LIGHTNING("lightning");

    private final String name;

    Element(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    //fire melts earth, earth grounds lightning, lightning ignites fire
    public Element beats(){
        switch(this){
            case FIRE: return EARTH;
            case EARTH: return LIGHTNING;
            case LIGHTNING: return FIRE;
            default: return NONE;
        }
    }

    //1.25x when this element beats the defender, 0.8x when it is beaten
    public double multiplierAgainst(Element defender){
        if(this == NONE || defender == NONE){
            return 1.0;
        }
        if(this.beats() == defender){
            return 1.25;
        }
        if(defender.beats() == this){
            return 0.8;
        }
        return 1.0;
    }
}

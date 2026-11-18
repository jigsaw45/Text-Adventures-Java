package items;

public enum PillType {
    CULTIVATION("cultivation"),
    HEALING("healing"),
    COMBAT("combat"),
    BREAKTHROUGH("breakthrough");

    private final String name;

    PillType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

package player;

public class Realm {
    private String name;
    private int stageCount;

    Realm(String name, int stageCount){
        this.name = name;
        this.stageCount = stageCount;
    }

    public String getName() {
        return name;
    }

    public int getStageCount() {
        return stageCount;
    }
}

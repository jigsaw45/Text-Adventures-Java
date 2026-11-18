package player;

import combat.Element;
import combat.Stats;
import items.ItemFactory;
import items.Weapon;
import skills.SkillLoadout;
import world.Room;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private world.Room currentRoom;
    private Stats playerStats;
    private Inventory inventory;
    private SkillLoadout skillLoadout;
    private Element affinity;
    private int spiritStones;
    private Weapon equippedWeapon;
    private int damageBuff;
    private int days;
    private String sectRank;
    private Map<String,Integer> reputation;

    public Player(String name, Room startingRoom, SkillLoadout skillLoadout){
        this.name = name;
        this.currentRoom = startingRoom;
        this.playerStats = new Stats(10,10,10,10,10,RealmSystem.getREALMS()[0],1);
        this.skillLoadout = skillLoadout;
        this.inventory = new Inventory();
        this.affinity = Element.NONE;
        this.spiritStones = 0;
        this.equippedWeapon = null;
        this.damageBuff = 0;
        this.days = 0;
        this.sectRank = "Outer Disciple";
        this.reputation = new HashMap<>();
        this.reputation.put("Mount Hua Sect", 0);
        this.reputation.put("Tang Clan", 0);
        this.reputation.put("Black Lotus Society", 0);
    }
    public world.Room getCurrentRoom(){
        return currentRoom;
    }
    public void setCurrentRoom(world.Room room){
        this.currentRoom = room;
    }
    public Stats getStats(){
        return this.playerStats;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public SkillLoadout getSkillLoadout(){
        return skillLoadout;
    }
    public Inventory getInventory(){
        return inventory;
    }

    //affinity lives on the player and is mirrored into stats for damage maths
    public Element getAffinity(){
        return affinity;
    }
    public void setAffinity(Element affinity){
        this.affinity = affinity;
        this.playerStats.setElement(affinity);
    }

    //spirit stones
    public int getSpiritStones(){
        return spiritStones;
    }
    public void changeSpiritStones(int amount){
        this.spiritStones += amount;
        if(this.spiritStones < 0){
            this.spiritStones = 0;
        }
    }
    public boolean spendSpiritStones(int amount){
        if(this.spiritStones >= amount){
            this.spiritStones -= amount;
            return true;
        }
        return false;
    }

    //weapons
    public Weapon getEquippedWeapon(){
        return equippedWeapon;
    }
    public void setEquippedWeapon(Weapon weapon){
        this.equippedWeapon = weapon;
    }
    public int getWeaponDamage(){
        return equippedWeapon == null ? 0 : equippedWeapon.getDamage();
    }

    //one fight damage buff from combat pills
    public int getDamageBuff(){
        return damageBuff;
    }
    public void addDamageBuff(int amount){
        this.damageBuff += amount;
    }
    public int consumeDamageBuff(){
        int buff = this.damageBuff;
        this.damageBuff = 0;
        return buff;
    }

    //flavour only, days still tick when cultivating
    public int getDays(){
        return days;
    }
    public void addDays(int amount){
        this.days += amount;
    }

    //reputation and sect standing
    public int getReputation(String faction){
        Integer value = reputation.get(faction);
        return value == null ? 0 : value;
    }
    public void changeReputation(String faction, int amount){
        reputation.put(faction, getReputation(faction) + amount);
        updateSectRank();
    }
    public Map<String,Integer> getReputationMap(){
        return reputation;
    }
    public String getSectRank(){
        return sectRank;
    }
    public void updateSectRank(){
        int realm = getStats().getRealmIndex();
        int stage = getStats().getCurrentStage();
        if(realm >= 3){
            this.sectRank = "Core Disciple";
        }else if(realm == 2){
            this.sectRank = "Inner Disciple";
        }else if(realm == 1){
            this.sectRank = "First-Class Disciple";
        }else if(stage >= 7){
            this.sectRank = "Second-Class Disciple";
        }else if(stage >= 4){
            this.sectRank = "Third-Class Disciple";
        }else{
            this.sectRank = "Outer Disciple";
        }
    }
    public void setSectRank(String sectRank){
        this.sectRank = sectRank;
    }
}

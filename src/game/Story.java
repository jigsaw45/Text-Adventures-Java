package game;

import Npc.Npc;
import Npc.Quest;
import Npc.QuestLog;
import Npc.QuestObjective;
import Npc.QuestType;
import combat.Element;
import enemy.Enemy;
import enemy.EnemyRole;
import items.Item;
import items.ItemFactory;
import player.RealmSystem;
import skills.SkillLoadout;
import skills.SkillBox;
import world.Room;
import world.Shop;
import world.World;

public class Story {
    private World world;
    private QuestLog questLog;
    private Shop medicineShop;
    private Shop tangShop;
    private SkillBox skillBox;

    public Story(SkillBox skillBox){
        this.skillBox = skillBox;
        this.world = new World();
        this.questLog = new QuestLog();
        buildWorld();
        buildNpcs();
        buildShops();
        buildQuests();
        questLog.refresh();
    }

    //World setup -----------------------------------------------------------------------------------------------------
    private void buildWorld(){
        Room ashfall = new Room("Ashfall Village",
                "Your village. It is currently a black smear on a green mountain. Nobody is left to complain about it.");
        Room mountainPath = new Room("Mountain Path",
                "A narrow trail where the wind smells of pine and, faintly, of people who made poor choices.");
        Room sectGate = new Room("Sect Gate",
                "Mount Hua's gate: two stone lions, one missing its head, both judging you.");
        Room courtyard = new Room("Outer Courtyard",
                "Outer disciples sweep leaves that will fall again in ten minutes. Such is the Dao.");
        Room quarters = new Room("Outer Quarters",
                "Twenty beds, nineteen snores. Yours is the one with the blanket that smells of smoke.");
        Room training = new Room("Training Grounds",
                "Wooden dummies stand in rows. They have seen things. They say nothing.");
        Room medicine = new Room("Medicine Hall",
                "The air is thick with herbs and the quiet despair of the recently poisoned.");
        Room elders = new Room("Elder's Hall",
                "The only room with a decent roof. Clearly the Dao rewards seniority.");
        Room bamboo = new Room("Bamboo Grove",
                "Tall bamboo, soft light, and at least three people who do not want company.");
        Room cliff = new Room("Cliff Spring",
                "A spring pours out of the cliff into a pool so clear you can see your own regrets.");

        world.addRoom(ashfall);
        world.addRoom(mountainPath);
        world.addRoom(sectGate);
        world.addRoom(courtyard);
        world.addRoom(quarters);
        world.addRoom(training);
        world.addRoom(medicine);
        world.addRoom(elders);
        world.addRoom(bamboo);
        world.addRoom(cliff);

        world.link("Ashfall Village", "north", "Mountain Path");
        world.link("Mountain Path", "north", "Sect Gate");
        world.link("Sect Gate", "north", "Outer Courtyard");
        world.link("Outer Courtyard", "north", "Training Grounds");
        world.link("Outer Courtyard", "east", "Outer Quarters");
        world.link("Outer Courtyard", "west", "Medicine Hall");
        world.link("Training Grounds", "north", "Elder's Hall");
        world.link("Outer Quarters", "east", "Bamboo Grove");
        world.link("Bamboo Grove", "north", "Cliff Spring");

        //floor loot
        ashfall.addItem(ItemFactory.create("Old Letter"));
        mountainPath.addItem(ItemFactory.create("Bamboo Shoot"));
        bamboo.addItem(ItemFactory.create("Moonpetal Herb"));
        bamboo.addItem(ItemFactory.create("Moonpetal Herb"));
        cliff.addItem(ItemFactory.create("Spirit Ginseng"));

        //threats
        mountainPath.setEnemyName("Bamboo Cutthroat");
        bamboo.setEnemyName("Bamboo Cutthroat");
        cliff.setEnemyName("Mountain Boar");
    }

    //Npc setup -------------------------------------------------------------------------------------------------------
    private void buildNpcs(){
        Npc wonBo = new Npc("Won Bo", "Gatekeeper", "An old soldier with one eye and no patience.");
        wonBo.addLine("I guard this gate. Nothing gets past me, except everyone.");
        wonBo.addLine("New disciples go north. Try not to die before lunch, it upsets the cook.");

        Npc tang = new Npc("Tang Seol-Hwa", "Tang Clan merchant", "Sharp eyes, sharper prices.");
        tang.addLine("Tang Clan steel, guaranteed to cut. Probably the thing you want it to cut.");
        tang.addLine("Payment up front. I have been burned before. Literally, once, in a wagon.");
        tang.setShopkeeper(true);
        tang.addQuestId("escort");

        Npc bum = new Npc("Disciple Bum", "Outer disciple", "He has been here two years and knows nothing.");
        bum.addLine("Senior! Is it true the golden boy once sneezed and a mountain moved?");
        bum.addLine("I am going to become a great cultivator. Right after lunch.");

        Npc mae = new Npc("Disciple Mae", "Outer disciple", "She notices everything and says half of it.");
        mae.addLine("The well water tastes strange lately. Probably just minerals.");
        mae.addLine("Three disciples are in the medicine hall. Also probably minerals.");

        Npc yeon = new Npc("Senior Sister Yeon", "Second-Class Disciple", "The only person here who looks at you like a person.");
        yeon.addLine("You have the eyes of someone who has already died once. Interesting.");
        yeon.addLine("If you mean to chase revenge, get strong first. Corpses are terrible at it.");
        yeon.addLine("Walk to the Cliff Spring and back without dying. Consider it a lesson.");
        yeon.addQuestId("errand");

        Npc ajumma = new Npc("Cook Ajumma", "Sect cook", "She could win a war with a ladle.");
        ajumma.addLine("Eat. You are all bones and bad decisions.");
        ajumma.addLine("The well? I cook with the well. Everyone is fine. Mostly fine.");

        Npc ma = new Npc("Instructor Ma", "Sword Instructor", "Half teacher, half threat.");
        ma.addLine("Your stance is terrible, but your stubbornness is first rate. I can work with that.");
        ma.addLine("Bandits have moved into the bamboo. Go make the sect look competent for once.");
        ma.addQuestId("bandits");

        Npc haJin = new Npc("Go Ha-Jin", "Mount Hua's golden boy", "The sect's prodigy, and he knows it.");
        haJin.addLine("Ah. The one from the burned village. How rustic.");
        haJin.addLine("You look at this world like a tourist. I find that insulting.");

        Npc gu = new Npc("Physician Gu", "Sect physician", "Tired, precise, and out of herbs.");
        gu.addLine("Herbs. I need herbs. I am a physician, not a gardener.");
        gu.addLine("Bring me three moonpetal. I will pay you in medicine and mild approval.");
        gu.setShopkeeper(true);
        gu.addQuestId("herbs");

        Npc nam = new Npc("Elder Nam", "Elder of the outer court", "Speaks in aphorisms because it saves time.");
        nam.addLine("The heavens do not care about your revenge. They do, however, charge a toll.");
        nam.addLine("At the ninth stage, swallow a Breakthrough Pill and climb. Most who try come back shorter.");
        nam.addQuestId("gate");

        Npc sweeper = new Npc("Sweeper Old Man", "Sweeper", "Nobody knows his name or why the leaves avoid him.");
        sweeper.addLine("These leaves will outlive us both, young one.");
        sweeper.addLine("Sweep your mind before you sweep the yard. Or after. I am not your master.");

        Npc beggar = new Npc("Little Beggar", "Beggar", "Dirty, quick, and better informed than the elders.");
        beggar.addLine("Spare a copper, senior? I will tell you a secret.");
        beggar.addLine("Masked men come down this path at night. They do not tip.");

        world.getRoom("Sect Gate").addNpc(wonBo);
        world.getRoom("Sect Gate").addNpc(tang);
        world.getRoom("Mountain Path").addNpc(beggar);
        world.getRoom("Outer Courtyard").addNpc(bum);
        world.getRoom("Outer Courtyard").addNpc(sweeper);
        world.getRoom("Outer Courtyard").addNpc(ajumma);
        world.getRoom("Outer Quarters").addNpc(yeon);
        world.getRoom("Training Grounds").addNpc(ma);
        world.getRoom("Training Grounds").addNpc(haJin);
        world.getRoom("Medicine Hall").addNpc(gu);
        world.getRoom("Elder's Hall").addNpc(nam);
        world.getRoom("Bamboo Grove").addNpc(mae);
    }

    public Npc getNpcByName(String name){
        for(Room room : world.getRooms()){
            Npc found = room.getNpc(name);
            if(found != null){
                return found;
            }
        }
        return null;
    }

    //Shop setup ------------------------------------------------------------------------------------------------------
    private void buildShops(){
        medicineShop = new Shop("Medicine Hall");
        medicineShop.addStock(ItemFactory.create("Healing Salve"));
        medicineShop.addStock(ItemFactory.create("Blood Restoring Pill"));
        medicineShop.addStock(ItemFactory.create("Qi Gathering Pill"));
        medicineShop.addStock(ItemFactory.create("Meridian Cleansing Pill"));
        medicineShop.addStock(ItemFactory.create("Breakthrough Pill"));

        tangShop = new Shop("Tang Merchant Wagon");
        tangShop.addStock(ItemFactory.create("Tang Steel Dagger"));
        tangShop.addStock(ItemFactory.create("Mount Hua Straight Sword"));
        tangShop.addStock(ItemFactory.create("Rage Powder"));
        tangShop.addStock(ItemFactory.create("Bamboo Shoot"));
    }

    //Quest setup -----------------------------------------------------------------------------------------------------
    private void buildQuests(){
        Quest ashes = new Quest("ashes", "Ashes and Questions", "Search the ruins of your village. Something survived the fire.", QuestType.INVESTIGATE, null, 30, 20);
        ashes.addObjective(new QuestObjective(QuestType.INVESTIGATE, "Village Ruins", 3));
        ashes.addItemReward("Azure Cloud Manual");
        questLog.add(ashes);

        Quest escort = new Quest("escort", "The Long Climb", "Tang Seol-Hwa's wagon needs an escort up the mountain path.", QuestType.ESCORT, "ashes", 50, 30);
        escort.addObjective(new QuestObjective(QuestType.ESCORT, "Mountain Path", 1));
        escort.addObjective(new QuestObjective(QuestType.KILL, "Bamboo Cutthroat", 2));
        escort.addItemReward("Healing Salve");
        questLog.add(escort);

        Quest bandits = new Quest("bandits", "Bandits in the Bamboo", "Instructor Ma wants the bamboo grove cleared of cutthroats.", QuestType.KILL, "ashes", 60, 25);
        bandits.addObjective(new QuestObjective(QuestType.KILL, "Bamboo Cutthroat", 3));
        bandits.addItemReward("Qi Gathering Pill");
        questLog.add(bandits);

        Quest herbs = new Quest("herbs", "Herbs for the Hall", "Physician Gu needs three moonpetal herbs before his patients get creative.", QuestType.FETCH, "ashes", 40, 20);
        herbs.addObjective(new QuestObjective(QuestType.FETCH, "Moonpetal Herb", 3));
        herbs.addItemReward("Healing Salve");
        questLog.add(herbs);

        Quest errand = new Quest("errand", "A Senior's Errand", "Yeon wants proof you can walk to the Cliff Spring without dying.", QuestType.ESCORT, "herbs", 50, 40);
        errand.addObjective(new QuestObjective(QuestType.ESCORT, "Cliff Spring", 1));
        errand.addItemReward("Meridian Cleansing Pill");
        questLog.add(errand);

        Quest duel = new Quest("duel", "The Golden Boy", "Go Ha-Jin has been insulting your village. Correct him.", QuestType.DUEL, "bandits", 80, 60);
        duel.addObjective(new QuestObjective(QuestType.DUEL, "Go Ha-Jin", 1));
        duel.addItemReward("Tang Steel Dagger");
        questLog.add(duel);

        Quest poison = new Quest("poison", "Poison in the Well", "Three disciples are sick and the well tastes wrong. Ask around.", QuestType.INVESTIGATE, "duel", 90, 70);
        poison.addObjective(new QuestObjective(QuestType.INVESTIGATE, "Physician Gu", 1));
        poison.addObjective(new QuestObjective(QuestType.INVESTIGATE, "Disciple Bum", 1));
        poison.addObjective(new QuestObjective(QuestType.INVESTIGATE, "Cook Ajumma", 1));
        poison.addObjective(new QuestObjective(QuestType.FETCH, "Black Lotus Token", 1));
        poison.addItemReward("Blood Restoring Pill");
        questLog.add(poison);

        Quest gate = new Quest("gate", "The Realm Gate", "Elder Nam says the ninth stage is where the heavens start watching.", QuestType.STORY, "poison", 100, 100);
        gate.addObjective(new QuestObjective(QuestType.STORY, "Breakthrough", 1));
        gate.addItemReward("Meridian Cleansing Pill");
        questLog.add(gate);

        Quest finale = new Quest("finale", "Black Lotus at the Gate", "The masked men are no longer hiding. Meet them at the gate.", QuestType.KILL, "gate", 200, 200);
        finale.addObjective(new QuestObjective(QuestType.KILL, "Black Lotus Lieutenant", 1));
        finale.addItemReward("Mount Hua Straight Sword");
        questLog.add(finale);
    }

    //Enemy factory ---------------------------------------------------------------------------------------------------
    public Enemy createEnemy(String name){
        switch(name){
            case "Bamboo Cutthroat":
                return new Enemy("Bamboo Cutthroat", 6, 7, 11, 5, 8,
                        RealmSystem.getREALMS()[0], 1, loadout(4, 5, 4, 5),
                        EnemyRole.DUELIST, Element.NONE, 30, 8);
            case "Mountain Boar":
                return new Enemy("Mountain Boar", 10, 12, 6, 4, 10,
                        RealmSystem.getREALMS()[0], 2, loadout(4, 4, 0, 4),
                        EnemyRole.BERSERKER, Element.EARTH, 35, 6);
            case "Black Lotus Scout":
                return new Enemy("Black Lotus Scout", 7, 6, 11, 13, 9,
                        RealmSystem.getREALMS()[0], 4, loadout(11, 6, 9, 4),
                        EnemyRole.CASTER, Element.LIGHTNING, 45, 15);
            case "Black Lotus Enforcer":
                return new Enemy("Black Lotus Enforcer", 13, 14, 8, 8, 12,
                        RealmSystem.getREALMS()[0], 6, loadout(0, 5, 9, 0),
                        EnemyRole.TANK, Element.EARTH, 60, 25);
            case "Go Ha-Jin":
                Enemy rival = new Enemy("Go Ha-Jin", 10, 14, 14, 14, 14,
                        RealmSystem.getREALMS()[0], 8, loadout(12, 7, 5, 13),
                        EnemyRole.DUELIST, Element.LIGHTNING, 80, 30);
                rival.setDeathText("Go Ha-Jin yields, breathing hard. 'Fine. You are less rustic than I thought.'");
                return rival;
            case "Heavenly Tribulation":
                Enemy tribulation = new Enemy("Heavenly Tribulation", 12, 10, 16, 20, 20,
                        RealmSystem.getREALMS()[0], 9, loadout(13, 11, 12, 11),
                        EnemyRole.CASTER, Element.LIGHTNING, 100, 0);
                tribulation.setDeathText("The clouds break. The heavens, having made their point, move on.");
                return tribulation;
            case "Black Lotus Lieutenant":
                Enemy boss = new Enemy("Black Lotus Lieutenant", 14, 16, 16, 18, 18,
                        RealmSystem.getREALMS()[0], 9, loadout(15, 7, 13, 12),
                        EnemyRole.BERSERKER, Element.FIRE, 150, 100);
                boss.setDeathText("The masked man falls. His mask splits, and the face under it is nobody you know. Yet.");
                return boss;
            default:
                return new Enemy(name, 8, 8, 8, 8, 8,
                        RealmSystem.getREALMS()[0], 1, loadout(4, 0, 4, 0),
                        EnemyRole.DUELIST, Element.NONE, 20, 5);
        }
    }

    private SkillLoadout loadout(int one, int two, int three, int four){
        return new SkillLoadout(skillBox.getSkillIndex()[one], skillBox.getSkillIndex()[two],
                skillBox.getSkillIndex()[three], skillBox.getSkillIndex()[four]);
    }

    //getters
    public World getWorld(){
        return world;
    }
    public QuestLog getQuestLog(){
        return questLog;
    }
    public Shop getMedicineShop(){
        return medicineShop;
    }
    public Shop getTangShop(){
        return tangShop;
    }
}

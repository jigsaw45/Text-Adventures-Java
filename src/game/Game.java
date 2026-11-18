package game;

import Npc.Npc;
import Npc.Quest;
import Npc.QuestLog;
import Npc.QuestObjective;
import Npc.QuestType;
import combat.CombatEngine;
import combat.Element;
import combat.Stats;
import enemy.Enemy;
import enemy.EnemyAI;
import io.Command;
import io.CommandParser;
import io.SaveManager;
import io.TextUI;
import items.Fruit;
import items.Item;
import items.ItemFactory;
import items.Manual;
import items.Pill;
import items.Token;
import items.Weapon;
import player.Player;
import player.RealmSystem;
import skills.Skill;
import skills.SkillBox;
import skills.SkillLoadout;
import world.Room;
import world.Shop;
import world.World;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
    private Player player;
    private World world;
    private Story story;
    private QuestLog questLog;
    private Shop medicineShop;
    private Shop tangShop;
    private CombatController combatController;
    private CombatEngine combatEngine;
    private EnemyAI enemyAI;
    private TextUI textUI;
    private SkillBox skillBox;
    private SaveManager saveManager;
    private Set<String> flags;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);

    public Game(){
        setupworld();
    }

    //Setup and creation -----------------------------------------------------------------------------------------------
    private void setupworld(){
        //misc setups
        textUI = new TextUI();
        combatEngine = new CombatEngine();
        enemyAI = new EnemyAI();
        combatController = new CombatController(combatEngine, enemyAI, textUI, scanner);
        skillBox = new SkillBox();
        flags = new HashSet<>();
        saveManager = new SaveManager("save.txt");

        //story, world, quests and shops
        story = new Story(skillBox);
        world = story.getWorld();
        questLog = story.getQuestLog();
        medicineShop = story.getMedicineShop();
        tangShop = story.getTangShop();

        //player starts with one real technique and an apology for a sword
        skillBox.getSkillIndex()[4].setOwned(true);
        SkillLoadout playerSkillLoadout = new SkillLoadout(
                new Skill(),
                skillBox.getSkillIndex()[4],
                new Skill(),
                new Skill());
        player = new Player("Han Woo", world.getRoom("Ashfall Village"), playerSkillLoadout);
        player.getInventory().addItem(ItemFactory.create("Rusted Saber"));
        player.getInventory().addItem(ItemFactory.create("Healing Salve"));
        player.setEquippedWeapon((Weapon) player.getInventory().getItem("Rusted Saber"));
        player.changeSpiritStones(10);
        player.updateSectRank();

        //the prologue quest is running from the first screen
        Quest ashes = questLog.get("ashes");
        ashes.start();
        questLog.refresh();
    }

    public void start() {
        intro();
        while(running){
            String input = scanner.nextLine();
            Command cmd = CommandParser.parse(input);
            handleCommand(cmd);
        }
    }

    private void intro(){
        textUI.banner("Ashfall Village, three days after the fire");
        textUI.print("You remember dying in a bus accident and waking up in a body that was already on fire.");
        textUI.print("The genre conventions suggest a second chance. The burns suggest otherwise.");
        textUI.print("You are Han Woo, newest outer disciple of Mount Hua. Technically.");
        textUI.print("A half burned letter in the ash has one word left on it: MASK.");
        textUI.print("Type 'help' if you are new. Type 'look' to pay attention.");
        textUI.commandLook(player);
    }

    public void handleCommand(Command cmd){
        String verb = cmd.getVerb();
        String noun = cmd.getNoun() == null ? "" : cmd.getNoun().trim();

        switch(verb){
            case "look":
                textUI.commandLook(player);
                break;
            case "go":
            case "move":
            case "walk":
                if(noun.isEmpty()){
                    textUI.print("Go where? (" + player.getCurrentRoom().getExitText() + ")");
                }else{
                    move(noun);
                }
                break;
            case "north": case "n":
                move("north");
                break;
            case "south": case "s":
                move("south");
                break;
            case "east": case "e":
                move("east");
                break;
            case "west": case "w":
                move("west");
                break;
            case "up":
                move("up");
                break;
            case "down":
                move("down");
                break;
            case "talk":
            case "speak":
                talk(noun);
                break;
            case "search":
                search();
                break;
            case "take":
            case "get":
                takeItem(noun);
                break;
            case "drop":
                dropItem(noun);
                break;
            case "give":
            case "offer":
                give(noun);
                break;
            case "inventory":
            case "inv":
            case "i":
                player.getInventory().displayInventory();
                break;
            case "quests":
            case "journal":
                questLog.display();
                break;
            case "fight":
            case "attack":
            case "duel":
                fight(noun);
                break;
            case "train":
            case "cultivate":
                cultivate();
                break;
            case "stats":
                statMenu();
                break;
            case "status":
            case "sheet":
                textUI.displayPlayerStats(player);
                break;
            case "skills":
            case "techniques":
                skillMenu();
                break;
            case "equip":
                equipTechnique(noun);
                break;
            case "use":
            case "eat":
                useItem(noun);
                break;
            case "read":
                readItem(noun);
                break;
            case "shop":
            case "store":
                shop();
                break;
            case "buy":
                buy(noun);
                break;
            case "sell":
                sell(noun);
                break;
            case "breakthrough":
                attemptBreakthrough();
                break;
            case "rest":
            case "sleep":
                rest();
                break;
            case "save":
                save();
                break;
            case "load":
                load();
                break;
            case "help":
                help();
                break;
            case "quit":
            case "exit":
                running = false;
                textUI.print("Goodbye. Try not to reincarnate into anything flammable.");
                break;
            default:
                textUI.invalidCmd();
                break;
        }
    }

    //Misc methods -----------------------------------------------------------------------------------------------------
    public void sleep(double seconds){
        try{
            Thread.sleep((long) (seconds*1000));
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

    private int readInt(){
        String input = scanner.nextLine();
        try{
            return Integer.parseInt(input.trim());
        }catch(NumberFormatException ex){
            return -1;
        }
    }

    private int parseIntSafe(String value){
        try{
            return Integer.parseInt(value.trim());
        }catch(NumberFormatException ex){
            return -1;
        }
    }

    private void help(){
        textUI.banner("Commands");
        textUI.print("look, go <direction>, talk <name>, search, take <item>, drop <item>, give <item> to <name>");
        textUI.print("inventory, quests, fight [enemy], train, stats, status, skills, equip <index> <slot>");
        textUI.print("use <item>, read <item>, shop, buy <item>, sell <item>, breakthrough, rest, save, load, quit");
    }

    //Movement and rooms -----------------------------------------------------------------------------------------------
    private void move(String direction){
        Room next = player.getCurrentRoom().getExit(direction);
        if(next == null){
            textUI.print("You cant go that way!");
            return;
        }
        enterRoom(next);
    }

    private void enterRoom(Room room){
        boolean firstVisit = !room.isVisited();
        player.setCurrentRoom(room);
        room.setVisited(true);
        textUI.commandLook(player);
        storyOnEnter(room, firstVisit);

        if(room.getEnemyName() != null && !room.isCleared()){
            textUI.print("A " + room.getEnemyName() + " notices you.");
        }
    }

    private void storyOnEnter(Room room, boolean firstVisit){
        String name = room.getRoomName();

        if(name.equals("Sect Gate") && firstVisit){
            textUI.banner("Mount Hua Sect");
            textUI.print("Won Bo looks you over. \"Another one. Wonderful.\"");
            textUI.print("Tang Seol-Hwa's wagon sits by the gate. She is already counting your money.");
        }
        if(name.equals("Outer Courtyard") && firstVisit){
            textUI.banner("Outer Court");
            textUI.print("An old sweeper nods at you. The leaves part around his broom.");
        }
        if(name.equals("Training Grounds") && firstVisit){
            textUI.print("Go Ha-Jin stops mid-form, looks at you, and laughs exactly once.");
        }
        if(name.equals("Elder's Hall") && firstVisit){
            textUI.print("Elder Nam does not look up. \"Peak of the ninth stage. Then we talk.\"");
        }
        if(name.equals("Cliff Spring") && firstVisit){
            textUI.print("The water is clear to the bottom. Something at the bottom looks back.");
        }

        //escort style quests advance by arriving
        if(name.equals("Mountain Path")){
            questLog.addProgress(QuestType.ESCORT, "Mountain Path");
            checkQuestCompletion();
        }
        if(name.equals("Cliff Spring")){
            questLog.addProgress(QuestType.ESCORT, "Cliff Spring");
            checkQuestCompletion();
        }

        //the finale spawns at the gate once the realm gate is done
        Quest finale = questLog.get("finale");
        if(name.equals("Sect Gate") && finale != null && finale.isActive() && !room.isCleared() && room.getEnemyName() == null){
            room.setEnemyName("Black Lotus Lieutenant");
            textUI.print("A masked figure waits under the gate, a black lotus stitched on one sleeve.");
        }
    }

    //Talking, searching and items ------------------------------------------------------------------------------------
    private void talk(String target){
        if(target.isEmpty()){
            textUI.print("Talk to whom?");
            return;
        }
        Npc npc = player.getCurrentRoom().getNpc(target);
        if(npc == null){
            textUI.print("No one called " + target + " here.");
            return;
        }

        textUI.print(npc.getName() + ": \"" + npc.getLine((int)(Math.random()*Math.max(1, npc.lineCount()))) + "\"");

        //investigations advance by asking the right people
        questLog.addProgress(QuestType.INVESTIGATE, npc.getName());
        checkQuestCompletion();

        for(String questId : npc.getQuestIds()){
            Quest quest = questLog.get(questId);
            if(quest != null && quest.isAvailable()){
                offerQuest(npc, quest);
            }
        }

        if(npc.isShopkeeper()){
            textUI.print("Type 'shop' to trade.");
        }
        if(player.getCurrentRoom().getRoomName().equals("Training Grounds") && npc.getName().equals("Go Ha-Jin")){
            Quest duel = questLog.get("duel");
            if(duel != null && duel.isActive()){
                textUI.print("Go Ha-Jin steps onto the practice mat. Type 'fight go ha-jin'.");
            }
        }
        if(npc.getName().equalsIgnoreCase("Little Beggar") && !flags.contains("beggarPaid")){
            textUI.print("(He is clearly waiting for a coin. Try: give 5 stones to beggar)");
        }
    }

    //gifts, mostly to see whether the beggar is worth listening to
    private void give(String args){
        if(args.isEmpty() || !args.toLowerCase().contains(" to ")){
            textUI.print("Usage: give <amount> stones to <name>, or give <item> to <name>");
            return;
        }
        String[] parts = args.split("(?i)\\s+to\\s+", 2);
        String gift = parts[0].trim();
        String target = parts[1].trim();

        Npc npc = player.getCurrentRoom().getNpc(target);
        if(npc == null){
            textUI.print("No one called " + target + " here.");
            return;
        }

        if(npc.getName().equalsIgnoreCase("Little Beggar")){
            beggarGift(gift);
            return;
        }
        textUI.print(npc.getName() + " politely declines. The sect frowns on bribery, officially.");
    }

    private void beggarGift(String gift){
        String lower = gift.toLowerCase();
        boolean coin = lower.contains("stone") || lower.matches("[0-9]+");
        int paid = 0;

        if(coin){
            String digits = gift.replaceAll("[^0-9]", "");
            paid = digits.isEmpty() ? 5 : parseIntSafe(digits);
            if(paid <= 0){
                textUI.print("A gift of nothing is still a gift. He is not impressed.");
                return;
            }
            if(!player.spendSpiritStones(paid)){
                textUI.print("You do not have " + paid + " spirit stones.");
                return;
            }
        }else{
            Item item = player.getInventory().getItem(gift);
            if(item == null){
                textUI.print("You do not have " + gift + ".");
                return;
            }
            player.getInventory().removeItem(gift, 1);
        }

        if(flags.contains("beggarPaid")){
            textUI.print("The beggar pockets it and repeats himself. Information is a subscription.");
            return;
        }

        flags.add("beggarPaid");
        textUI.print("The beggar bites the coin, nods, and leans in.");
        textUI.print("\"Masked men come down this path at night. Boots too clean for bandits.\"");
        textUI.print("\"They meet at the gate when the moon is thin. And they always buy bamboo shoots.\"");
        textUI.print("You feel worldly. +10 XP.");
        player.getStats().increaseXp(10);
        giveItem("Bamboo Shoot");
    }

    private void offerQuest(Npc npc, Quest quest){
        textUI.banner(npc.getName() + " has work");
        textUI.print(quest.getQuestName() + ": " + quest.getQuestDescription());
        textUI.print("Accept? (1) yes (2) no");
        if(scanner.nextLine().trim().equals("1")){
            startQuest(quest);
            textUI.print("Quest accepted: " + quest.getQuestName());
            textUI.print(quest.getObjectiveText().trim());

            //the poison trail actually leaves something to find
            if(quest.getId().equals("poison")){
                world.getRoom("Bamboo Grove").addItem(ItemFactory.create("Black Lotus Token"));
                textUI.print("Disciple Mae mentioned a black petal in the bamboo. Something was dropped there.");
            }
            //a fetch quest you already have the goods for can finish on the spot
            checkQuestCompletion();
        }
    }

    //starting a fetch quest counts herbs you were already carrying
    private void startQuest(Quest quest){
        quest.start();
        for(QuestObjective objective : quest.getObjectives()){
            if(objective.getType() == QuestType.FETCH){
                int held = player.getInventory().countItem(objective.getTarget());
                objective.setCurrent(Math.min(objective.getRequired(), held));
            }
        }
        questLog.refresh();
    }

    private void search(){
        Room room = player.getCurrentRoom();
        if(!room.getRoomName().equals("Ashfall Village")){
            textUI.print("You search. The world declines to cooperate.");
            return;
        }
        if(!flags.contains("ruins1")){
            flags.add("ruins1");
            textUI.print("A boot print in the ash. Too clean for a villager. You take a moonpetal herb while you are here.");
            giveItem("Moonpetal Herb");
        }else if(!flags.contains("ruins2")){
            flags.add("ruins2");
            textUI.print("A crushed black petal. It smells of iron. You pocket a spirit ginseng beside it.");
            giveItem("Spirit Ginseng");
        }else if(!flags.contains("ruins3")){
            flags.add("ruins3");
            textUI.print("Scratched into a doorframe: a five petalled lotus, painted in a hurry.");
            giveItem("Old Letter");
        }else{
            textUI.print("Only ash left. You have searched this grief to the bottom.");
            return;
        }
        questLog.addProgress(QuestType.INVESTIGATE, "Village Ruins");
        checkQuestCompletion();
    }

    private void takeItem(String itemName){
        if(itemName.isEmpty()){
            textUI.print("Take what?");
            return;
        }
        Item taken = player.getCurrentRoom().takeItem(itemName);
        if(taken == null){
            textUI.print("There is no " + itemName + " here.");
            return;
        }
        player.getInventory().addItem(taken);
        textUI.itemGained(taken);
        questLog.addProgress(QuestType.FETCH, taken.getName());
        checkQuestCompletion();
    }

    private void dropItem(String itemName){
        Item held = player.getInventory().getItem(itemName);
        if(held == null){
            textUI.print("You do not have " + itemName + ".");
            return;
        }
        Item dropped = ItemFactory.create(held.getName());
        dropped.setQuantity(1);
        player.getCurrentRoom().addItem(dropped);
        player.getInventory().removeItem(itemName, 1);
        textUI.print("You drop " + held.getName() + ".");
    }

    private void giveItem(String itemName){
        Item item = ItemFactory.create(itemName);
        player.getInventory().addItem(item);
        textUI.itemGained(item);
    }

    //Cultivation and progression -------------------------------------------------------------------------------------
    private void cultivate(){
        textUI.print("Do you want to start cultivating?\n(1)Yes\n(2)No");
        if(!scanner.nextLine().trim().equals("1")){
            return;
        }
        int xpPerDay = 10 + (player.getStats().getTotalStages() * 2);
        textUI.print("How many days do you want to cultivate for? (0 to cancel)");
        int amount = readInt();
        if(amount <= 0){
            textUI.print("You decide the floor is fine.");
            return;
        }
        for(int i = 0; i < amount; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(".");
                sleep(0.1);
            }
            sleep(0.05);
            System.out.println(" You have been cultivating for " + (i+1) + " days");
            player.getStats().increaseXp(xpPerDay);
            player.addDays(1);
            player.updateSectRank();

            //check in halfway so a long session can be cut short
            if((i+1) % 5 == 0 && i+1 < amount){
                textUI.print("Keep going? (1) yes (2) stop");
                if(scanner.nextLine().trim().equals("2")){
                    textUI.print("You stop while your legs still work.");
                    break;
                }
            }
        }
        if(player.getStats().canBreakthrough()){
            textUI.print("Your qi has filled the ninth stage. Type 'breakthrough'.");
        }
    }

    private void attemptBreakthrough(){
        Stats stats = player.getStats();
        if(!stats.canBreakthrough()){
            textUI.print("You are not at the peak of the ninth stage. Cultivate first.");
            return;
        }
        if(!player.getInventory().hasItem("Breakthrough Pill")){
            textUI.print("You need a Breakthrough Pill. Physician Gu sells them, at a price that hurts.");
            return;
        }
        player.getInventory().removeItem("Breakthrough Pill", 1);

        RealmSystem realmSystem = new RealmSystem();
        boolean major = !stats.isMaxRealm() && realmSystem.isMajorRealm(RealmSystem.getREALMS()[stats.getRealmIndex()+1]);

        if(major){
            textUI.banner("Heavenly Tribulation");
            textUI.print("The sky darkens with an interest you did not ask for.");
            Enemy tribulation = story.createEnemy("Heavenly Tribulation");
            boolean survived = combatController.fight(player, tribulation);
            if(!survived){
                stats.breakthroughFailed();
                textUI.print("The tribulation breaks you. Half your gathered qi is gone, and so is some blood.");
                return;
            }
        }else{
            int chance = Math.min(90, 55 + (stats.getFocus()/2));
            if((int)(Math.random()*100) >= chance){
                stats.breakthroughFailed();
                textUI.print("Your meridians rebel. You lose half your gathered qi and a fair amount of blood.");
                return;
            }
        }

        stats.breakthroughSucceeded();
        player.updateSectRank();
        textUI.print("Your cultivation breaks through. You are now " + stats.getCurrentRealm().getName() + " stage " + stats.getCurrentStage() + ".");
        questLog.addProgress(QuestType.STORY, "Breakthrough");
        checkQuestCompletion();
    }

    private void rest(){
        if(!player.getCurrentRoom().getRoomName().equals("Outer Quarters")){
            textUI.print("You need a bed. There is one in the Outer Quarters.");
            return;
        }
        player.getStats().recoverHP();
        player.getStats().recoverStamina();
        player.getStats().recoverQi();
        player.getStats().clearStatuses();
        player.addDays(1);
        textUI.print("You sleep like the dead, but with better breathing. Everything restored.");
    }

    //Stat investment Methods -----------------------------------------------------------------------------------------
    private void investInStat(int statChoice){
        int playerPoints = player.getStats().getPoints();
        if(playerPoints <= 0){
            textUI.print("No points to spend. Cultivate, or take up farming.");
            return;
        }
        textUI.pointAmount(playerPoints);
        int amount = readInt();
        if(amount <= 0 || amount > playerPoints){
            textUI.pointAmountError();
            return;
        }

        String stat = "";
        switch(statChoice){
            case 1:
                stat="Vitality";
                player.getStats().increaseVitality(amount);
                player.getStats().recoverHP();
                break;
            case 2:
                stat="Stamina";
                player.getStats().increaseStamina(amount);
                player.getStats().recoverStamina();
                break;
            case 3:
                stat="Strength";
                player.getStats().increaseStrength(amount);
                break;
            case 4:
                stat="Focus";
                player.getStats().increaseFocus(amount);
                player.getStats().recoverQi();
                break;
            case 5:
                stat="Agility";
                player.getStats().increaseAgility(amount);
                break;
            default:
                textUI.invalidCmd();
                return;
        }
        textUI.pointInvested(stat, amount);
        player.getStats().increasePoints(-amount);
    }

    public void statMenu() {
        boolean menuRun = true;
        while(menuRun) {
            textUI.displayPlayerStats(player);

            switch (readInt()) {
                case 1:
                    investInStat(1);
                    break;
                case 2:
                    investInStat(2);
                    break;
                case 3:
                    investInStat(3);
                    break;
                case 4:
                    investInStat(4);
                    break;
                case 5:
                    investInStat(5);
                    break;
                case 6:
                    menuRun = false;
                    break;
                default:
                    textUI.invalidCmd();
                    break;
            }
        }
    }

    //Skills -----------------------------------------------------------------------------------------------------------
    private void skillMenu(){
        textUI.skillBookUnlockMenu(skillBox);
        textUI.displayLoadout(player);
        textUI.print("Equip with: equip <technique index> <slot 1-4>");
    }

    private void equipTechnique(String args){
        if(args.isEmpty()){
            textUI.print("Usage: equip <technique index> <slot 1-4>");
            return;
        }
        String[] parts = args.split("\\s+");
        if(parts.length < 2){
            textUI.print("Usage: equip <technique index> <slot 1-4>");
            return;
        }
        int index = parseIntSafe(parts[0]);
        int slot = parseIntSafe(parts[1]) - 1;
        if(index < 0 || index >= skillBox.getSkillIndex().length || slot < 0 || slot > 3){
            textUI.invalidCmd();
            return;
        }
        Skill skill = skillBox.getSkillIndex()[index];
        if(!skill.getOwned()){
            textUI.print("You have not learned that one.");
            return;
        }
        player.getSkillLoadout().addSkill(skill, slot);
        textUI.print(skill.getName() + " equipped in slot " + (slot+1) + ".");
    }

    //Item use --------------------------------------------------------------------------------------------------------
    private void useItem(String itemName){
        if(itemName.isEmpty()){
            textUI.print("Use what?");
            return;
        }
        Item item = player.getInventory().getItem(itemName);
        if(item == null){
            textUI.print("You do not have " + itemName + ".");
            return;
        }

        if(item instanceof Manual){
            Manual manual = (Manual) item;
            if(manual.getSkillIndex() == -1){
                if(!chooseAffinity()){
                    return;
                }
            }else{
                Skill learned = skillBox.getSkillIndex()[manual.getSkillIndex()];
                learned.setOwned(true);
                textUI.print("You study " + manual.getName() + " and learn " + learned.getName() + ".");
            }
            player.getInventory().removeItem(itemName, 1);
            return;
        }

        if(item instanceof Pill){
            Pill pill = (Pill) item;
            switch(pill.getPillType()){
                case CULTIVATION:
                    player.getStats().increaseXp(pill.getEnergyAmount());
                    player.getInventory().removeItem(itemName, 1);
                    textUI.print("Warmth floods your meridians. +" + pill.getEnergyAmount() + " XP.");
                    break;
                case HEALING:
                    player.getStats().heal(pill.getEnergyAmount());
                    player.getInventory().removeItem(itemName, 1);
                    textUI.print("Your wounds close. HP: " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP());
                    break;
                case COMBAT:
                    player.addDamageBuff(pill.getEnergyAmount());
                    player.getInventory().removeItem(itemName, 1);
                    textUI.print("Your fists tingle. +" + pill.getEnergyAmount() + " damage for the next fight only.");
                    break;
                case BREAKTHROUGH:
                    textUI.print("Save that for the peak of a stage. Then type 'breakthrough'.");
                    break;
                default:
                    textUI.invalidCmd();
                    break;
            }
            player.updateSectRank();
            return;
        }

        if(item instanceof Fruit){
            player.getStats().heal(15);
            player.getInventory().removeItem(itemName, 1);
            textUI.print("You eat it. It tastes like the ground, which is apparently nutritious.");
            return;
        }

        if(item instanceof Token){
            readItem(itemName);
            return;
        }

        textUI.print("Nothing happens. The item remains disappointed.");
    }

    //letters and manuals are read rather than used
    private void readItem(String itemName){
        if(itemName.isEmpty()){
            textUI.print("Read what?");
            return;
        }
        Item item = player.getInventory().getItem(itemName);
        if(item == null){
            textUI.print("You do not have " + itemName + ".");
            return;
        }

        if(item instanceof Manual){
            useItem(itemName);
            return;
        }

        if(item instanceof Token && ((Token) item).getType().equalsIgnoreCase("letter")){
            textUI.banner("The Old Letter");
            textUI.print("Most of it is ash. What survives reads:");
            textUI.print("\"...the lotus blooms where the water is sweetest. Burn the rest.\"");
            textUI.print("The signature is gone. The paper smells faintly of iron.");
            return;
        }

        textUI.print("There is nothing written on the " + item.getName() + ".");
    }

    //the Azure Cloud Manual decides your element
    private boolean chooseAffinity(){
        if(player.getAffinity() != Element.NONE){
            textUI.print("You already carry " + player.getAffinity().getName() + ". The manual has gone quiet.");
            return false;
        }
        textUI.print("The pages are blank. Three elements wait behind your eyes.");
        textUI.print("(1) Fire  (2) Earth  (3) Lightning");
        int choice = readInt();

        Element element;
        int skillIndex;
        switch(choice){
            case 1:
                element = Element.FIRE;
                skillIndex = 8;
                break;
            case 2:
                element = Element.EARTH;
                skillIndex = 10;
                break;
            case 3:
                element = Element.LIGHTNING;
                skillIndex = 12;
                break;
            default:
                textUI.print("You blink and the moment is gone. Try the manual again.");
                return false;
        }

        player.setAffinity(element);
        skillBox.getSkillIndex()[skillIndex].setOwned(true);
        player.getSkillLoadout().addSkill(skillBox.getSkillIndex()[skillIndex], 2);
        textUI.print("You are now " + element.getName() + "-aligned. " + skillBox.getSkillIndex()[skillIndex].getName() + " is yours.");
        return true;
    }

    //Shop ------------------------------------------------------------------------------------------------------------
    private Shop currentShop(){
        String room = player.getCurrentRoom().getRoomName();
        if(room.equals("Medicine Hall")){
            return medicineShop;
        }
        if(room.equals("Sect Gate")){
            return tangShop;
        }
        return null;
    }

    private void shop(){
        Shop shop = currentShop();
        if(shop == null){
            textUI.print("No shop here. The merchants are where the money is.");
            return;
        }
        shop.displayStock();
        textUI.print("You have " + player.getSpiritStones() + " spirit stones. Type 'buy <item>' or 'sell <item>'.");
    }

    private void buy(String itemName){
        Shop shop = currentShop();
        if(shop == null){
            textUI.print("No shop here.");
            return;
        }
        Item template = shop.findStock(itemName);
        if(template == null){
            textUI.print("They do not sell that.");
            return;
        }
        if(!player.spendSpiritStones(template.getValue())){
            textUI.print("Not enough spirit stones. Poverty is also a cultivation path.");
            return;
        }
        Item bought = ItemFactory.create(template.getName());
        player.getInventory().addItem(bought);
        textUI.itemGained(bought);
        textUI.print("Remaining stones: " + player.getSpiritStones());
    }

    private void sell(String itemName){
        Shop shop = currentShop();
        if(shop == null){
            textUI.print("No shop here.");
            return;
        }
        Item held = player.getInventory().getItem(itemName);
        if(held == null){
            textUI.print("You do not have " + itemName + ".");
            return;
        }
        int price = held.getSellValue();
        player.getInventory().removeItem(itemName, 1);
        player.changeSpiritStones(price);
        textUI.print("Sold " + held.getName() + " for " + price + " stones.");
    }

    //Combat ----------------------------------------------------------------------------------------------------------
    private void fight(String target){
        Room room = player.getCurrentRoom();
        String enemyName = null;
        boolean roomEncounter = false;

        if(target.isEmpty()){
            if(room.getEnemyName() != null && !room.isCleared()){
                enemyName = room.getEnemyName();
                roomEncounter = true;
            }
        }else{
            if(room.getEnemyName() != null && !room.isCleared()
                    && room.getEnemyName().toLowerCase().contains(target.toLowerCase())){
                enemyName = room.getEnemyName();
                roomEncounter = true;
            }
            if(enemyName == null
                    && (target.equalsIgnoreCase("go ha-jin") || target.equalsIgnoreCase("ha-jin"))
                    && room.getRoomName().equals("Training Grounds")){
                Quest duel = questLog.get("duel");
                if(duel != null && duel.isActive()){
                    enemyName = "Go Ha-Jin";
                }
            }
        }

        if(enemyName == null){
            textUI.print("Nothing here wants to fight you. Give it time.");
            return;
        }

        Enemy enemy = story.createEnemy(enemyName);
        boolean won = combatController.fight(player, enemy);

        if(won){
            questLog.addProgress(QuestType.KILL, enemy.getName());
            questLog.addProgress(QuestType.DUEL, enemy.getName());
            if(roomEncounter){
                room.setCleared(true);
            }
            checkQuestCompletion();
        }else{
            handleDefeat();
        }
    }

    private void handleDefeat(){
        Stats stats = player.getStats();
        int lostXp = stats.getCurrentXP()/4;
        stats.loseXp(lostXp);
        player.changeSpiritStones(-(player.getSpiritStones()/2));
        stats.setCurrentHP(1);
        stats.recoverStamina();
        stats.recoverQi();
        stats.clearStatuses();
        player.setCurrentRoom(world.getRoom("Outer Quarters"));
        textUI.print("You wake in the Outer Quarters with a headache and a lighter purse.");
        textUI.print("Lost " + lostXp + " XP and half your spirit stones. The mountain is not sorry.");
    }

    //Quest completion ------------------------------------------------------------------------------------------------
    private void checkQuestCompletion(){
        for(Quest quest : questLog.getQuests()){
            if(!quest.isActive() || !quest.objectivesComplete()){
                continue;
            }

            //gathered items are handed over as part of finishing the job
            for(QuestObjective objective : quest.getObjectives()){
                if(objective.getType() == QuestType.FETCH){
                    player.getInventory().removeItem(objective.getTarget(), objective.getRequired());
                }
            }

            quest.complete();
            player.getStats().increaseXp(quest.getXpReward());
            player.changeSpiritStones(quest.getStoneReward());
            player.changeReputation("Mount Hua Sect", 5);
            for(String itemName : quest.getItemRewards()){
                giveItem(itemName);
            }
            textUI.questComplete(quest.getQuestName(), quest.getXpReward(), quest.getStoneReward());
            player.updateSectRank();
            questLog.refresh();

            if(quest.getId().equals("ashes")){
                textUI.print("The Azure Cloud Manual is warm in your hands. Type 'use azure cloud manual'.");
            }
            if(quest.getId().equals("gate")){
                Quest finale = questLog.get("finale");
                if(finale != null && finale.isAvailable()){
                    finale.start();
                    world.getRoom("Sect Gate").setCleared(false);
                    world.getRoom("Sect Gate").setEnemyName("Black Lotus Lieutenant");
                    textUI.print("A horn sounds at the gate. The Black Lotus has stopped pretending.");
                }
            }
            if(quest.getId().equals("finale")){
                textUI.banner("Act One ends");
                textUI.print("One mask down. The man who burned your village wears a better one.");
                textUI.print("You are still alive. In this genre, that counts as a win.");
                flags.add("act1done");
            }
        }
    }

    //Persistence -----------------------------------------------------------------------------------------------------
    private void save(){
        if(saveManager.save(player, questLog, flags, skillBox)){
            textUI.print("Saved to " + saveManager.getPath().toAbsolutePath());
        }else{
            textUI.print("Save failed. Even the heavens are unimpressed.");
        }
    }

    private void load(){
        if(saveManager.load(player, questLog, flags, world, skillBox)){
            textUI.print("Loaded.");
            textUI.commandLook(player);
        }else{
            textUI.print("No save found, or the save is broken. You are stuck with now.");
        }
    }
}

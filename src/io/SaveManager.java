package io;

import combat.Element;
import items.Item;
import items.ItemFactory;
import items.Weapon;
import Npc.Quest;
import Npc.QuestLog;
import Npc.QuestState;
import player.Player;
import player.RealmSystem;
import skills.SkillBox;
import world.World;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SaveManager {
    private final Path path;

    public SaveManager(String fileName){
        this.path = Paths.get(fileName);
    }

    public Path getPath(){
        return path;
    }

    public boolean save(Player player, QuestLog questLog, Set<String> flags, SkillBox skillBox){
        List<String> lines = new ArrayList<>();
        lines.add("version=2");
        lines.add("name=" + player.getName());
        lines.add("stones=" + player.getSpiritStones());
        lines.add("sectRank=" + player.getSectRank());
        lines.add("days=" + player.getDays());
        lines.add("affinity=" + player.getAffinity().name());
        lines.add("weapon=" + (player.getEquippedWeapon() == null ? "none" : player.getEquippedWeapon().getName()));
        lines.add("room=" + player.getCurrentRoom().getRoomName());

        lines.add("vitality=" + player.getStats().getVitality());
        lines.add("strength=" + player.getStats().getStrength());
        lines.add("agility=" + player.getStats().getAgility());
        lines.add("focus=" + player.getStats().getFocus());
        lines.add("stamina=" + player.getStats().getStamina());
        lines.add("points=" + player.getStats().getPoints());
        lines.add("xp=" + player.getStats().getCurrentXP());
        lines.add("realm=" + player.getStats().getRealmIndex());
        lines.add("stage=" + player.getStats().getCurrentStage());
        lines.add("hp=" + player.getStats().getCurrentHP());
        lines.add("currentStamina=" + player.getStats().getCurrentStamina());
        lines.add("qi=" + player.getStats().getCurrentQi());
        lines.add("momentum=" + player.getStats().getMomentum());

        for(String faction : player.getReputationMap().keySet()){
            lines.add("rep=" + faction + ":" + player.getReputation(faction));
        }
        for(int i = 0; i < skillBox.getSkillIndex().length; i++){
            if(skillBox.getSkillIndex()[i].getOwned()){
                lines.add("skill=" + i);
            }
        }
        for(Item item : player.getInventory().getItems()){
            lines.add("item=" + item.getName() + ":" + item.getQuantity());
        }
        for(Quest quest : questLog.getQuests()){
            if(quest.getState() != QuestState.LOCKED){
                lines.add("quest=" + quest.getId() + ":" + quest.getState().name());
            }
        }
        for(String flag : flags){
            lines.add("flag=" + flag);
        }

        try{
            Files.write(path, lines, StandardCharsets.UTF_8);
            return true;
        }catch(IOException ex){
            return false;
        }
    }

    public boolean load(Player player, QuestLog questLog, Set<String> flags, World world, SkillBox skillBox){
        if(!Files.exists(path)){
            return false;
        }
        try{
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            int vitality = player.getStats().getVitality();
            int strength = player.getStats().getStrength();
            int agility = player.getStats().getAgility();
            int focus = player.getStats().getFocus();
            int stamina = player.getStats().getStamina();
            int points = player.getStats().getPoints();
            int xp = player.getStats().getCurrentXP();
            int realmIndex = player.getStats().getRealmIndex();
            int stage = player.getStats().getCurrentStage();
            int hp = player.getStats().getCurrentHP();
            int currentStamina = player.getStats().getCurrentStamina();
            int qi = player.getStats().getCurrentQi();
            int momentum = player.getStats().getMomentum();
            Element affinity = player.getAffinity();
            String roomName = player.getCurrentRoom().getRoomName();
            String weaponName = "none";

            player.getInventory().getItems().clear();
            flags.clear();

            for(String line : lines){
                String[] parts = line.split("=", 2);
                if(parts.length < 2){
                    continue;
                }
                String key = parts[0];
                String value = parts[1];

                switch(key){
                    case "name": break;
                    case "stones": player.changeSpiritStones(Integer.parseInt(value) - player.getSpiritStones()); break;
                    case "sectRank": player.setSectRank(value); break;
                    case "days": player.addDays(Integer.parseInt(value) - player.getDays()); break;
                    case "affinity": affinity = Element.valueOf(value); break;
                    case "weapon": weaponName = value; break;
                    case "room": roomName = value; break;
                    case "vitality": vitality = Integer.parseInt(value); break;
                    case "strength": strength = Integer.parseInt(value); break;
                    case "agility": agility = Integer.parseInt(value); break;
                    case "focus": focus = Integer.parseInt(value); break;
                    case "stamina": stamina = Integer.parseInt(value); break;
                    case "points": points = Integer.parseInt(value); break;
                    case "xp": xp = Integer.parseInt(value); break;
                    case "realm": realmIndex = Integer.parseInt(value); break;
                    case "stage": stage = Integer.parseInt(value); break;
                    case "hp": hp = Integer.parseInt(value); break;
                    case "currentStamina": currentStamina = Integer.parseInt(value); break;
                    case "qi": qi = Integer.parseInt(value); break;
                    case "momentum": momentum = Integer.parseInt(value); break;
                    case "rep":
                        String[] rep = value.split(":", 2);
                        player.getReputationMap().put(rep[0], Integer.parseInt(rep[1]));
                        break;
                    case "skill":
                        skillBox.getSkillIndex()[Integer.parseInt(value)].setOwned(true);
                        break;
                    case "item":
                        String[] item = value.split(":", 2);
                        Item loaded = ItemFactory.create(item[0]);
                        loaded.setQuantity(Integer.parseInt(item[1]));
                        player.getInventory().addItem(loaded);
                        break;
                    case "quest":
                        String[] quest = value.split(":", 2);
                        Quest found = questLog.get(quest[0]);
                        if(found != null){
                            found.setState(QuestState.valueOf(quest[1]));
                        }
                        break;
                    case "flag":
                        flags.add(value);
                        break;
                    default: break;
                }
            }

            player.getStats().applyState(vitality, strength, agility, focus, stamina, points, xp,
                    RealmSystem.getREALMS()[realmIndex], stage, hp, currentStamina, qi, momentum, affinity);
            player.setAffinity(affinity);

            if(!weaponName.equalsIgnoreCase("none")){
                Item held = player.getInventory().getItem(weaponName);
                if(held instanceof Weapon){
                    player.setEquippedWeapon((Weapon) held);
                }
            }

            if(world.getRoom(roomName) != null){
                player.setCurrentRoom(world.getRoom(roomName));
            }
            questLog.refresh();
            return true;
        }catch(IOException | RuntimeException ex){
            return false;
        }
    }
}

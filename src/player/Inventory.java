package player;

import items.Fruit;
import items.Item;
import items.Weapon;

import java.util.ArrayList;
import java.util.Comparator;

public class Inventory {
    private ArrayList<Item> items;
    //makes and inventory aka a arrayList names items

    public Inventory() {
        items = new ArrayList<>();
        //makes inventory equal the arrayList
    }

    public void addItem(Item item) {
        //stack anything with the same name and class instead of adding clones
        for(Item existing : items){
            if(existing.getName().equalsIgnoreCase(item.getName()) && existing.getClass() == item.getClass()){
                existing.addQuantity(item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void addItem(String name, int quantity, String type, String description) {
        addItem(new Fruit(name, quantity, type, description));
    }

    public void addItem(String name, int quantity, int damage, String type, String description) {
        addItem(new Weapon(name, quantity, damage, type, description));
    }

    //removes a number of an item and deletes it when the stack runs out
    public boolean removeItem(String name, int quantity){
        Item item = getItem(name);
        if(item == null){
            return false;
        }
        item.setQuantity(item.getQuantity() - quantity);
        if(item.getQuantity() <= 0){
            items.remove(item);
        }
        return true;
    }

    public void removeItem(Item item){
        if(items.contains(item)){
            item.setQuantity(item.getQuantity() - 1);
            if(item.getQuantity() <= 0){
                items.remove(item);
            }
        }else{
            System.out.println("you do not have item "+item.getName()+"!");
        }
    }

    public Item getItem(String name){
        for(Item item : items){
            if(item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String name){
        return getItem(name) != null;
    }

    public int countItem(String name){
        Item item = getItem(name);
        return item == null ? 0 : item.getQuantity();
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public void displayInventory() {
        if(items.isEmpty()){
            System.out.println("Your pack is empty. Even the dust left.");
            return;
        }
        for (Item item : items) {
            item.displayInfo();
            //prints content of Inventory
        }
    }

    public void displayInventory(String type) {
        for (Item item : items) {
            if (item instanceof Fruit && ((Fruit) item).getType().equalsIgnoreCase(type)) {
                System.out.println(item.toString());
            } else if (item instanceof Weapon && ((Weapon) item).getType().equalsIgnoreCase(type)) {
                System.out.println(item.toString());
            }
        }
    }

    //kept for the inventory sorting exercise, now used only to keep the list tidy
    public int sortAndSearchName(String input){
        items.sort(Comparator.comparing(Item::getName));

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equalsIgnoreCase(input)){
                return i;
            }
        }
        return -1;
    }
}

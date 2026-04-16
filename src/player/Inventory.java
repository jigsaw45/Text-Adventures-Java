package player;

import items.Fruit;
import items.Item;
import items.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Inventory {
    private ArrayList<Item> items;
    //makes and inventory aka a arrayList names items

    public Inventory() {
        items = new ArrayList<>();
        //makes inventory equal the arrayList
    }

    public void addItem(Item item) {
        items.add(item);
        //adds item to arrayList
    }

    public void addItem(String name, int quantity, String type, String description) {
        items.add(new Fruit(name, quantity, type, description));
    }

    public void addItem(String name, int quantity, int damage, String type, String description) {
        items.add(new Weapon(name, quantity, damage, type, description));
    }

    public void displayInventory() {
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
    public int sortAndSearchName(String input){
        items.sort(Comparator.comparing(Item::getName));

        int low = 0;
        int high = items.size() - 1;

        while (low <= high) {
            int middle = (low + high) / 2;

            int compare = items.get(middle).getName().compareTo(input);

            if (compare == 0)
                return middle;
            else if (compare > 0)
                high = middle - 1;
            else
                low = middle + 1;
        }
        return -1;
    }
}


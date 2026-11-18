package world;

import items.Item;

import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<Item> stock;

    public Shop(String name){
        this.name = name;
        this.stock = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void addStock(Item item){
        stock.add(item);
    }

    public ArrayList<Item> getStock(){
        return stock;
    }

    public void displayStock(){
        System.out.println("--- " + name + " ---");
        for(Item item : stock){
            System.out.println(item.getName() + " - " + item.getValue() + " stones");
        }
    }

    public Item findStock(String itemName){
        for(Item item : stock){
            if(item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null;
    }

    //hands over a copy and keeps one in the shop
    public Item buy(String itemName){
        return findStock(itemName);
    }
}

import java.util.ArrayList;


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

        public void addItem(String name, int quantity, String type){
            items.add(new Fruit(name,quantity,type));
        }

        public void addItem(String name, int quantity, int damage, String type) {
            items.add(new Weapon(name, quantity, damage, type));
        }

        public void displayInventory() {
            for (Item item : items) {
                item.displayInfo();
                //prints content of Inventory
            }
        }

        public void displayInventory(String type){
            for (Item item : items) {
                if (item instanceof Fruit && ((Fruit) item).getType().equalsIgnoreCase(type)) {
                    System.out.println(item.toString());
                }
                else if (item instanceof Weapon && ((Weapon) item).getType().equalsIgnoreCase(type)) {
                    System.out.println(item.toString());
                }

            }
        }
        //this makes it so if type is in displayInventory it prints
    }


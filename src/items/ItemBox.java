package items;

public class ItemBox {
    private Item[] itemBox;

    public ItemBox(Item[] itemBox){
        this.itemBox = itemBox;
    }

    public Item[] getItemBox(){
        return itemBox;
    }

    public Item getItem(int index){
        return itemBox[index];
    }
}

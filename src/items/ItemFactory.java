package items;

public class ItemFactory {

    //every item in the game lives here so shops, quests and save files agree on what it is
    public static Item create(String name){
        switch(name){
            case "Moonpetal Herb":
                return new Fruit("Moonpetal Herb",1,"herb","A pale herb that only blooms when nobody is watching.",8);
            case "Spirit Ginseng":
                return new Fruit("Spirit Ginseng",1,"herb","Older than your grandfather. Probably tastes like him too.",20);
            case "Bamboo Shoot":
                return new Fruit("Bamboo Shoot",1,"food","Crunchy, nutritious, and deeply humiliating to eat raw.",5);
            case "Qi Gathering Pill":
                return new Pill("Qi Gathering Pill",1,40,PillType.CULTIVATION,"Tastes like chalk and ambition.",30);
            case "Meridian Cleansing Pill":
                return new Pill("Meridian Cleansing Pill",1,120,PillType.CULTIVATION,"Your meridians will thank you. Loudly.",90);
            case "Breakthrough Pill":
                return new Pill("Breakthrough Pill",1,0,PillType.BREAKTHROUGH,"Required to cross a realm. Do not chew.",200);
            case "Healing Salve":
                return new Pill("Healing Salve",1,60,PillType.HEALING,"Smells of pine and bad decisions.",25);
            case "Blood Restoring Pill":
                return new Pill("Blood Restoring Pill",1,150,PillType.HEALING,"Restores blood. Adds none of the moral kind.",70);
            case "Rage Powder":
                return new Pill("Rage Powder",1,10,PillType.COMBAT,"Inhale before a fight. Regret immediately after.",40);
            case "Rusted Saber":
                return new Weapon("Rusted Saber",1,6,"saber","Held together by rust and spite.",15);
            case "Tang Steel Dagger":
                return new Weapon("Tang Steel Dagger",1,12,"dagger","Poisoned? Only if you ask nicely.",60);
            case "Mount Hua Straight Sword":
                return new Weapon("Mount Hua Straight Sword",1,18,"sword","Standard issue for people who matter.",150);
            case "Black Lotus Token":
                return new Token("Black Lotus Token",1,"token","A black lotus stamped into iron. Bad news, decent weight.",0);
            case "Old Letter":
                return new Token("Old Letter",1,"letter","Half burned. Naturally, the signature is the burned half.",0);
            case "Azure Cloud Manual":
                return new Manual("Azure Cloud Manual",-1,"A manual you cannot read, yet somehow understand perfectly.",0);
            default:
                return new Fruit(name,1,"misc","An item of unclear purpose. Suspiciously light.",0);
        }
    }
}

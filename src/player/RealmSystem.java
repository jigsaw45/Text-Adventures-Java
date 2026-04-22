package player;

import skills.Skill;

public class RealmSystem {

    private static final Realm[] REALMS = new Realm[18];

    static{
        REALMS[0] = new Realm("Qi refining",9);
        REALMS[1] = new Realm("Foundation establishment",9);
        REALMS[2] = new Realm("Core Formation",9);
        REALMS[3] = new Realm("Golden Core",9);
        REALMS[4] = new Realm("Nascent Soul",9);
        REALMS[5] = new Realm("Spirit Transformation",9);
        REALMS[6] = new Realm("Void Refining",9);
        REALMS[7] = new Realm("Body Fusion",9);
        REALMS[8] = new Realm("Mahayana",9);
        REALMS[9] = new Realm("Half Immortal",9);
        REALMS[10] = new Realm("Human Immortal",9);
        REALMS[11] = new Realm("Earth Immortal",9);
        REALMS[12] = new Realm("Heavenly Immortal",9);
        REALMS[13] = new Realm("Golden Immortal",9);
        REALMS[14] = new Realm("Celestial Immortal",9);
        REALMS[15] = new Realm("Celestial Immortal Emperor",9);
        REALMS[16] = new Realm("Dao Venerable",9);
        REALMS[17] = new Realm("Dao Emperor",5);
    }

    public static Realm[] getREALMS() {
        return REALMS;
    }
    public int findRealmIndex(Realm realm){
        for(int i = 0;i<18;i++){
            if(realm.getName().equalsIgnoreCase(REALMS[i].getName())){
                return i;
            }
        }
        return -1;
    }
}

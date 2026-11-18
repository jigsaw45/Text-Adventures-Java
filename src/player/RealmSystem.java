package player;

import skills.Skill;

public class RealmSystem {

    private static final Realm[] REALMS = new Realm[9];

    static{
        REALMS[0] = new Realm("Qi Refining",9);
        REALMS[1] = new Realm("Foundation Establishment",9);
        REALMS[2] = new Realm("Core Formation",9);
        REALMS[3] = new Realm("Nascent Soul",9);
        REALMS[4] = new Realm("Spirit Transformation",9);
        REALMS[5] = new Realm("Void Refining",9);
        REALMS[6] = new Realm("Body Integration",9);
        REALMS[7] = new Realm("Mahayana",9);
        REALMS[8] = new Realm("Immortal Ascension",9);
    }

    public static Realm[] getREALMS() {
        return REALMS;
    }
    //major realms where the heavens bother to take an interest
    public boolean isMajorRealm(Realm realm){
        int index = findRealmIndex(realm);
        return index == 1 || index == 3 || index == 5 || index == 7;
    }
    public int findRealmIndex(Realm realm){
        for(int i = 0;i<REALMS.length;i++){
            if(realm.getName().equalsIgnoreCase(REALMS[i].getName())){
                return i;
            }
        }
        return 0;
    }
}

package com.ucb.appin.data.realmDb;

import com.ucb.appin.data.model.AvisosBean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by tecnosim on 1/27/2018.
 */

public class Parent extends RealmObject {
    @SuppressWarnings("unused")
    private RealmList<AvisosBean> itemList;

    public RealmList<AvisosBean> getItemList() {
        return itemList;
    }

     //se puede crear mas objetos de tuipo realm si se necesita

}

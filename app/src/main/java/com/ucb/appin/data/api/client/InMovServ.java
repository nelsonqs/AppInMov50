package com.ucb.appin.data.api.client;

import com.ucb.appin.data.model.AvisosBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by tecnosim on 1/29/2018.
 */

public interface InMovServ {
    void  addAdviceAsync (Realm realm, AvisosBean avisosBean, int sourceRequired);

    void editAdviceAsync(Realm srealm, AvisosBean avisosBean, int localLocation);

    List<AvisosBean> getAllAdvicesAsync(Realm srealm, String tbuscar, int localLocation);
}

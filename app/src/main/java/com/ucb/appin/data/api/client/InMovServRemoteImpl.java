package com.ucb.appin.data.api.client;

import com.ucb.appin.data.model.AvisosBean;

import java.util.List;

import io.realm.Realm;

/**
 * Created by tecnosim on 1/29/2018.
 */

public class InMovServRemoteImpl implements InMovServ {
    @Override
    public void addAdviceAsync(Realm realm, AvisosBean avisosBean, int sourceRequired) {
        //Actualmente no esta implementado, ya va !!!.
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void editAdviceAsync(Realm srealm, AvisosBean avisosBean, int localLocation) {

    }

    @Override
    public List<AvisosBean> getAllAdvicesAsync(Realm srealm, String tbuscar, int localLocation) {
        return null;
    }
}

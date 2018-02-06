package com.ucb.appin.data.api.client;

import android.util.Log;

import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.util.ConstantAppInVirtual;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by tecnosim on 1/29/2018.
 */

public class InMovServLocalImpl implements InMovServ {
    @Override
    public void addAdviceAsync(Realm realm, final AvisosBean avisosBean, int sourceRequired) {
       if (sourceRequired == ConstantAppInVirtual.LOCAL_LOCATION) {
           realm.executeTransactionAsync(new Realm.Transaction() {
               @Override
               public void execute(Realm realm) {
                   AvisosBean.createAdvices(realm, avisosBean);
               }
           });
       }
    }

    @Override
    public void editAdviceAsync(Realm srealm, final AvisosBean avisosBean, int localLocation) {
        if (localLocation == ConstantAppInVirtual.LOCAL_LOCATION) {
            srealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    AvisosBean.editAdvices(realm, avisosBean);
                }
            });
        }
    }

    @Override
    public List<AvisosBean> getAllAdvicesAsync(Realm srealm, final String tbuscar, int localLocation) {
        final List<AvisosBean> avisos = new ArrayList<AvisosBean>();
        if (localLocation == ConstantAppInVirtual.LOCAL_LOCATION) {
            srealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    avisos.addAll(AvisosBean.listAllAdvices(realm, tbuscar));
                }
            });
        }
        Log.v(">>>",">>SERVICIO>>" + avisos.size());
        return avisos;
    }
}

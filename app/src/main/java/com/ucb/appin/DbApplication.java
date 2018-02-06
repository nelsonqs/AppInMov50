package com.ucb.appin;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Juan choque on 10/14/2017.
 */

public class DbApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("dcompras.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        //Realm.deleteRealm(configuration); // Borrado si la app reinicia.
        Realm.setDefaultConfiguration(configuration);

        /*
        * Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createObject(Parent.class);
                    }})
                .build();
        Realm.deleteRealm(realmConfig); // Borrado si la app reinicia.
        Realm.setDefaultConfiguration(realmConfig);

        *
        * */
    }
}

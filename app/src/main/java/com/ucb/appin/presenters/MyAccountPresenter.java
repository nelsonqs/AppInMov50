package com.ucb.appin.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ucb.appin.data.model.CuentasBean;
import com.ucb.appin.util.UtilInmo;
import com.ucb.appin.views.IAdminAccountView;

import io.realm.Realm;

/**
 * Created by Juan choque on 1/27/2018.
 */

public class MyAccountPresenter implements IMyAccountPresenter {
    private Context context;
    private IAdminAccountView iAdminAccountView;
    private UserAccountTask mAuthTask = null;

    public MyAccountPresenter(Context context, IAdminAccountView iAdminAccountView) {
        this.context = context;
        this.iAdminAccountView = iAdminAccountView;
    }

    @Override
    public void addAccount(CuentasBean cuentasBean) {
        iAdminAccountView.showProgress(true);
        mAuthTask = new UserAccountTask(cuentasBean, 1);
        mAuthTask.execute((Void) null);
    }

    @Override
    public void getAccount() {
        UtilInmo utilInmo = new UtilInmo(context);
        String imei = utilInmo.getNumberPhone();

        CuentasBean cuentasBean = new CuentasBean();
        cuentasBean.setTelefono(imei);

        iAdminAccountView.showProgress(true);
        mAuthTask = new UserAccountTask(cuentasBean, 0);
        mAuthTask.execute((Void) null);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class UserAccountTask extends AsyncTask<Void, Void, Boolean> {
        private Realm realm;

        private CuentasBean cuentasBean;
        private int operation = 0;
        private CuentasBean rCuentasBean;
        private CuentasBean nCuentasBean = null;

        public UserAccountTask(CuentasBean cuentasBean, int operation) {
            this.cuentasBean = cuentasBean;
            this.operation = operation;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            realm = Realm.getDefaultInstance();

            try {
                realm.beginTransaction();
                if(operation == 0){
                    rCuentasBean = null;
                    try {
                        rCuentasBean = realm.where(CuentasBean.class)
                                .equalTo("telefono",this.cuentasBean.getTelefono())
                                .findFirst();

                        if(rCuentasBean != null){
                            nCuentasBean = new CuentasBean();
                            nCuentasBean.setId(rCuentasBean.getId());
                            nCuentasBean.setNombres(rCuentasBean.getNombres());
                            nCuentasBean.setApaterno(rCuentasBean.getApaterno());
                            nCuentasBean.setAmaterno(rCuentasBean.getAmaterno());
                        }

                    }catch (Exception e) {
                        rCuentasBean = null;
                    }
                }
                else if(operation == 1){
                    try {
                        realm.copyToRealmOrUpdate(this.cuentasBean);
                        Log.v(">>>>",">>REGISTRO CORRECTO>>");
                    }catch (Exception e) {
                        Log.v(">>>>",">>ERROR>>" + e.getMessage());
                    }

                }
               realm.commitTransaction();
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            iAdminAccountView.showProgress(false);

            if (success) {
                if(operation == 1){
                    iAdminAccountView.finish();
                }
                else if(operation == 0){

                    iAdminAccountView.loadData(nCuentasBean);
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            iAdminAccountView.showProgress(false);
        }
    }
}

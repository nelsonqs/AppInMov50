package com.ucb.appin.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ucb.appin.data.api.client.InMovServ;
import com.ucb.appin.data.api.client.InMovServLocalImpl;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.data.model.TipoAvisoBean;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.util.ConstantAppInVirtual;
import com.ucb.appin.util.UtilInmo;
import com.ucb.appin.views.IAddMyPublicationsView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Juan choque on 1/27/2018.
 */

public class AddMyPublicationsPresenter implements IAddMyPublicationsPresenter {
    private Context context;
    private IAddMyPublicationsView iAddMyPublicationsView;

    private Realm realm;
    private AddMyPublicatonsTask mAuthTask = null;
    private InMovServ inMovServ;

    public AddMyPublicationsPresenter(Context context, IAddMyPublicationsView iAddMyPublicationsView) {
        this.context = context;
        this.iAddMyPublicationsView = iAddMyPublicationsView;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public AvisosBean addAviso(AvisosBean avisosBean) {
        try {
            if(avisosBean != null){
                inMovServ = new InMovServLocalImpl();
                //realm = Realm.getDefaultInstance();

                UtilInmo utilInmo = new UtilInmo(context);
                String imei = utilInmo.getNumberPhone();
                avisosBean.setTelefono(imei);

                iAddMyPublicationsView.showProgress(true);
                mAuthTask = new AddMyPublicatonsTask(avisosBean,inMovServ);
                mAuthTask.execute((Void) null);
            }
        }catch (Exception er){
            Log.i("---ERROR--->",">>" + er.getMessage());
            avisosBean = null;
        }
        return avisosBean;
    }

    @Override
    public void listTipoAvisos() {
        List<TipoAvisoBean> listTipoAvisos = new ArrayList<TipoAvisoBean>();

        TipoAvisoBean tipoAvisoBean = new TipoAvisoBean();
        tipoAvisoBean.setId(1);
        tipoAvisoBean.setNombre("Casa/Chalet");

        TipoAvisoBean tipoAvisoBean0 = new TipoAvisoBean();
        tipoAvisoBean0.setId(2);
        tipoAvisoBean0.setNombre("Departamento");

        TipoAvisoBean tipoAvisoBean1 = new TipoAvisoBean();
        tipoAvisoBean1.setId(3);
        tipoAvisoBean1.setNombre("Habitacion");

        TipoAvisoBean tipoAvisoBean2 = new TipoAvisoBean();
        tipoAvisoBean2.setId(4);
        tipoAvisoBean2.setNombre("Local Comercial/Tienda");

        TipoAvisoBean tipoAvisoBean3 = new TipoAvisoBean();
        tipoAvisoBean3.setId(5);
        tipoAvisoBean3.setNombre("Otros");

        listTipoAvisos.add(tipoAvisoBean);
        listTipoAvisos.add(tipoAvisoBean0);
        listTipoAvisos.add(tipoAvisoBean1);
        listTipoAvisos.add(tipoAvisoBean2);
        listTipoAvisos.add(tipoAvisoBean3);

        this.iAddMyPublicationsView.loadInitDataTipoAviso(listTipoAvisos);
    }

    @Override
    public void listTransaccionAvisos() {
        List<TransaccionAviso>listTransaccionAvisos = new ArrayList<TransaccionAviso>();

        TransaccionAviso transaccionAviso = new TransaccionAviso();
        transaccionAviso.setId(1);
        transaccionAviso.setNombre("Venta");

        TransaccionAviso transaccionAviso0 = new TransaccionAviso();
        transaccionAviso0.setId(1);
        transaccionAviso0.setNombre("Alquiler");

        TransaccionAviso transaccionAviso1 = new TransaccionAviso();
        transaccionAviso1.setId(1);
        transaccionAviso1.setNombre("Anticretico");

        TransaccionAviso transaccionAviso2 = new TransaccionAviso();
        transaccionAviso2.setId(1);
        transaccionAviso2.setNombre("Otros");

        listTransaccionAvisos.add(transaccionAviso);
        listTransaccionAvisos.add(transaccionAviso0);
        listTransaccionAvisos.add(transaccionAviso1);
        listTransaccionAvisos.add(transaccionAviso2);

        this.iAddMyPublicationsView.loadInitDataTransaccionAviso(listTransaccionAvisos);
    }

    @Override
    public int getMaxId() {
        int result = 0;
        try {
            Number aux = realm.where(AvisosBean.class)
                    .max("id");
            if(aux != null){
                result = aux.intValue();
            }
            Log.i("---CORRECTO HILO--->",">>");
        }catch (Exception er){
            Log.i("---ERROR HILO--->",">>" + er.getMessage());
            result = 0;
        }
        return result;
    }

    @Override
    public void editAviso(AvisosBean avisosBean) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class AddMyPublicatonsTask extends AsyncTask<Void, Void, Boolean> {
        private AvisosBean avisosBean;
        private Realm srealm;
        private InMovServ inMovServ;

        AddMyPublicatonsTask(AvisosBean avisosBean, InMovServ inMovServLocal){
            this.avisosBean = avisosBean;
            this.inMovServ = inMovServLocal;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            srealm = Realm.getDefaultInstance();
            try {
                /*if(avisosBean.getId() == 0){
                    this.inMovServ.addAdviceAsync(srealm,avisosBean,ConstantAppInVirtual.LOCAL_LOCATION);
                }
                else{
                    //edit
                    this.inMovServ.editAdviceAsync(srealm,avisosBean,ConstantAppInVirtual.LOCAL_LOCATION);
                }*/
                srealm.beginTransaction();
                srealm.copyToRealmOrUpdate(avisosBean);
                srealm.commitTransaction();


                Log.i("---CORRECTO--->",">>");
            }catch (Exception er){
                Log.i("---ERROR--->",">>" + er.getMessage());
                avisosBean = null;
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            iAddMyPublicationsView.showProgress(false);

            if (success) {
                iAddMyPublicationsView.showMessage(ConstantAppInVirtual.INCORRECT_OPERATION);
                iAddMyPublicationsView.finish();
            }
            else{
                iAddMyPublicationsView.showMessage(ConstantAppInVirtual.CORRECT_OPERATION);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            iAddMyPublicationsView.showProgress(false);
        }
    }
}

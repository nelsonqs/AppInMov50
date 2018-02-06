package com.ucb.appin.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ucb.appin.data.api.client.InMovServ;
import com.ucb.appin.data.api.client.InMovServLocalImpl;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.presenters.adapters.MyPublicationsAdapter;
import com.ucb.appin.util.ConstantAppInVirtual;
import com.ucb.appin.views.IMyPublicationsView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Juan choque on 1/27/2018.
 */

public class MyPublicationsPresenter implements IMyPublicationsPresenter {
    private Context context;
    private IMyPublicationsView iMyPublicationsView;

    private Realm realm;
    private MyPublicatonsTask mAuthTask = null;
    private InMovServ inMovServ;

    public MyPublicationsPresenter(Context context, IMyPublicationsView iMyPublicationsView) {
        this.context = context;
        this.iMyPublicationsView = iMyPublicationsView;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public void listAvisos(String tBuscar) {
        try {
            /*List<AvisosBean> rListAvisos = new ArrayList<AvisosBean>();

            RealmResults<AvisosBean> avisos = realm.where(AvisosBean.class).findAll();

            Log.v(">>>>",">>>ANTES SIN HILO>>>" + avisos.size());

            if(avisos != null){
                for(int i = 0;i < avisos.size();i++){
                    AvisosBean rAvisosBean = avisos.get(i);
                    AvisosBean avisosBean = new AvisosBean();
                    avisosBean.setId(rAvisosBean.getId());
                    avisosBean.setTitulo(rAvisosBean.getTitulo());
                    avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                    avisosBean.setPrecio(rAvisosBean.getPrecio());
                    avisosBean.setTelefono(rAvisosBean.getTelefono());
                    avisosBean.setDireccion(rAvisosBean.getDireccion());
                    avisosBean.setLatitud(rAvisosBean.getLatitud());
                    avisosBean.setLongitud(rAvisosBean.getLongitud());
                    avisosBean.setTipoAviso(rAvisosBean.getTipoAviso());
                    avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionAviso());
                    avisosBean.setImagen(rAvisosBean.getImagen());

                    rListAvisos.add(avisosBean);
                }
            }

            Log.v(">>>>",">>>FIN SIN HILO>>>" + rListAvisos.size());

            iMyPublicationsView.loadData(rListAvisos);*/

            //inMovServ = new InMovServLocalImpl();

            //List<AvisosBean> rListAvisos = this.inMovServ.getAllAdvicesAsync(realm, tBuscar, ConstantAppInVirtual.LOCAL_LOCATION);
            //iMyPublicationsView.loadData(rListAvisos);
            Log.v(">>>>>>>>>>>>>",">>entra en lista>>>");
            iMyPublicationsView.showProgress(true);
            mAuthTask = new MyPublicatonsTask(tBuscar, inMovServ, this);
            mAuthTask.execute((Void) null);
            Log.v(">>>>>>>>>>>>>",">>al final entra en lista>>>");
        }catch (Exception er){
            Log.i("---ERROR--->",">>" + er.getMessage());
        }
    }



    public void listAvisos(List<AvisosBean>rListAvisos) {
        try {
            Log.i("------>",">ENTRA EN LISTA  PRESENTER>");
            iMyPublicationsView.loadData(rListAvisos);
        }catch (Exception er){
            Log.i("---ERROR--->",">>" + er.getMessage());
        }
    }



    @Override
    public void confirmDeleteAviso(AvisosBean avisosBean) {
        this.iMyPublicationsView.showDialogDelete(avisosBean);
    }

    @Override
    public AvisosBean deleteAviso(AvisosBean avisosBean) {
        try {
            AvisosBean rAvisosBean = realm.where(AvisosBean.class)
                    .equalTo("id",avisosBean.getId())
                    .findFirst();

            if(rAvisosBean != null){
                realm.beginTransaction();
                rAvisosBean.deleteFromRealm();
                realm.commitTransaction();
            }

            this.listAvisos("");
        }catch (Exception e){

        }
        return avisosBean;
    }

    @Override
    public void showEditAviso(AvisosBean avisosBean) {
        this.iMyPublicationsView.showEditAviso(avisosBean);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class MyPublicatonsTask extends AsyncTask<Void, Void, Boolean> {
        private List<AvisosBean>rListAvisos;
        private Realm srealm;
        private String tbuscar;
        private InMovServ inMovServ;

        private MyPublicationsPresenter myPublicationsPresenter;

        MyPublicatonsTask(String tbuscar, InMovServ inMovServLocal, MyPublicationsPresenter myPublicationsPresenter) {
            rListAvisos = new ArrayList<AvisosBean>();
            this.tbuscar = tbuscar;
            this.inMovServ = inMovServLocal;
            this.myPublicationsPresenter = myPublicationsPresenter;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            srealm = Realm.getDefaultInstance();
            srealm.beginTransaction();
            try {
                //rListAvisos = this.inMovServ.getAllAdvicesAsync(srealm, tbuscar, ConstantAppInVirtual.LOCAL_LOCATION);
                rListAvisos = new ArrayList<AvisosBean>();

                RealmResults<AvisosBean> avisos = srealm.where(AvisosBean.class).findAll();

                Log.v(">>>>",">>>ANTES>>>" + avisos.size());

                if(avisos != null){
                    for(int i = 0;i < avisos.size();i++){
                        AvisosBean rAvisosBean = avisos.get(i);
                        AvisosBean avisosBean = new AvisosBean();
                        avisosBean.setId(rAvisosBean.getId());
                        avisosBean.setTitulo(rAvisosBean.getTitulo());
                        avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                        avisosBean.setPrecio(rAvisosBean.getPrecio());
                        avisosBean.setTelefono(rAvisosBean.getTelefono());
                        avisosBean.setDireccion(rAvisosBean.getDireccion());
                        avisosBean.setLatitud(rAvisosBean.getLatitud());
                        avisosBean.setLongitud(rAvisosBean.getLongitud());
                        avisosBean.setTipoAviso(rAvisosBean.getTipoAviso());
                        avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionAviso());
                        avisosBean.setImagen(rAvisosBean.getImagen());

                        rListAvisos.add(avisosBean);
                    }
                }

                srealm.commitTransaction();
                Log.v("=====>",">>C AVISOS>>" + rListAvisos.size());
            }catch (Exception er){
                Log.v("=====>",">ERROR>" + er.getMessage());
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            iMyPublicationsView.showProgress(false);
            Log.v("=>>>>>",success + "<<<>>>" + rListAvisos.size());
            if (success) {
                myPublicationsPresenter.listAvisos(rListAvisos);
                iMyPublicationsView.finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            iMyPublicationsView.showProgress(false);
        }
    }
}

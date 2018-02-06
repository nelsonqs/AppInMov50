package com.ucb.appin.views;

import com.ucb.appin.data.model.AvisosBean;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyPublicationsView {
    void loadData(List<AvisosBean> listAvisos);
    void showDialogDelete(AvisosBean avisosBean);
    void showEditAviso(AvisosBean avisosBean);

    void showProgress(boolean b);

    void showMessage(String incorrectOperation);

    void finish();

    void reloadData();
}

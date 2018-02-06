package com.ucb.appin.presenters;

import com.ucb.appin.data.model.AvisosBean;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyPublicationsPresenter {
    void listAvisos(String tBuscar);
    void confirmDeleteAviso(AvisosBean avisosBean);
    AvisosBean deleteAviso(AvisosBean avisosBean);
    void showEditAviso(AvisosBean avisosBean);
}

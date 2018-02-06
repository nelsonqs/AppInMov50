package com.ucb.appin.presenters;

import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.data.model.TipoAvisoBean;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IAddMyPublicationsPresenter {
    AvisosBean addAviso(AvisosBean avisosBean);
    void listTipoAvisos();
    void listTransaccionAvisos();
    int getMaxId();

    void editAviso(AvisosBean avisosBean);
}

package com.ucb.appin.views;

import com.ucb.appin.data.model.TipoAvisoBean;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IAddMyPublicationsView {
    void showProgress(final boolean show);
    void showMessage(String message);
    void loadInitDataTipoAviso(List<TipoAvisoBean>listTipoAvisos);
    void loadInitDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos);
    void finish();
}

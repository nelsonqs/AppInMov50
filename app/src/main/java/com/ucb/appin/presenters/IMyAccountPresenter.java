package com.ucb.appin.presenters;

import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.data.model.CuentasBean;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyAccountPresenter {
    void addAccount(CuentasBean cuentasBean);

    void getAccount();

}

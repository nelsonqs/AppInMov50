package com.ucb.appin.views;

import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.data.model.CuentasBean;

import java.util.List;

/**
 * Created by Juan choque on 2/4/2018.
 */

public interface IAdminAccountView {
    void loadData(CuentasBean cuentasBean);

    void showProgress(boolean b);

    void finish();
}

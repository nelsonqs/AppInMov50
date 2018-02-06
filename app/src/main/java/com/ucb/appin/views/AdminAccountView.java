package com.ucb.appin.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ucb.appin.R;
import com.ucb.appin.data.model.CuentasBean;
import com.ucb.appin.presenters.IMyAccountPresenter;
import com.ucb.appin.presenters.MyAccountPresenter;
import com.ucb.appin.util.UtilInmo;

public class AdminAccountView extends AppCompatActivity implements IAdminAccountView {

    private IMyAccountPresenter iMyAccountPresenter;

    private View mProgressView;
    private View mAccountFormView;

    private EditText txtNombre;
    private EditText txtApaterno;
    private EditText txtAmaterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        txtNombre = (EditText) findViewById(R.id.txt_acc_nombre);
        txtApaterno = (EditText) findViewById(R.id.txt_acc_apaterno);
        txtAmaterno = (EditText) findViewById(R.id.txt_acc_amaterno);

        Button btnAdminAccount = (Button) findViewById(R.id.btn_admin_account);
        btnAdminAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });

        mAccountFormView = findViewById(R.id.account_form);
        mProgressView = findViewById(R.id.account_progress);

        this.iMyAccountPresenter = new MyAccountPresenter(this, this);
        this.iMyAccountPresenter.getAccount();//recupera datos de la cuenta

    }

    private void updateAccount() {

        String nombre = txtNombre.getText().toString();
        String apaterno = txtApaterno.getText().toString();
        String amaterno = txtAmaterno.getText().toString();

        if(validateData(nombre, apaterno, amaterno)){
            UtilInmo utilInmo = new UtilInmo(this);
            String imei = utilInmo.getNumberPhone();

            CuentasBean cuentasBean = new CuentasBean();
            cuentasBean.setId((long)1);
            cuentasBean.setNombres(nombre);
            cuentasBean.setAmaterno(amaterno);
            cuentasBean.setApaterno(apaterno);
            cuentasBean.setTelefono(imei);

            this.iMyAccountPresenter.addAccount(cuentasBean);
        }
    }

    @Override
    public void loadData(CuentasBean cuentasBean) {
        Log.v(">>>>",">cuentasBean>" + cuentasBean);
        if(cuentasBean != null){
            txtAmaterno.setText(cuentasBean.getAmaterno());
            txtApaterno.setText(cuentasBean.getApaterno());
            txtNombre.setText(cuentasBean.getNombres());

        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAccountFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean validateData(String nombre, String apaterno, String amaterno) {
        boolean result = true;
        if(nombre.isEmpty() || nombre.length() > 250){
            result = false;
            txtNombre.setError("Nombre Incorrecto");
        }
        if(apaterno != null){
            if(apaterno.length() > 250){
                result = false;
                txtNombre.setError("Primer Apellido Incorrecto");
            }
        }
        if(amaterno != null){
            if(amaterno.length() > 250){
                result = false;
                txtNombre.setError("Segundo Apellido Incorrecto");
            }
        }

        return result;
    }

}


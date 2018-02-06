package com.ucb.appin.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.views.fragment.FragmentPublicationDetail;

public class ActivityPublicationDetail extends AppCompatActivity {
    private String subject;
    private String message;
    private String sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication_detail);

        if (getIntent().getExtras() != null) {
            subject = getIntent().getStringExtra("subject");
            message = getIntent().getStringExtra("message");
            sender = getIntent().getStringExtra("senderName");
        }

        AvisosBean avisosBean = new AvisosBean();
        avisosBean.setTitulo(subject);
        avisosBean.setDescripcion(message);
        avisosBean.setDireccion(sender);

        FragmentPublicationDetail detailsFragment = (FragmentPublicationDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentDetailsMail);
        detailsFragment.renderMail(avisosBean);
    }
}

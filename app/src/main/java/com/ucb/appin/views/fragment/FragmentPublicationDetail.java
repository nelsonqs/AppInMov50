package com.ucb.appin.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPublicationDetail extends Fragment {
    private TextView textViewSubject;
    private TextView textViewSender;
    private TextView textViewMessage;
    private LinearLayout wrapper;


    public FragmentPublicationDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publication_detail, container, false);

        textViewSubject = (TextView) view.findViewById(R.id.textViewFragmentSubject);
        textViewSender = (TextView) view.findViewById(R.id.textViewFragmentSenderName);
        textViewMessage = (TextView) view.findViewById(R.id.textViewFragmentMessage);
        wrapper = (LinearLayout) view.findViewById(R.id.fragmentDetailsMailWrapper);
        return view;

    }

    public void renderMail(AvisosBean avisosBean) {
        wrapper.setVisibility(View.VISIBLE);
        textViewSubject.setText(avisosBean.getTitulo());
        textViewSender.setText(avisosBean.getDireccion());
        textViewMessage.setText(avisosBean.getDescripcion());
    }

}

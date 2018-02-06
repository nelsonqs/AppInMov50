package com.ucb.appin.views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.presenters.adapters.MailAdapter;
import com.ucb.appin.util.UtilInVirtual;

import java.util.List;

import io.realm.Realm;

public class PublicationsView extends Fragment {
    private View view;
    private Realm realm;
    private ListView listView;
    private List<AvisosBean> avisosBeans;
    private MailAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public PublicationsView() {
        // Required empty public constructor
    }

    public static PublicationsView newInstance(String param1, String param2) {
        PublicationsView fragment = new PublicationsView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_publications_view, container, false);
        realm = Realm.getDefaultInstance();
        listView = (ListView) view.findViewById(R.id.listViewMails);
        avisosBeans = realm.where(AvisosBean.class).findAll();
        if (avisosBeans.size()==0){
            avisosBeans = UtilInVirtual.getDummyData();
        }
        adapter = new MailAdapter(this.getActivity().getApplicationContext(), R.layout.list_view_mail, avisosBeans);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mListener.onListClick(avisosBeans.get(position));
            }
        });
        return view;
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onListClick(AvisosBean mail);
    }


}

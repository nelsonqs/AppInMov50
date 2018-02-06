package com.ucb.appin.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.presenters.IMyPublicationsPresenter;
import com.ucb.appin.presenters.MyPublicationsPresenter;
import com.ucb.appin.presenters.adapters.MyPublicationsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyPublicationsView extends Fragment implements IMyPublicationsView{

    private OnFragmentInteractionListener mListener;
    private View view;
    private IMyPublicationsPresenter iMyPublicationsPresenter;

    @BindView(R.id.l_my_publications)
    RecyclerView recycler;

    @BindView(R.id.add_progress)
    View mProgressView;

    public MyPublicationsView() {
        // Required empty public constructor
    }

    public static MyPublicationsView newInstance(String param1, String param2) {
        MyPublicationsView fragment = new MyPublicationsView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_my_publications_view, container, false);

        this.recycler = this.view.findViewById(R.id.l_my_publications);
        this.mProgressView = this.view.findViewById(R.id.list_my_progress);

        this.iMyPublicationsPresenter = new MyPublicationsPresenter(this.getActivity().getApplicationContext(),this);
        reloadData();

        return this.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    @Override
    public void loadData(List<AvisosBean> listAvisos) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        MyPublicationsAdapter myPublicationsAdapter = new MyPublicationsAdapter(this.getActivity().getApplicationContext(), listAvisos, this.iMyPublicationsPresenter);
        this.recycler.setLayoutManager(linearLayoutManager);
        this.recycler.setAdapter(myPublicationsAdapter);
        this.recycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showDialogDelete(final AvisosBean avisosBean) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.confirmation))
                //.setIcon(R.mipmap.ic_clear_black_18dp)
                .setMessage(getActivity().getString(R.string.delete_messaje))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        iMyPublicationsPresenter.deleteAviso(avisosBean);
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showEditAviso(AvisosBean avisosBean){
        Intent intent = new Intent(getActivity(), AddMyPublicationsView.class);
        Gson gson = new Gson();
        String jAvisosBean = gson.toJson(avisosBean);
        intent.putExtra("avisosBean", jAvisosBean);
        getActivity().startActivity(intent);
    }

    @Override
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recycler.setVisibility(show ? View.GONE : View.VISIBLE);
            recycler.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recycler.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recycler.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String incorrectOperation) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void reloadData() {
        this.iMyPublicationsPresenter.listAvisos("");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

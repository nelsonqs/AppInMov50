package com.ucb.appin.views;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.views.activity.ActivityPublicationDetail;

public class MainView extends AppCompatActivity implements MappView.OnFragmentInteractionListener,
        PublicationsView.OnFragmentInteractionListener,
        MyPublicationsView.OnFragmentInteractionListener {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private int indexFragment = 0;

    private BottomNavigationView navigation;
    private Menu menu;
    private boolean isMultiPanel;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = new PublicationsView();
            switch (item.getItemId()) {
                case R.id.nav_publications:
                    indexFragment = 0;
                    hideShowMenu(false);
                    fragment = new PublicationsView();
                    break;
                case R.id.nav_maps:
                    indexFragment = 1;
                    hideShowMenu(false);
                    fragment = new MappView();
                    break;
                case R.id.nav_my_publications:
                    indexFragment = 2;
                    hideShowMenu(true);
                    fragment = new MyPublicationsView();
                    break;
            }
            Log.v("----<>", "......" + indexFragment);
            registeredFragments.append(indexFragment, fragment);

            loadFragment(fragment);

            return false;
        }

    };

    @Override
    @SuppressLint("RestrictedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init test only
        Fragment rFragment = new PublicationsView();
        this.loadFragment(rFragment);
        registeredFragments.append(indexFragment, rFragment);
        //////////////////////////////////////////////////////////////////////////////////////////////////

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        setMultiPanel();

    }

    public void loadFragment(Fragment fragment) {

        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment, fragment);
            fragmentTransaction.commit();
        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            Intent intent = new Intent(MainView.this, AddMyPublicationsView.class);
            int requestCode = 1; // Or some number you choose
            startActivityForResult(intent, requestCode);
        }
        if (id == R.id.menu_cuenta) {
            Intent intent = new Intent(MainView.this, AdminAccountView.class);
            int requestCode = 2; // Or some number you choose
            startActivityForResult(intent, requestCode);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResume() {
        super.onResume();

        Fragment rFragment = registeredFragments.get(indexFragment);
        if (rFragment == null) {
            rFragment = new PublicationsView();
            indexFragment = 0;
        } else {
            if (indexFragment == 2) {
                MyPublicationsView myPublicationsView = (MyPublicationsView) rFragment;
                myPublicationsView.reloadData();
            }
        }

    }

    @Override
    public void onListClick(AvisosBean avisosBean) {
        Intent intent = new Intent(this, ActivityPublicationDetail.class);
        intent.putExtra("subject", avisosBean.getTitulo());
        intent.putExtra("message", avisosBean.getDescripcion());
        intent.putExtra("senderName", avisosBean.getDireccion());
        startActivity(intent);
    }

    private void setMultiPanel() {
        isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.fragmentDetailsMail) != null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void hideShowMenu(boolean visible) {
        MenuItem menuItem = menu.findItem(R.id.menu_add);
        menuItem.setVisible(visible);
    }

}

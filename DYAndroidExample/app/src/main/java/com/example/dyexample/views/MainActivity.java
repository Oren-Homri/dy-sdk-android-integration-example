package com.example.dyexample.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.dynamicyield.dyapi.DYApi;
import com.dynamicyield.engine.DYPageContext;
import com.dynamicyield.listener.itf.DYListenerItf;
import com.dynamicyield.state.DYExperimentsState;
import com.dynamicyield.state.DYInitState;
import com.example.dyexample.R;

import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements DYListenerItf {


    protected final DYPageContext DY_PAGE_CONTEXT= new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);


    private ViewPager mTab;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showWaitingForDY();

        initiateTheTab();

    }

    @Override
    protected void onResume() {
        super.onResume();

        DYApi.getInstance().setListener(this);
        DYApi.getInstance().trackPageView("Homepage",DY_PAGE_CONTEXT);
    }


    //***** DY Listener Implementation *****//

    @Override
    public void experimentsReadyWithState(DYExperimentsState dyExperimentsState) {

        final DYPageContext PAGE_CONTEXT= new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);

        //Call DY as soon as possible
        if (dyExperimentsState == DYExperimentsState.DY_READY_LOCAL_CACHE){

        }

        //Call DY if the SDK have the latest experiments
        if (dyExperimentsState == DYExperimentsState.DY_READY_NO_UPDATE_NEEDED){

        }

        //Call DY again if experiments has been edited
        if (dyExperimentsState == DYExperimentsState.DY_READY_AND_UPDATED){

        }

        hideWaitingForDY(dyExperimentsState.toString());
    }

    @Override
    public void onSmartObjectLoadResult(String s, String s1, View view) {

    }

    @Override
    public void onSmartActionReturned(String s, JSONObject jsonObject) {

    }

    //***** Deprecated Functions, will be removed soon *****//

    @Override
    public boolean shouldDYRender(String s) {
        return true;
    }

    @Override
    public void experimentsUpdatedAndReady() {

    }

    @Override
    public void initReply(DYInitState dyInitState) {

    }

    //***** FragmentHelper functions *****//
    private void hideWaitingForDY(String state){

        showToast("Got respond from DY: " + state);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgress != null) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        });
    }

    private void initiateTheTab() {

        String tabTitles[] = new String[] { "Dynamic Content", "Recommendation"};

        ExampleFragmentPagerAdapter tabAdapter = new ExampleFragmentPagerAdapter(getSupportFragmentManager(), tabTitles);

        mTab = findViewById(R.id.pager);
        mTab.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);

        for (String tabName : tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mTab.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showWaitingForDY() {

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Waiting for DY to be ready!");
        mProgress.setCancelable(false);
        mProgress.show();

    }

    public void showToast(final String textToShow){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, textToShow,Toast.LENGTH_LONG).show();
            }
        });
    }

    //*****End Of FragmentHelper functions *****//
}

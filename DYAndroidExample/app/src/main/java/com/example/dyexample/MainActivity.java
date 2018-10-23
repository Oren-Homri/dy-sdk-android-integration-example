package com.example.dyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dynamicyield.dyapi.DYApi;
import com.dynamicyield.engine.DYPageContext;
import com.dynamicyield.engine.DYRecommendationListenerItf;
import com.dynamicyield.listener.itf.DYListenerItf;
import com.dynamicyield.state.DYExperimentsState;
import com.dynamicyield.state.DYInitState;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DYListenerItf,DYRecommendationListenerItf {

    private final DYPageContext PAGE_CONTEXT= new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);
    private final String PAGE_NAME = "Homepage";
    private final String RCOM_WIDGET_ID = YOUR_WIDGET_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DYApi.getInstance().setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Report Page Viewed when the user arrived
        DYApi.getInstance().trackPageView(PAGE_NAME,PAGE_CONTEXT);

    }

    @Override
    public void onRecommendationResult(JSONArray jsonArray, String widgetID) {
        Log.d("DYExampleApp","RCOM call returned, WidgetID: " + widgetID + ", data: " + (jsonArray != null ? jsonArray.toString() : "Empty data"));
    }

    public void reportClickToDY(String SKU){
        DYApi.getInstance().trackRecomItemClick(RCOM_WIDGET_ID,SKU);
    }

    public void reportRIMPToDY(String[] SKUs){
        DYApi.getInstance().trackRecomItemRealImpression(RCOM_WIDGET_ID, SKUs);
    }


    @Override
    public void experimentsReadyWithState(DYExperimentsState dyExperimentsState) {


        //Call DY as soon as possible
        if (dyExperimentsState == DYExperimentsState.DY_READY_LOCAL_CACHE){
            DYApi.getInstance().sendRecommendationsRequest(RCOM_WIDGET_ID,PAGE_CONTEXT,false,this);
        }

        if (dyExperimentsState == DYExperimentsState.DY_READY_NO_UPDATE_NEEDED){

        }

        //Call DY again if experiments has been edited
        if (dyExperimentsState == DYExperimentsState.DY_READY_AND_UPDATED){
            DYApi.getInstance().sendRecommendationsRequest(RCOM_WIDGET_ID,PAGE_CONTEXT,false,this);
        }

    }

    @Override
    public void experimentsUpdatedAndReady() {

    }

    @Override
    public void initReply(DYInitState dyInitState) {

    }

    @Override
    public void onSmartObjectLoadResult(String s, String s1, View view) {

    }

    @Override
    public void onSmartActionReturned(String s, JSONObject jsonObject) {

    }

    @Override
    public boolean shouldDYRender(String s) {
        return true;
    }
}

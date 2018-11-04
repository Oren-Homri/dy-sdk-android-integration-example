package com.example.dyexample.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dynamicyield.dyapi.DYApi;
import com.dynamicyield.engine.DYPageContext;
import com.dynamicyield.engine.DYRecommendationListenerItf;
import com.example.dyexample.R;
import com.example.dyexample.utils.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;

public class RcomView extends Helper implements DYRecommendationListenerItf {

    private final String RCOM_WIDGET_ID = YOUR_WIDGET_ID_HERE;
    private final DYPageContext DY_PAGE_CONTEXT= new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);

    private TextView mRcomText;
    private Button mCallRcomButton;
    private boolean[] mReportedToDY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rcom_frag, container, false);

        configureVars(view);

        return view;
    }

    private void fillRcomFromDY() {

        DYApi.getInstance().sendRecommendationsRequest(RCOM_WIDGET_ID,DY_PAGE_CONTEXT,false,this);

        showToast("Calling RCOM with: " + RCOM_WIDGET_ID);

    }

    //***** DY Recommendations Listener Implementation *****//

    @Override
    public void onRecommendationResult(JSONArray jsonArray, String widgetID) {
        setResults(jsonArray);

        showToast("RCOM call returned, WidgetID: " + widgetID + ", data count: " + (jsonArray != null ? jsonArray.length() : " 0"));
    }

    //***** DY Recommendations Listener Implementation End *****//


    public void reportClickToDY(String SKU){
        if (SKU != null) {

            DYApi.getInstance().trackRecomItemClick(RCOM_WIDGET_ID, SKU);

            showToast("Reporting click on SKU: " + SKU + ", WidgetID: " + RCOM_WIDGET_ID);
        }
    }

    public void reportRIMPToDY(String[] SKUs){

        if (SKUs != null) {

            DYApi.getInstance().trackRecomItemRealImpression(RCOM_WIDGET_ID, SKUs);

            showToast("Reporting rimp on SKU: " + SKUs[0] + ", WidgetID: " + RCOM_WIDGET_ID);
        }

    }

    //***** Helper functions *****//

    private void configureVars(View android) {
        mCallRcomButton = (Button) android.findViewById(R.id.rcomButton);
        mRcomText = (TextView) android.findViewById(R.id.rcomTextView);

        mCallRcomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillRcomFromDY();
            }
        });
    }

    private void setTextOnTextView(final String textToShow){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRcomText.setText(textToShow);
            }
        });

    }

    private void setResults(final JSONArray results){

        mReportedToDY = new boolean[results.length()];

        if (results != null && results.length() > 0) {
            setTextOnTextView(results.toString());

            final LinearLayout layout = getActivity().findViewById(R.id.linear);
            for (int i = 0; i < results.length(); i++) {
                final ImageView imageView = new ImageView(getContext());
                imageView.setId(i);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);



                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            reportClickToDY(results.getJSONObject(finalI).getJSONObject("item").getString("sku"));
                        } catch (JSONException e) {
                        }

                    }
                });

                try {
                    new DownloadImageTask(imageView).execute(results.getJSONObject(i).getJSONObject("item").getString("image_url"));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            layout.addView(imageView);
                        }
                    });
                } catch (JSONException e) {

                }
            }

            getActivity().findViewById(R.id.horizontal_scroll).getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {

                    int scrollX = getActivity().findViewById(R.id.horizontal_scroll).getScrollX(); // For HorizontalScrollView
                    int sizeOfItem = getActivity().findViewById(R.id.horizontal_scroll).getWidth()/results.length();

                    int index = scrollX/sizeOfItem;

                    if ((index < results.length()) && !mReportedToDY[index]) {
                        try {
                            String skus[] = new String[]{results.getJSONObject(index).getJSONObject("item").getString("sku")};
                            reportRIMPToDY(skus);
                        } catch (JSONException e) {

                        }

                        mReportedToDY[index] = true;
                    }
                }
            });


        } else {
            setTextOnTextView("");
        }
    }


}

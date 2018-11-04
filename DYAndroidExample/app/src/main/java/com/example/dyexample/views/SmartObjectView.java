package com.example.dyexample.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.dynamicyield.dyapi.DYApi;
import com.dynamicyield.engine.DYPageContext;
import com.example.dyexample.R;


public class SmartObjectView extends Helper {

    private Button mShowDCOnWebView;
    private Button mShowDCOnImageView;

    private ImageView mDCImageView;
    private WebView mDCWebView;

    private final DYPageContext DY_PAGE_CONTEXT= new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);
    private final String DY_PAGE_NAME = "Homepage";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.smart_object_frag, container, false);

        configureVars(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Report Page Viewed when the user arrived
        DYApi.getInstance().trackPageView(DY_PAGE_NAME,DY_PAGE_CONTEXT);

    }

    private void fillImageViewFromDY(ImageView imageView) {

        String dynamicContentID = YOUR_DYNAMIC_CONTENT_ID;

        //Call DY to load Dynamic Content with ID "dynamicContentID" into "mDCImageView"
        DYApi.getInstance().loadSmartObject(mDCImageView,dynamicContentID,"https://www.gstatic.com/webp/gallery/4.jpg");

        showToast("Calling DC Image with: " + dynamicContentID);

    }

    private void fillWebViewFromDY(WebView webView) {
        String dynamicContentID = YOUR_DYNAMIC_CONTENT_ID;

        //Call DY to load Dynamic Content with ID "dynamicContentID" into "mDCWebView"
        DYApi.getInstance().loadSmartObject(mDCWebView,dynamicContentID,"https://www.gstatic.com/webp/gallery/4.jpg");

        showToast("Calling DC Web with: " + dynamicContentID);

    }

    private void configureVars(View android) {
        mShowDCOnImageView = android.findViewById(R.id.DCImageButton);
        mDCImageView = android.findViewById(R.id.DCImage);

        mShowDCOnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillImageViewFromDY(mDCImageView);
            }
        });

        mShowDCOnWebView = android.findViewById(R.id.DCWebButton);
        mDCWebView = android.findViewById(R.id.DCWeb);

        mShowDCOnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebViewFromDY(mDCWebView);
            }
        });

    }

}
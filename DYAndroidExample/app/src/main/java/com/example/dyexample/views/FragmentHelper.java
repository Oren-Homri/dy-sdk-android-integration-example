
package com.example.dyexample.views;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.dynamicyield.dyapi.DYApi;
import com.dynamicyield.engine.DYPageContext;

/**
 * Created by idanoshri on 04/07/2017.
 */

public class FragmentHelper extends Fragment {

    protected final DYPageContext DY_PAGE_CONTEXT = new DYPageContext("en_US",DYPageContext.HOMEPAGE,null);
    protected String mPageName;


    public void showToast(final String textToShow){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), textToShow,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        DYApi.getInstance().trackPageView(mPageName,DY_PAGE_CONTEXT);

    }

}
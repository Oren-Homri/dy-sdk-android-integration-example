
package com.example.dyexample.views;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by idanoshri on 04/07/2017.
 */

public class Helper extends Fragment {

    public void showToast(final String textToShow){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), textToShow,Toast.LENGTH_LONG).show();
            }
        });
    }

}
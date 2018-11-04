package com.example.dyexample.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ExampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String mTabTitles[];

    public ExampleFragmentPagerAdapter(FragmentManager fm,String[] tabTitles) {
        super(fm);
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for SmartObjectView mTab
                return new SmartObjectView();
            case 1:
                //Fragement for RCOM mTab
                return new RcomView();

        }
        return null;

    }

    @Override
    public int getCount () {
        return mTabTitles.length; //No of Tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTabTitles[position];
    }

}
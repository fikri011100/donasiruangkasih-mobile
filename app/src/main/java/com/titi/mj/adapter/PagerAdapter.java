package com.titi.mj.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.titi.mj.fragment.history.CompleteFragment;
import com.titi.mj.fragment.history.PendingFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CompleteFragment();
            case 1:
                return new PendingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

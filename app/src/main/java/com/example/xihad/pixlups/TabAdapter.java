package com.example.xihad.pixlups;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragmentList = new ArrayList<>();
    private  ArrayList <String> titleslist = new ArrayList<>();

    public  void addFragment(Fragment fragment, String title){

        fragmentList.add(fragment);
        titleslist.add(title);

    }


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return  titleslist.get(position);
    }

}

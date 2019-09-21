package com.example.xihad.pixlups;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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

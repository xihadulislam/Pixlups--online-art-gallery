package com.example.xihad.pixlups;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingupActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tabLayout = findViewById(R.id.tablayoutId);
        viewPager = findViewById(R.id.viewpagerId);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new UserFragment(), "Become a Seller");
        tabAdapter.addFragment(new ClientFragment(),"Become a Buyer");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}

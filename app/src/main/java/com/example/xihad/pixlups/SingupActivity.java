package com.example.xihad.pixlups;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SingupActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
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

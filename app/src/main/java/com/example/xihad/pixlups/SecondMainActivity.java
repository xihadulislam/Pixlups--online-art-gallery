package com.example.xihad.pixlups;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class SecondMainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private ClientCart clientCart;
    private ClientHome clientHome;
    private ClientNotification  clientNotification;
    private ClientProfile  clientProfile;

    private AlertDialog.Builder albil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        frameLayout = findViewById(R.id.fragment_container2);
        BottomNavigationView navigation = findViewById(R.id.navigation2);

        clientHome = new ClientHome();
        clientCart = new ClientCart();
        clientNotification = new ClientNotification();
        clientProfile = new ClientProfile();

        setFragment(clientHome);


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home2:
                        setFragment(clientHome);
                        return true;
                    case R.id.navigation_cart2:
                        setFragment(clientCart);
                        return true;
                    case R.id.navigation_notifications2:
                        setFragment(clientNotification);
                        return true;
                    case R.id.navigation_account2:
                        setFragment(clientProfile);
                        return true;
                }

                return false;
            }


        });


    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container2, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.aboutus2){

            albil = new AlertDialog.Builder(this);
            albil.setMessage("\n\n Developed by Team Pixlups\n Team Members:\n\n Zihadul Islam\n id:1612020084 \n \n Raju Naidu \n id:1612020083 \n \n Jamiya alom \n id:1612020084 \n \n copyright 2019 all rights reserved \n\n");
            AlertDialog alertDialog = albil.create();
            alertDialog.show();
        }

        if (item.getItemId()==R.id.share2){

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
        }


        if (item.getItemId()==R.id.contact2){


            albil = new AlertDialog.Builder(this);
            albil.setMessage("\n\nif you have any queries please feel free to contact us 01793734938\n\n");
            AlertDialog alertDialog = albil.create();
            alertDialog.show();
        }


        if (item.getItemId()==R.id.signout2)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SecondMainActivity.this,LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }


}



package com.example.xihad.pixlups;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private Upload upload;
    private Home home;
    private Notifications notifications;
    private Account account;
    private BottomNavigationView navigation;

    private AlertDialog.Builder albil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frameLayout = findViewById(R.id.fragment_container);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        home = new Home();
        upload = new Upload();
        notifications = new Notifications();
        account = new Account();

        setFragment(home);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(home);
                        return true;
                    case R.id.navigation_upload:
                        setFragment(upload);
                        return true;
                    case R.id.navigation_notifications:
                        setFragment(notifications);
                        return true;
                    case R.id.navigation_account:
                        setFragment(account);
                        return true;
                }

                return false;
            }


        });


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.aboutus){

            albil = new AlertDialog.Builder(this);
            albil.setMessage("\n\n Developed by Team Pixlups\n Team Members:\n\n Zihadul Islam\n id:1612020084 \n \n Raju Naidu \n id:1612020083 \n \n Jamiya alom \n id:1612020084 \n \n copyright 2019 all rights reserved \n\n");
            AlertDialog alertDialog = albil.create();
            alertDialog.show();
        }

        if (item.getItemId()==R.id.signout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        if (item.getItemId()==R.id.editpayment){
            startActivity(new Intent(MainActivity.this,EditPaymentMethodActivity.class));

        }

        if (item.getItemId()==R.id.withdraw){
            startActivity(new Intent(MainActivity.this,WithdrawActivity.class));
        }


        if (item.getItemId()==R.id.share){

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
          //  Toast.makeText(this, "clicked...", Toast.LENGTH_SHORT).show();
        }


        if (item.getItemId()==R.id.contact){

            albil = new AlertDialog.Builder(this);
            albil.setMessage("\n\nif you have any queries please feel free to contact us 01793734938\n\n");
            AlertDialog alertDialog = albil.create();
            alertDialog.show();
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

package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    private Button singup, login,forgetpass;
    private EditText email, password;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 1500);

        progressDialog = new ProgressDialog(this);
        login = findViewById(R.id.loginButtonId);
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpass);
        forgetpass = findViewById(R.id.forgotpasswordId);
        final String[] value = new String[1];
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
        
        if (us!=null&&us.isEmailVerified()){

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("userinfo").child(us.getUid());
            db.keepSynced(true);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String idnty = dataSnapshot.child("identity").getValue().toString();

                    if (idnty.equals("client")){

                        startActivity(new Intent(LoginActivity.this,SecondMainActivity.class));
                        finish();
                    }
                    else if (idnty.equals("photographer")){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this,databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });



        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em = email.getText().toString().trim();
                String pass = password.getText().toString().trim();


                if (em.isEmpty()) {
                    email.setError("Email Required");
                    email.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {

                    password.setError("password Required");
                    password.requestFocus();
                    return;
                }

                if (!em.isEmpty() && !pass.isEmpty()) {

                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(em, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (task.isSuccessful()) {

                                        if (user.isEmailVerified()) {

                                            user = FirebaseAuth.getInstance().getCurrentUser();
                                            databaseReference =  FirebaseDatabase.getInstance().getReference("userinfo").child(user.getUid());
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    String idnt = dataSnapshot.child("identity").getValue().toString();
                                                    if (idnt.equals("client")){
                                                        progressDialog.dismiss();
                                                    Intent intent = new Intent(LoginActivity.this, SecondMainActivity.class);
                                                      startActivity(intent);
                                                    }
                                                    else if (idnt.equals("photographer")){
                                                        progressDialog.dismiss();
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "Email is not verified", Toast.LENGTH_LONG).show();
                                        }
                                    } else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }

            }
        });


        singup = findViewById(R.id.singupButtonId);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        return;
    }
}

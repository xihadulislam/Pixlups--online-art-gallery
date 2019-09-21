package com.example.xihad.pixlups;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientFragment extends Fragment {
    public ClientFragment() {
    }


    private Button button;
    private EditText editText1, editText2, editText3, editText4, editText5;
    private CheckBox checkBox;
    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client, container, false);


        button = view.findViewById(R.id.clisingupButtonId);
        editText1 = view.findViewById(R.id.userID2);
        editText2 = view.findViewById(R.id.EmailID2);
        editText3 = view.findViewById(R.id.passID2);
        editText4 = view.findViewById(R.id.conpaasID2);
        checkBox = view.findViewById(R.id.checkboxId2);
        textView = view.findViewById(R.id.alreadyhaveaccountclient);
        progressDialog = new ProgressDialog(getActivity());


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("userinfo");


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = editText1.getText().toString().trim();
                final String email = editText2.getText().toString().trim();
                final String password = editText3.getText().toString().trim();
                final String password2 = editText4.getText().toString().trim();
                final String identity = "client";


                if (username.isEmpty()) {

                    editText1.setError("Username is required");
                    editText1.requestFocus();
                    return;
                }

                if (email.isEmpty()) {

                    editText2.setError("Email is required");
                    editText2.requestFocus();
                    return;
                }

                if (password.isEmpty()) {

                    editText3.setError("Password is required");
                    editText2.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    editText3.setError("password must be minimum of 6 characters");
                    editText2.requestFocus();
                    return;
                }
                if (!password.equals(password2)) {

                    editText4.setError("Password are not match");
                    editText4.requestFocus();
                    return;
                }
                if (checkBox.isChecked()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    progressDialog.setMessage("Please Wait...!");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password2)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        sendVarification();
                                        Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();

                                        //store data
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String description = "describe yourself in short ! !";
                                        String image = "https://firebasestorage.googleapis.com/v0/b/pixlups-6e58d.appspot.com/o/zzz.png?alt=media&token=2bc972d4-f2af-48ba-9950-6bc3e86e0b0b";
                                        UserData userData = new UserData(username, email, password, identity,description,image);
                                       // String Id = databaseReference.push().getKey();

                                        databaseReference.child(user.getUid()).setValue(userData)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                        if (task.isSuccessful()) {
                                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });

                                    } else {

                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(getActivity(), "You are already Registered ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });


                }
                else

                {
                    Toast.makeText(getActivity(), "Please make sure Checked Terms and Condition ", Toast.LENGTH_LONG).show();

                }


            }
        });



        return view;




    }

    private void sendVarification() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Please Check your email for verification", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}

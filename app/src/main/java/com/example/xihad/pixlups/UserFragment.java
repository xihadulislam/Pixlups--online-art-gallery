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


public class UserFragment extends Fragment {


    public UserFragment() {
    }


    private Button button;
    private EditText editText1, editText2, editText3, editText4, editText5;
    private CheckBox checkBox;
    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private ProgressDialog progressDialog;

    DatabaseReference databaseReference,databaseReference2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        button = view.findViewById(R.id.usersingupButtonId);
        editText1 = view.findViewById(R.id.userID1);
        editText2 = view.findViewById(R.id.EmailID1);
        editText3 = view.findViewById(R.id.passID1);
        editText4 = view.findViewById(R.id.conpaasID1);
        checkBox = view.findViewById(R.id.checkID);
        textView = view.findViewById(R.id.alreadyhaveaccountuser);
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
                final String identity = "photographer";


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
                                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        String description = "describe yourself in short ! !";
                                        String image = "https://firebasestorage.googleapis.com/v0/b/pixlups-6e58d.appspot.com/o/zzz.png?alt=media&token=2bc972d4-f2af-48ba-9950-6bc3e86e0b0b";
                                        UserData userData = new UserData(username, email, password, identity,description,image);
                                        //String Id = databaseReference.push().getKey();

                                        databaseReference.child(user.getUid()).setValue(userData)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                        if (task.isSuccessful()) {

                                                            databaseReference2 = FirebaseDatabase.getInstance().getReference("paymentinfo");

                                                            PaymentData paymentData = new PaymentData("null","null",0);
                                                            databaseReference2.child(user.getUid()).setValue(paymentData);
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


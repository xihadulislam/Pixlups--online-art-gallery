package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPaymentMethodActivity extends AppCompatActivity {



    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;

    private EditText editText;
    private Button button1,button2;

    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment_method);

        radioGroup = findViewById(R.id.buttongroup);
        radioButton = findViewById(R.id.bkashId);
        radioButton2 = findViewById(R.id.rocketId);
        button1 = findViewById(R.id.cancelButtonId2);
        button2 = findViewById(R.id.saveButtonId2);
        editText = findViewById(R.id.editDesId2);
        progressDialog = new ProgressDialog(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String acc = editText.getText().toString().trim();

                String method;


                if (radioButton.isChecked()){
                    method = "Bkash";
                }
                else if (radioButton2.isChecked())
                {
                    method = "Bkash";
                }
                else
                {
                    method = "null";
                }


                if (acc.isEmpty()){
                    editText.setError("account no required");
                }
                else
                {
                    progressDialog.setMessage("Please Wait...!");
                    progressDialog.show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference("paymentinfo").child(user.getUid());

                    databaseReference.child("paymentmethod").setValue(method);
                    databaseReference.child("account").setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressDialog.dismiss();
                            if (task.isSuccessful()){

                                Toast.makeText(EditPaymentMethodActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditPaymentMethodActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(EditPaymentMethodActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });

        
    }
}

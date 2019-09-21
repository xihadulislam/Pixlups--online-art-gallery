package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WithdrawActivity extends AppCompatActivity {


    private TextView blnc,method,account;
    private Button save, cancel;
    private EditText editText;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;

    private String bal,meto,acc;
    private ProgressDialog progressDialog;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        blnc = findViewById(R.id.balanceeeId);
        method = findViewById(R.id.methodccId);
        account = findViewById(R.id.accountnoId);
        editText = findViewById(R.id.editDesId3);
        save = findViewById(R.id.saveButtonId3);
        cancel = findViewById(R.id.cancelButtonId3);
        progressDialog = new ProgressDialog(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



         user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("paymentinfo").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 bal= dataSnapshot.child("balance").getValue().toString();
                blnc.setText(bal);

                meto = dataSnapshot.child("paymentmethod").getValue().toString();
                method.setText(meto);
                acc = dataSnapshot.child("account").getValue().toString();

                account.setText(acc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference2 = FirebaseDatabase.getInstance().getReference("admin_pay_notify");





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                final int Bal = Integer.parseInt(bal);

                String amount = editText.getText().toString().trim();

                final int Amount = Integer.parseInt(amount);


                if (Amount>Bal||Amount<1000){
                    editText.setError("Please Enter Valid Amount");
                }
                else
                {
                    final String key = databaseReference2.push().getKey();
                    PaymentData pp =  new  PaymentData(acc,meto,Amount);
                    databaseReference2.child(key).setValue(pp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                databaseReference3 = FirebaseDatabase.getInstance().getReference("usernotify").child(user.getUid());
                                databaseReference3.child(user.getUid()+key).child("item").setValue("your Request is under processing..");
                                Toast.makeText(WithdrawActivity.this, "your Request is under processing..", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(WithdrawActivity.this,MainActivity.class));
                                databaseReference.child("balance").setValue(Bal-Amount);
                                databaseReference2.child(key).child("id").setValue(key);
                                databaseReference2.child(key).child("userid").setValue(user.getUid());

                            }
                            else
                            {
                                Toast.makeText(WithdrawActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }


            }
        });



    }
}

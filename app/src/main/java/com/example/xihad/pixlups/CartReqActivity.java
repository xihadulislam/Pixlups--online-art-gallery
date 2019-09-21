package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartReqActivity extends AppCompatActivity {


    private EditText orderId, TaxId;
    private Button submit;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference ,adminref,dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_req);


        final Intent intent = getIntent();
        final String imageUrl = intent.getStringExtra("img");
        final String caption = intent.getStringExtra("cap");
        final String price = intent.getStringExtra("price");
        final String upusid = intent.getStringExtra("userid");


        orderId = findViewById(R.id.orderId);
        TaxId = findViewById(R.id.TrxId);
        submit = findViewById(R.id.submitId);


        progressDialog = new ProgressDialog(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                String ord = orderId.getText().toString().trim();
                String trx = TaxId.getText().toString().trim();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference("cart").child(user.getUid());
                adminref = FirebaseDatabase.getInstance().getReference("admin_notify");

                final String id = databaseReference.push().getKey();

                CartData cartData = new CartData(caption,imageUrl,price,"Pending..",id);
                final CartData cartDataadmin = new CartData(ord,trx,id,user.getUid(),upusid,price);

                databaseReference.child(id).setValue(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        adminref.child(id).setValue(cartDataadmin);
                        progressDialog.dismiss();
                        Toast.makeText(CartReqActivity.this, "your order is under processing..", Toast.LENGTH_SHORT).show();
                        dbref = FirebaseDatabase.getInstance().getReference("client_notify").child(user.getUid()).child(id);
                        dbref.child("item").setValue("your order is under processing..");
                        startActivity(new Intent(CartReqActivity.this,SecondMainActivity.class));
                    }
                });

            }
        });


    }
}

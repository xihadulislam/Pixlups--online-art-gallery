package com.example.xihad.pixlups;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.xihad.pixlups.ShowItem.EXTRA_URL;
import static com.example.xihad.pixlups.ShowItem.EXTRA_CAPTION;
import static com.example.xihad.pixlups.ShowItem.EXTRA_DISCRIPTION;
import static com.example.xihad.pixlups.ShowItem.EXTRA_PRICE;

public class ShowDetailsActivity extends AppCompatActivity {

    private ImageView imageView1,imageView2;
    private TextView textView1, textView2,textView3,textView4,textView5,textView6;


    private DatabaseReference databaseReference,mdata;
    public boolean islike = false;
    public boolean islike2 = false;

    private  String count;
    private  int i;
    private  String cusername;

    private  String category;

    DatabaseReference dn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_show_details);


        imageView1 = findViewById(R.id.mainImageId);
        imageView2 = findViewById(R.id.mainheartId);
        textView1 = findViewById(R.id.finalpriceId);
        textView2 = findViewById(R.id.mainlikecountId);
        textView3 = findViewById(R.id.mainUdernameId);
        textView4 = findViewById(R.id.mainCaptionId);
        textView5 = findViewById(R.id.mainDiscriptionId);
        textView6= findViewById(R.id.cattttt);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        final String caption = intent.getStringExtra(EXTRA_CAPTION);
        final String discription = intent.getStringExtra(EXTRA_DISCRIPTION);
        final String price = intent.getStringExtra(EXTRA_PRICE);
        final String key = intent.getStringExtra("k");
        final String upuserid = intent.getStringExtra("userid");
        final String cat = intent.getStringExtra("cat");



        Picasso.get().load(imageUrl).fit().centerInside().into(imageView1);

        textView1.setText(price);
        textView4.setText(caption);
        textView5.setText(discription);
        textView6.setText(cat);
       // textView3.setText(username);


        if (cat.equals("ART photography")){
            category = "art_photography";
        }
        if (cat.equals("Wildlife")){
            category = "wildlife";
        }

        if (cat.equals("Landscape")){
            category = "landscape";
        }

        if (cat.equals("Mobile photography")){
            category ="mobile_photography";
        }

        if (cat.equals("Macro")){
            category = "macro";
        }

        if (cat.equals("Wedding")){
            category = "wedding";
        }

        if (cat.equals("Street photography")){
            category = "street_photography";
        }

        if (cat.equals("Children photos")){
            category = "children_photos";
        }

        if (cat.equals("Portrait")){
            category = "portrait";
        }

        if (cat.equals("Black and white")){
            category = "black_and_white";
        }

        if (cat.equals("Fashion and glamour")){
            category = "fashion_and_glamour";
        }

        if (cat.equals("Others")){
            category = "others";
        }


        databaseReference = FirebaseDatabase.getInstance().getReference(category).child(key);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        mdata = FirebaseDatabase.getInstance().getReference("userinfo");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cusername = dataSnapshot.child(user.getUid()).child("username").getValue().toString();
              String  upus = dataSnapshot.child(upuserid).child("username").getValue().toString();
                textView3.setText(upus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailsActivity.this,UserproActivity.class);
                intent.putExtra("name", upuserid);
                intent.putExtra("id", "cl");
                startActivity(intent);
            }
        });







        // for likeCount
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                count= dataSnapshot.child("likecount").getValue().toString();
                textView2.setText(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = Integer.parseInt(count);

                islike = true;
                islike2 = true;


                dn = FirebaseDatabase.getInstance().getReference("usernotify");

                final String ci = dn.push().getKey();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        DatabaseReference cup = FirebaseDatabase.getInstance().getReference("currentusersupload").child(upuserid).child(key);
                        if (islike){

                            if (dataSnapshot.child("likes").hasChild(user.getUid())){

                                databaseReference.child("likes").child(user.getUid()).removeValue();
                                islike = false;
                                int iadd = i-1;
                                databaseReference.child("likecount").setValue(iadd);



                                cup.child("likes").child(user.getUid()).removeValue();
                                islike = false;
                                int iad = i-1;
                                cup.child("likecount").setValue(iad);
                            }

                        else
                        {
                            databaseReference.child("likes").child(user.getUid()).setValue(cusername);
                            islike = false;
                            int iadd = i+1;
                            databaseReference.child("likecount").setValue(iadd);

                            cup.child("likes").child(user.getUid()).setValue(cusername);
                            islike = false;
                            int iad = i+1;
                            cup.child("likecount").setValue(iad);

                        }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                dn.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (islike2) {
                            if (dataSnapshot.child(upuserid).hasChild(user.getUid()+key)) {

                                dn.child(upuserid).child(user.getUid()+key).removeValue();
                                islike2 = false;
                            } else {

                                dn.child(upuserid).child(user.getUid()+key).child("item").setValue(cusername + " loves your photo");
                                islike2 = false;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("likes").hasChild(user.getUid())){

                    imageView2.setImageResource(R.drawable.red_heart);
                }
                else
                {
                    imageView2.setImageResource(R.drawable.heart2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
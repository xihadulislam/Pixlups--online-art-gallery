package com.example.xihad.pixlups;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserproActivity extends AppCompatActivity {



    public static final String EXTRA_URL = "imageuri";
    public static final String EXTRA_CAPTION = "caption";
    public static final String EXTRA_DISCRIPTION = "description";
    public static final String EXTRA_PRICE = "price";


    private TextView username, description;
    private RecyclerView recyclerView;
    private ImageView propic;
    private DatabaseReference databaseReference, databaseReference2;
    private ArrayList<UploadData> list;

    private String kk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpro);

        propic = findViewById(R.id.prophotoId2);
        username = findViewById(R.id.pronameId2);
        description = findViewById(R.id.prodescriptionId2);
        recyclerView = findViewById(R.id.myphotosId2);


        Intent intent = getIntent();
        final String userid = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");

        databaseReference = FirebaseDatabase.getInstance().getReference("userinfo").child(userid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("username").getValue().toString();
                String des = dataSnapshot.child("description").getValue().toString();
                String ing = dataSnapshot.child("image").getValue().toString();
                username.setText(name);
                description.setText(des);

                try {
                    Picasso.get().load(ing).placeholder(R.drawable.loading).into(propic);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        databaseReference2= FirebaseDatabase.getInstance().getReference("currentusersupload").child(userid);


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p = dataSnapshot1.getValue(UploadData.class);
                    list.add(p);

                }
                MyAdapter adapter = new MyAdapter(getApplicationContext(),list);
                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (id.equals("client")){


                            Intent intent = new Intent(UserproActivity.this, CshowdetailsActivity.class);
                            UploadData uploadData = list.get(position);
                            intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                            intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                            intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                            intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                            intent.putExtra("k", uploadData.getId());
                            intent.putExtra("userid", uploadData.getUserid());
                            intent.putExtra("cat", uploadData.getCategory());

                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(UserproActivity.this, ShowDetailsActivity.class);
                            UploadData uploadData = list.get(position);
                            intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                            intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                            intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                            intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                            intent.putExtra("k", uploadData.getId());
                            intent.putExtra("userid", uploadData.getUserid());
                            intent.putExtra("cat", uploadData.getCategory());

                            startActivity(intent);


                        }


                    }
                });
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(UserproActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }


        });







    }
}

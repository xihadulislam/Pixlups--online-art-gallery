package com.example.xihad.pixlups;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CshowitemActivity extends AppCompatActivity {


    public static final String EXTRA_URL = "imageuri";
    public static final String EXTRA_CAPTION = "caption";
    public static final String EXTRA_DISCRIPTION = "description";
    public static final String EXTRA_PRICE = "price";

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    private  String category,title;

    private String[] orderspin;
    private  String orderval;

    private Spinner odr;
    private ImageView imageView;

    // FirebaseUIAdapter uiAdapter;


    private FirebaseRecyclerAdapter<UploadData, FirebaseUiHolder> adapter;
    private FirebaseRecyclerOptions<UploadData> options;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cshowitem);

        recyclerView = findViewById(R.id.showRecylerId2);
        odr = findViewById(R.id.homeorderId2);


        orderspin  = getResources().getStringArray(R.array.order);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinarview, R.id.spinerviewId, orderspin);
        odr.setAdapter(adapter2);

        String val = getIntent().getStringExtra("name");
        if (val.equals("artphotogrphy")){
            category = "art_photography";
            title = "ART photography";

        }
        if (val.equals("Wildlife")){
            category = "wildlife";
            title = "Wildlife";

        }

        if (val.equals("Landscape")){
            category = "landscape";
            title = "Landscape";
        }

        if (val.equals("Mobile_photography")){
            category ="mobile_photography";
            title = "Mobile photography";
        }

        if (val.equals("Macro")){
            category = "macro";
            title = "Macro";
        }

        if (val.equals("Wedding")){
            category = "wedding";
            title = "Wedding";
        }

        if (val.equals("Street_photography")){
            category = "street_photography";
            title = "Street photography";
        }

        if (val.equals("Children_photos")){
            category = "children_photos";
            title = "Children photos";
        }

        if (val.equals("Portrait")){
            category = "portrait";
            title = "Portrait";
        }

        if (val.equals("Black_and_white")){
            category = "black_and_white";
            title = "Black and white";
        }

        if (val.equals("Fashion_and_glamour")){
            category = "fashion_and_glamour";
            title = "Fashion and glamour";
        }

        if (val.equals("Others")){
            category = "others";
            title = "Others";
        }

        getSupportActionBar().setTitle(title);



        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        query = FirebaseDatabase
                .getInstance()
                .getReference(category);

        options = new FirebaseRecyclerOptions.Builder<UploadData>()
                .setQuery(query, UploadData.class)
                .build();


        callAdapter(options);

        odr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(CshowitemActivity.this, t, Toast.LENGTH_SHORT).show();


                if (t.equals("All")){
                    query = FirebaseDatabase
                            .getInstance()
                            .getReference(category);
                }else if (t.equals("Most popular")){
                    query = FirebaseDatabase
                            .getInstance()
                            .getReference(category)
                            .orderByChild("likecount");
                }else if (t.equals("Last upload")){
                    query = FirebaseDatabase
                            .getInstance()
                            .getReference(category)
                            .orderByChild("priority");
                }else if (t.equals("Free")){
                    query = FirebaseDatabase
                            .getInstance()
                            .getReference(category)
                            .orderByChild("price")
                            .equalTo("Free");

                }
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("Query", dataSnapshot+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                options = new FirebaseRecyclerOptions.Builder<UploadData>()
                        .setQuery(query, UploadData.class)
                        .build();

                callAdapter(options);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }





    private void callAdapter(FirebaseRecyclerOptions options){

        if (adapter != null)adapter.stopListening();

        adapter = new FirebaseRecyclerAdapter<UploadData, FirebaseUiHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseUiHolder holder, final int position, @NonNull final UploadData model) {
                holder.setImageuri(model.getImageuri());
                holder.setCaption(model.getCaption());
                holder.setPrice(model.getPrice());

                Log.e("Modelll", model.getCaption()+"");

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CshowitemActivity.this, ShowDetailsActivity.class);
                        intent.putExtra(EXTRA_URL, model.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, model.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, model.getDescription());
                        intent.putExtra(EXTRA_PRICE, model.getPrice());
                        intent.putExtra("k", model.getId());
                        intent.putExtra("userid", model.getUserid());
                        intent.putExtra("cat", model.getCategory());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public FirebaseUiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater
                        .from(getApplicationContext())
                        .inflate(R.layout.item_view, viewGroup, false);
                return new FirebaseUiHolder(v);
            }

            @NonNull
            @Override
            public UploadData getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }



}



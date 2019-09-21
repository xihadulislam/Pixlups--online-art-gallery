package com.example.xihad.pixlups;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Account extends Fragment implements View.OnClickListener {
    public Account() {
    }

    public static final String EXTRA_URL = "imageuri";
    public static final String EXTRA_CAPTION = "caption";
    public static final String EXTRA_DISCRIPTION = "description";
    public static final String EXTRA_PRICE = "price";


    private AlertDialog.Builder albil;
    private Button editpro;
    private TextView username, description;
    private RecyclerView recyclerView;
    private ImageView propic;
    private DatabaseReference databaseReference, databaseReference2,db,db2,databaseReference3;
    private  TextView balance;

    private ArrayList<UploadData> list;

    private String kk;
    private   String category;

    private FirebaseRecyclerOptions<UploadData> options;
    private FirebaseRecyclerAdapter<UploadData, UiAdapter> firebaseRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_account, container, false);


        editpro = view.findViewById(R.id.editproId);
        username = view.findViewById(R.id.pronameId);
        description = view.findViewById(R.id.prodescriptionId);
        recyclerView = view.findViewById(R.id.myphotosId);
        propic = view.findViewById(R.id.prophotoId);
        balance = view.findViewById(R.id.blanceId);
        editpro.setOnClickListener(Account.this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("currentusersupload").child(user.getUid());
        databaseReference2 = FirebaseDatabase.getInstance().getReference("userinfo").child(user.getUid());


        databaseReference2.addValueEventListener(new ValueEventListener() {
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


        databaseReference3 = FirebaseDatabase.getInstance().getReference("paymentinfo").child(user.getUid());

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               balance.setText(dataSnapshot.child("balance").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Query query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("currentusersupload")
                .child(user.getUid())
                .orderByChild("priority");


        options = new FirebaseRecyclerOptions.Builder<UploadData>()
                .setQuery(query, UploadData.class)
                .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UploadData, UiAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UiAdapter holder, final int position, @NonNull final UploadData model) {

                holder.setImageuri(model.getImageuri());
                holder.setCaption(model.getCaption());
                holder.setPrice(model.getPrice());

                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
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


                holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        String cat = model.getCategory();

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


                         db = FirebaseDatabase.getInstance().getReference(category);
                        db2 = FirebaseDatabase.getInstance().getReference("currentusersupload").child(model.getUserid());

                        albil = new AlertDialog.Builder(getActivity());

                        albil.setMessage("Do You want to Delete?");

                        albil.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.child(model.getId()).removeValue();
                                db2.child(model.getId()).removeValue();
                                dialog.dismiss();
                            }
                        });
                        albil.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = albil.create();
                        alertDialog.show();

                        return true;
                    }
                });


            }


            @NonNull
            @Override
            public UiAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.item_view, viewGroup, false);


                return new UiAdapter(v);
            }

        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();

    }

    @Override
    public void onClick(View v) {

        if (v==editpro){
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        }

    }


    public static class UiAdapter extends RecyclerView.ViewHolder {


        View mview;

        public UiAdapter(@NonNull View itemView) {
            super(itemView);
            mview = itemView;

            Log.e("Paisi", itemView+"");

        }

        public void setCaption(String caption) {
            TextView Caption = mview.findViewById(R.id.itemnameId);
            Caption.setText(caption);
        }


        public void setPrice(String price) {
            TextView prices = mview.findViewById(R.id.itempriceId);
            prices.setText(price);
        }


        public void setImageuri(String imageuri) {
            ImageView Image = mview.findViewById(R.id.itemimgId);
            Picasso.get().load(imageuri).into(Image);
        }

    }


}



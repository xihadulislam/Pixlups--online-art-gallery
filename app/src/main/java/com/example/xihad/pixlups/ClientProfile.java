package com.example.xihad.pixlups;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ClientProfile extends Fragment {


    public ClientProfile() {

    }


    private Button editpro;
    private TextView username, description;
    private ImageView propic;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client_profile, container, false);


        editpro = view.findViewById(R.id.editproId2);
        username = view.findViewById(R.id.pronameId2);
        description = view.findViewById(R.id.prodescriptionId2);
        propic = view.findViewById(R.id.prophotoId2);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("userinfo").child(user.getUid());

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


        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


}

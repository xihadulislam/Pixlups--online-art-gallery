package com.example.xihad.pixlups;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ClientHome extends Fragment implements View.OnClickListener {
    public ClientHome() {
    }

    public static final String EXTRA_URL = "imageuri";
    public static final String EXTRA_CAPTION = "caption";
    public static final String EXTRA_DISCRIPTION = "description";
    public static final String EXTRA_PRICE = "price";


    private DatabaseReference databaseReference;

    private  TextView art,wild,land,mobile,macro,wedd,street,children,port,black,fashion,others;
    private Button button,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12;
    public RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4,recyclerView5,recyclerView6,recyclerView7,recyclerView8,recyclerView9,recyclerView10,recyclerView11,recyclerView12;
    private DatabaseReference mref, mref2,mref3,mref4,mref5,mref6,mref7,mref8,mref9,mref10,mref11,mref12;

    ArrayList<UploadData> list;
    ArrayList<UploadData> list2;
    ArrayList<UploadData> list3;
    ArrayList<UploadData> list4;
    ArrayList<UploadData> list5;
    ArrayList<UploadData> list6;
    ArrayList<UploadData> list7;
    ArrayList<UploadData> list8;
    ArrayList<UploadData> list9;
    ArrayList<UploadData> list10;
    ArrayList<UploadData> list11;
    ArrayList<UploadData> list12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_client_home, container, false);



       art = view.findViewById(R.id.arttextId);
       wild = view.findViewById(R.id.wildtextId);
       land = view.findViewById(R.id.landtextId);
       mobile = view.findViewById(R.id.mobiletextId);
       macro = view.findViewById(R.id.macrotextId);
       wedd = view.findViewById(R.id.wedtextId);
       street = view.findViewById(R.id.strettextId);
       children = view.findViewById(R.id.chiltextId);
       port = view.findViewById(R.id.porttextId);
       black = view.findViewById(R.id.blacktextId);
       fashion = view.findViewById(R.id.fashiontextId);
       others = view.findViewById(R.id.otherstextId);



        button = view.findViewById(R.id.artphotogrphyId2);
        button2 = view.findViewById(R.id.WildlifeId2);
        button3 = view.findViewById(R.id.LandscapeId2);
        button4 = view.findViewById(R.id.Mobile_photographyId2);
        button5 = view.findViewById(R.id.MacroId2);
        button6 = view.findViewById(R.id.WeddingId2);
        button7 = view.findViewById(R.id.Street_photographyId2);
        button8 = view.findViewById(R.id.Children_photosId2);
        button9 = view.findViewById(R.id.PortraitId2);
        button10 = view.findViewById(R.id.Black_and_whiteId2);
        button11 = view.findViewById(R.id.Fashion_and_glamourId2);
        button12 = view.findViewById(R.id.OthersId2);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);


        recyclerView = view.findViewById(R.id.reclyleId122);
        recyclerView2 = view.findViewById(R.id.reclyleId222);
        recyclerView3 = view.findViewById(R.id.reclyleId322);
        recyclerView4 =view.findViewById(R.id.reclyleId422);
        recyclerView5 = view.findViewById(R.id.reclyleId522);
        recyclerView6 = view.findViewById(R.id.reclyleId622);
        recyclerView7 = view.findViewById(R.id.reclyleId722);
        recyclerView8 =view.findViewById(R.id.reclyleId822);
        recyclerView9 = view.findViewById(R.id.reclyleId922);
        recyclerView10 =view. findViewById(R.id.reclyleId1022);
        recyclerView11 =view.findViewById(R.id.reclyleId1122);
        recyclerView12 =view.findViewById(R.id.reclyleId1222);


        mref = FirebaseDatabase.getInstance().getReference("art_photography");
        mref2 = FirebaseDatabase.getInstance().getReference("wildlife");
        mref3 = FirebaseDatabase.getInstance().getReference("landscape");
        mref4 = FirebaseDatabase.getInstance().getReference("mobile_photography");
        mref5 = FirebaseDatabase.getInstance().getReference("macro");
        mref6 = FirebaseDatabase.getInstance().getReference("wedding");
        mref7 = FirebaseDatabase.getInstance().getReference("street_photography");
        mref8 = FirebaseDatabase.getInstance().getReference("children_photos");
        mref9 = FirebaseDatabase.getInstance().getReference("portrait");
        mref10 = FirebaseDatabase.getInstance().getReference("black_and_white");
        mref11 = FirebaseDatabase.getInstance().getReference("fashion_and_glamour");
        mref12= FirebaseDatabase.getInstance().getReference("others");


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p = dataSnapshot1.getValue(UploadData.class);
                    list.add(p);

                }
                MyAdapter adapter = new MyAdapter(getContext(),list);
                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
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
                });
                recyclerView.setAdapter(adapter);

                art.setText("ART photography");
                button.setVisibility(view.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }


        });



        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list2 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p2 = dataSnapshot1.getValue(UploadData.class);
                    list2.add(p2);
                }
                MyAdapter adapter2 = new MyAdapter(getContext(),list2);
                adapter2.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list2.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView2.setAdapter(adapter2);


                wild.setText("Wildlife");
                button2.setVisibility(view.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });





        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(linearLayoutManager3);

        mref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list3 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p3 = dataSnapshot1.getValue(UploadData.class);
                    list3.add(p3);
                }
                MyAdapter adapter3 = new MyAdapter(getContext(),list3);
                adapter3.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list3.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView3.setAdapter(adapter3);

                land.setText("Landscape");
                button3.setVisibility(view.VISIBLE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        recyclerView4.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView4.setLayoutManager(linearLayoutManager4);

        mref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list4 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p4 = dataSnapshot1.getValue(UploadData.class);
                    list4.add(p4);
                }
                MyAdapter adapter4 = new MyAdapter(getContext(),list4);
                adapter4.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list4.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView4.setAdapter(adapter4);

                mobile.setText("Mobile photography");
                button4.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        recyclerView5.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView5.setLayoutManager(linearLayoutManager5);

        mref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list5 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p5 = dataSnapshot1.getValue(UploadData.class);
                    list5.add(p5);
                }
                MyAdapter adapter5 = new MyAdapter(getContext(),list5);
                adapter5.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list5.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView5.setAdapter(adapter5);

                macro.setText("Macro");
                button5.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        recyclerView6.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView6.setLayoutManager(linearLayoutManager6);

        mref6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list6 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p6 = dataSnapshot1.getValue(UploadData.class);
                    list6.add(p6);
                }
                MyAdapter adapter6 = new MyAdapter(getContext(),list6);
                adapter6.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list6.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView6.setAdapter(adapter6);

                wedd.setText("Wedding");
                button6.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        recyclerView7.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView7.setLayoutManager(linearLayoutManager7);

        mref7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list7 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p7 = dataSnapshot1.getValue(UploadData.class);
                    list7.add(p7);
                }
                MyAdapter adapter7 = new MyAdapter(getContext(),list7);
                adapter7.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list7.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView7.setAdapter(adapter7);

                street.setText("Street photography");
                button7.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        recyclerView8.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager8 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView8.setLayoutManager(linearLayoutManager8);

        mref8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list8 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p8 = dataSnapshot1.getValue(UploadData.class);
                    list8.add(p8);
                }
                MyAdapter adapter8 = new MyAdapter(getContext(),list8);
                adapter8.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list8.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView8.setAdapter(adapter8);

                children.setText("Children photos");
                button8.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });





        recyclerView9.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager9 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView9.setLayoutManager(linearLayoutManager9);

        mref9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list9 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p9 = dataSnapshot1.getValue(UploadData.class);
                    list9.add(p9);
                }
                MyAdapter adapter9 = new MyAdapter(getContext(),list9);
                adapter9.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list9.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView9.setAdapter(adapter9);

                port.setText("Portrait");
                button9.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        recyclerView10.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager10 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView10.setLayoutManager(linearLayoutManager10);

        mref10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list10 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p10 = dataSnapshot1.getValue(UploadData.class);
                    list10.add(p10);
                }
                MyAdapter adapter10 = new MyAdapter(getContext(),list10);
                adapter10.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list10.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView10.setAdapter(adapter10);

                black.setText("Black and white");
                button10.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });





        recyclerView11.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager11 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView11.setLayoutManager(linearLayoutManager11);

        mref11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list11 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p11 = dataSnapshot1.getValue(UploadData.class);
                    list11.add(p11);
                }
                MyAdapter adapter11 = new MyAdapter(getContext(),list11);
                adapter11.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list11.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView11.setAdapter(adapter11);

                fashion.setText("Fashion and glamour");
                button11.setVisibility(view.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        recyclerView12.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager12 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView12.setLayoutManager(linearLayoutManager12);

        mref12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list12 = new ArrayList<UploadData>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    UploadData p12 = dataSnapshot1.getValue(UploadData.class);
                    list12.add(p12);
                }
                MyAdapter adapter12 = new MyAdapter(getContext(),list12);
                adapter12.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), CshowdetailsActivity.class);
                        UploadData uploadData = list12.get(position);
                        intent.putExtra(EXTRA_URL, uploadData.getImageuri());
                        intent.putExtra(EXTRA_CAPTION, uploadData.getCaption());
                        intent.putExtra(EXTRA_DISCRIPTION, uploadData.getDescription());
                        intent.putExtra(EXTRA_PRICE, uploadData.getPrice());
                        intent.putExtra("k", uploadData.getId());
                        intent.putExtra("userid", uploadData.getUserid());
                        intent.putExtra("cat", uploadData.getCategory());

                        startActivity(intent);

                    }
                });
                recyclerView12.setAdapter(adapter12);


                others.setText("Others");
                button12.setVisibility(view.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }


    @Override
    public void onClick(View v) {

        if (v==button){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "artphotogrphy");
            startActivity(intent);
        }
        if (v.getId()==R.id.WildlifeId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Wildlife");
            startActivity(intent);
        }
        if (v.getId()==R.id.LandscapeId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Landscape");
            startActivity(intent);
        }
        if (v.getId()==R.id.Mobile_photographyId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Mobile_photography");
            startActivity(intent);
        }
        if (v.getId()==R.id.MacroId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Macro");
            startActivity(intent);
        }
        if (v.getId()==R.id.WeddingId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Wedding");
            startActivity(intent);
        }
        if (v.getId()==R.id.Street_photographyId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Street_photography");
            startActivity(intent);
        }
        if (v.getId()==R.id.Children_photosId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Children_photos");
            startActivity(intent);
        }
        if (v.getId()==R.id.PortraitId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Portrait");
            startActivity(intent);
        }
        if (v.getId()==R.id.Black_and_whiteId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Black_and_white");
            startActivity(intent);
        }
        if (v.getId()==R.id.Fashion_and_glamourId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Fashion_and_glamour");
            startActivity(intent);
        }
        if (v.getId()==R.id.OthersId2){
            Intent intent  = new Intent(getActivity(),CshowitemActivity.class);
            intent.putExtra("name", "Others");
            startActivity(intent);
        }


    }
}


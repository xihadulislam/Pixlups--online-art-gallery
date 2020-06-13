package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Upload extends Fragment {

    public Upload() {
    }

    private ImageView showphoto;
    private Button takephoto, uploadphoto;
    private EditText caption, discription,priceEd;
    private Spinner category;
    private RadioButton free, premium;
    private RadioGroup priceradiogroup;
    String pricespinerselected = "Free";
    private static final int PICK_IMAGE_REQUEST = 1;

    private DatabaseReference databaseReference, databaseReference2,db,db2;
    private StorageReference storageReference;
    private Uri muri;
    private StorageTask mUploadTask;
    private ProgressDialog progressDialog;

    private String[] categoris;
    private String[] prices;

    public  String userid="";
    String captionvalue;
    String categoryspinerselected;
    String settpirce;
    String discriptionvalue;
    String Imguri;

    public   int pryt = 1;
    public   String prytt = "";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        showphoto = view.findViewById(R.id.showphotoId);
        takephoto = view.findViewById(R.id.takephotoId);
        caption = view.findViewById(R.id.captionId);
        category = view.findViewById(R.id.categoryspinerId);
        priceradiogroup = view.findViewById(R.id.priceGroupId);
        free = view.findViewById(R.id.freeId);
        premium = view.findViewById(R.id.premiumId);
        discription = view.findViewById(R.id.descriptionId);
        uploadphoto = view.findViewById(R.id.uploadbuttonId);
        priceEd = view.findViewById(R.id.priceEdId);
        progressDialog = new ProgressDialog(getActivity());

        categoris = getResources().getStringArray(R.array.category_Name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinarview, R.id.spinerviewId, categoris);

        category.setAdapter(adapter);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });



        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceEd.setVisibility(View.GONE);

            }
        });
        priceEd.setVisibility(View.GONE);
        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceEd.setVisibility(View.VISIBLE);

            }
        });



        //<------------------------<bal>------------------------------->//

        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pryt = Integer.parseInt(prytt);


                if (premium.isChecked()){
                    pricespinerselected = priceEd.getText().toString().trim();
                }
                else
                {
                    pricespinerselected = "Free";
                }


                captionvalue = caption.getText().toString().trim();
                categoryspinerselected = category.getSelectedItem().toString();
                settpirce = pricespinerselected;
                discriptionvalue = discription.getText().toString().trim();


                if (captionvalue.isEmpty()) {
                    caption.setError("Caption is required");
                }
                if (discriptionvalue.isEmpty()) {
                    discription.setError("Discription is required");
                }


                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    if (categoryspinerselected.equals("ART photography")) {

                        databaseReference = FirebaseDatabase.getInstance().getReference("art_photography");
                        storageReference = FirebaseStorage.getInstance().getReference("art_photography");
                        photoupload();
                    } else if (categoryspinerselected.equals("Wildlife")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("wildlife");
                        storageReference = FirebaseStorage.getInstance().getReference("wildlife");
                        photoupload();
                    } else if (categoryspinerselected.equals("Landscape")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("landscape");
                        storageReference = FirebaseStorage.getInstance().getReference("landscape");
                        photoupload();
                    } else if (categoryspinerselected.equals("Mobile photography")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("mobile_photography");
                        storageReference = FirebaseStorage.getInstance().getReference("mobile_photography");
                        photoupload();
                    } else if (categoryspinerselected.equals("Macro")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("macro");
                        storageReference = FirebaseStorage.getInstance().getReference("macro");
                        photoupload();
                    } else if (categoryspinerselected.equals("Wedding")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("wedding");
                        storageReference = FirebaseStorage.getInstance().getReference("wedding");
                        photoupload();
                    } else if (categoryspinerselected.equals("Street photography")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("street_photography");
                        storageReference = FirebaseStorage.getInstance().getReference("street_photography");
                        photoupload();
                    } else if (categoryspinerselected.equals("Children photos")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("children_photos");
                        storageReference = FirebaseStorage.getInstance().getReference("children_photos");
                        photoupload();
                    } else if (categoryspinerselected.equals("Portrait")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("portrait");
                        storageReference = FirebaseStorage.getInstance().getReference("portrait");
                        photoupload();
                    } else if (categoryspinerselected.equals("Black and white")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("black_and_white");
                        storageReference = FirebaseStorage.getInstance().getReference("black_and_white");
                        photoupload();
                    } else if (categoryspinerselected.equals("Fashion and glamour")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("fashion_and_glamour");
                        storageReference = FirebaseStorage.getInstance().getReference("fashion_and_glamour");
                        photoupload();
                    } else if (categoryspinerselected.equals("Others")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("others");
                        storageReference = FirebaseStorage.getInstance().getReference("others");
                        photoupload();
                    }
                }
            }
        });

        FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();

        userid = us.getUid();

        db2 = FirebaseDatabase.getInstance().getReference("priority");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                prytt = dataSnapshot.child("count").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            muri = data.getData();
            Picasso.get().load(muri).fit().centerCrop().into(showphoto);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }



    private void photoupload() {
        if (muri != null) {
            progressDialog.setMessage("Please Wait...!");
            progressDialog.show();
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(muri));

            mUploadTask = fileReference.putFile(muri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri imageuri = uri;
                                    final String Id = databaseReference.push().getKey();
                                    final UploadData uploadData = new UploadData(captionvalue, categoryspinerselected, settpirce, discriptionvalue, imageuri.toString(),userid,pryt,Id,0);

                                    pryt = pryt +1;
                                 //  prytt = String.valueOf(pryt);
                                    db2.child("count").setValue(pryt);

                                    databaseReference.child(Id).setValue(uploadData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Upload Successful..", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                databaseReference2 = FirebaseDatabase.getInstance().getReference("currentusersupload").child(user.getUid());
                                                databaseReference2.child(Id).setValue(uploadData);
                                            } else {
                                                Toast.makeText(getActivity(), "Faild..", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });


                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(getActivity(), "No Photo Selected.", Toast.LENGTH_SHORT).show();
        }


    }

}


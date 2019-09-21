package com.example.xihad.pixlups;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView propic;
    private EditText username, description;
    private Button takephoto,save,cancel;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri muri;
    private StorageTask mUploadTask;
    private ProgressDialog progressDialog;

    private  String userName,Descriptiom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        propic = findViewById(R.id.editphotoID);
        takephoto = findViewById(R.id.tekepicId);
        username = findViewById(R.id.edituserID);
        description = findViewById(R.id.editDesId);
        save = findViewById(R.id.saveButtonId);
        cancel = findViewById(R.id.cancelButtonId);

        takephoto.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("userinfo").child(user.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("userpropic");
        databaseReference.keepSynced(true);

        callDatabase();


    }

    @Override
    public void onClick(View v) {

        if (v==takephoto){
            Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        if (v==cancel){
            finish();

        }
        if (v==save){

            userName = username.getText().toString().trim();
            Descriptiom = description.getText().toString().trim();

            upload();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            muri = data.getData();
            Picasso.get().load(muri).fit().centerCrop().into(propic);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }




    public  void  callDatabase(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String uname = dataSnapshot.child("username").getValue().toString();
                String des = dataSnapshot.child("description").getValue().toString();
                String ing = dataSnapshot.child("image").getValue().toString();
                username.setText(uname);
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


    }

    private  void  upload(){

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
                                    progressDialog.dismiss();
                                    final Uri imageuri = uri;
                                    databaseReference.child("username").setValue(userName);
                                    databaseReference.child("description").setValue(Descriptiom);
                                    databaseReference.child("image").setValue(imageuri.toString());
                                }
                            });

                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            progressDialog.dismiss();
            databaseReference.child("username").setValue(userName);
            databaseReference.child("description").setValue(Descriptiom).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(EditProfileActivity.this, "Update successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditProfileActivity.this, "Error..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
           
           
        }

    }

}


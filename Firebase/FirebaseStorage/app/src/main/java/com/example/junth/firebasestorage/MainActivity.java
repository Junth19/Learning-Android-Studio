package com.example.junth.firebasestorage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private Button selectImage;
    private ImageView retrieveImageFromFirebase;
    private static final int GALLERY_iNTENT = 2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectImage = (Button) findViewById(R.id.selectImage);
        retrieveImageFromFirebase = (ImageView) findViewById(R.id.retretrieveImageFromFirebase);
        progressDialog = new ProgressDialog(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, GALLERY_iNTENT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_iNTENT && resultCode == RESULT_OK) {

            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("Demo").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Picasso.get().load(taskSnapshot.getDownloadUrl()).fit().into(retrieveImageFromFirebase);
                    Toast.makeText(MainActivity.this, "Upload Done.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}

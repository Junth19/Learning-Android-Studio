package com.example.junth.retrievingdatafromdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Firebase Connection
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReferenceFromUrl("https://retrievingdatafromdataba-ce8c5.firebaseio.com/");

        final EditText key = (EditText) findViewById(R.id.key);
        final EditText value = (EditText) findViewById(R.id.value);
        Button btnSendDataToFirebase = (Button) findViewById(R.id.btnSendDataToServer);
        final TextView retrieveData = (TextView) findViewById(R.id.retrieveData);
        Button retriveDataFromFirebase = (Button) findViewById(R.id.btnRetrieveDataFromDatabase);

        btnSendDataToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child(key.getText().toString()).setValue(value.getText().toString());

                /*String uniqueID = myRef.push().getKey();
                myRef.child(uniqueID).setValue(value.getText().toString());

                myRef.push().child("Child").setValue("Key");*/

            }
        });

        retriveDataFromFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.getReference(key.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String value = dataSnapshot.getValue(String.class);
                        retrieveData.setText(value);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {



                    }
                });

            }
        });

    }
}

package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class createGroup extends AppCompatActivity {
    Button creategrp;
    EditText groupname;
    FirebaseAuth fireAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupname = findViewById(R.id.grpname);
        fireAuth = FirebaseAuth.getInstance();
        creategrp = findViewById(R.id.createbutton);

        creategrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = groupname.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    groupname.setError("Group name cannot be an empty String!"); return;
                }
                groupCreation(name);

            }
        });
    }

    private void groupCreation(String name) {
        String currenttime = "" + System.currentTimeMillis();
        HashMap<String, String> groupinfo = new HashMap<>();
        groupinfo.put("GroupName", name);
        groupinfo.put("GroupID", currenttime);
        groupinfo.put("TimeStamp", currenttime);
        groupinfo.put("CreatedBy", fireAuth.getUid());


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Groups");
        myRef.child(name).setValue(groupinfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(createGroup.this, "Group Created Successfully!", Toast.LENGTH_SHORT).show();

                HashMap<String, String> myInfo = new HashMap<>();
                myInfo.put("GroupName", name);
                myInfo.put("uid", currenttime);
                myInfo.put("Balance", "0");
                myInfo.put("role", "creator");
                myInfo.put("timestamp", currenttime);

                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Groups");
                ref1.child(name).child("Participants").child(fireAuth.getUid()).setValue(myInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(createGroup.this, "Group Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(createGroup.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(createGroup.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
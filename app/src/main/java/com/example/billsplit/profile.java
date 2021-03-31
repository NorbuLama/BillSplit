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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class profile extends AppCompatActivity {
    private Button updatebtn;
    private EditText userName;
    private EditText status;
    FirebaseAuth myAuth;
    private String currentUserid;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updatebtn = findViewById(R.id.upbutton);
        userName = findViewById(R.id.user_name);
        status = findViewById(R.id.status_info);
        myAuth = FirebaseAuth.getInstance();
        currentUserid = myAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setUserName = userName.getText().toString();
                String setUserStatus = status.getText().toString();

                if(TextUtils.isEmpty(setUserName)){
                    Toast.makeText(profile.this, "Please enter you name!!!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(setUserStatus)){
                    Toast.makeText(profile.this, "Please write you status!!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap<String, String> userProfile =  new HashMap<>();
                    userProfile.put("uid", currentUserid);
                    userProfile.put("name", setUserName);
                    userProfile.put("status", setUserStatus);

                    myRef.child("Users").child(currentUserid).setValue(userProfile)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
                                    else{
                                        String e = task.getException().toString();
                                        Toast.makeText(profile.this, e, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });

    }
}
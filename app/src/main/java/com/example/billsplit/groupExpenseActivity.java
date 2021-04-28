package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class groupExpenseActivity extends AppCompatActivity {
    static String groupName;
    static String currentUserID;
    private String curentUserName;
    private FirebaseAuth myAuth;
    private DatabaseReference myRef;
    private TextView Balance;
    private Map<String,String> allBalance = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expense);

        groupName = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(groupExpenseActivity.this, groupName, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(groupName);

        myAuth = FirebaseAuth.getInstance();
        Balance = findViewById(R.id.balance);
        currentUserID = myAuth.getCurrentUser().getUid();
        System.out.println(currentUserID);
        myRef = FirebaseDatabase.getInstance().getReference().child("Users");

        getUserInfo();


        FloatingActionButton addBtn = findViewById(R.id.fab);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),addExpense.class));
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String a = String.valueOf(dsp.child("Balance").getValue());
                    allBalance.put(dsp.getKey(), a);
                }
                String bal = allBalance.get(currentUserID);
                Balance.setText(bal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }


    private void getUserInfo() {
        myRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    curentUserName = snapshot.child("name").getValue().toString();
                    System.out.println(curentUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
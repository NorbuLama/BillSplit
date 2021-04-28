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
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> onlybalance = new ArrayList<>();

    private TextView user1;
    private TextView user2;
    private TextView user3;
    private TextView user4;
    private TextView bal1;
    private TextView bal2;
    private TextView bal3;
    private TextView bal4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expense);
        user1 = findViewById(R.id.user1);
        user2 = findViewById(R.id.user2);
        user3 = findViewById(R.id.user3);
        user4 = findViewById(R.id.user4);
        bal1 = findViewById(R.id.bal1);
        bal2 = findViewById(R.id.bal2);
        bal3 = findViewById(R.id.bal3);
        bal4 = findViewById(R.id.bal4);



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
                    String user = String.valueOf(dsp.child("name").getValue());
                    System.out.println(user);
                    String a = String.valueOf(dsp.child("Balance").getValue());
                    allBalance.put(dsp.getKey(), a);
                    onlybalance.add(a);
                    names.add(user);
                }
                String bal = allBalance.get(currentUserID);
                Balance.setText(bal);
                user1.setText(names.get(0));
                user2.setText(names.get(1));
                user3.setText(names.get(2));
                user4.setText(names.get(3));
                bal1.setText("$ "+onlybalance.get(0));
                bal2.setText("$ "+onlybalance.get(1));
                bal3.setText("$ "+onlybalance.get(2));
                bal4.setText("$ "+onlybalance.get(3));

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
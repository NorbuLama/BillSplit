package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class addExpense extends AppCompatActivity {

    private Button addExpenseButton;
    private EditText expLoc, expAmt;
    String location;
    Double amount;
    private FirebaseAuth myAuth;
    private DatabaseReference myRef;
    String userid;
    int noOfParticipants;
    String groupName;
    List<String> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        myAuth = FirebaseAuth.getInstance();
        //userid = myAuth.getCurrentUser().getUid();

        users =   new ArrayList<>();


        addExpenseButton = findViewById(R.id.addexp);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expLoc = findViewById(R.id.expenseLocation);
                expAmt = findViewById(R.id.expenseAmount);
                location = expLoc.getText().toString().trim();
                amount = Double.parseDouble(expAmt.getText().toString().trim());
                System.out.println(location + " " + amount);
                splittheBill();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }



    private void splittheBill() {
        groupExpenseActivity path = new groupExpenseActivity();
        groupName = groupExpenseActivity.groupName;
        userid = groupExpenseActivity.currentUserID;
        System.out.println(groupName);
        myRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupName);
        myRef.child("Participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noOfParticipants = (int) snapshot.getChildrenCount();
                System.out.println(noOfParticipants);
                System.out.println(users);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        double due = amount/noOfParticipants;
        for(int i = 0; i< noOfParticipants; i++){

        }

    }
}
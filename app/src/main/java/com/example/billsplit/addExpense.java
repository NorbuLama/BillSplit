package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class addExpense extends AppCompatActivity {

    private Button addExpenseButton;
    private EditText expLoc, expAmt;
    String location;
    double amount;
    private FirebaseAuth myAuth;
    private DatabaseReference myRef;
    String userid;
    double noOfParticipants;
    String groupName;
    Map<String, String> users = new HashMap<>();
    List<String> onlyusers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        myAuth = FirebaseAuth.getInstance();
        //userid = myAuth.getCurrentUser().getUid();

        groupExpenseActivity path = new groupExpenseActivity();
        groupName = path.groupName;
        userid = path.currentUserID;
        System.out.println(groupName + " and " + userid);

        addExpenseButton = findViewById(R.id.addexp);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expLoc = findViewById(R.id.expenseLocation);
                expAmt = findViewById(R.id.expenseAmount);
                location = expLoc.getText().toString().trim();
                amount = Double.parseDouble(expAmt.getText().toString().trim());
                myRef = FirebaseDatabase.getInstance().getReference().child("Users");
                splittheBill();
                updatethebill();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void updatethebill() {
        double due = amount-(amount/noOfParticipants);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noOfParticipants = (int) snapshot.getChildrenCount();

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String uid = dsp.getKey();
                    if (uid == userid){
                        double a = Double.parseDouble(users.get(userid));
                        double balance = a+amount-due;
                        myRef.child(uid).child("Balance").setValue(String.valueOf(balance));
                    }else {
                        double a = Double.parseDouble(users.get(uid));
                        double balance = a-due;
                        myRef.child(uid).child("Balance").setValue(String.valueOf(balance));
                    }

                }
//                System.out.println(users);
                System.out.println("this is users: " + users);
                System.out.println("this is onlyusers: " + onlyusers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dss: snapshot.getChildren()){
//                    if (dss.getKey().equalsIgnoreCase(userid)){
//                        double a = Double.parseDouble(users.get(userid));
//                        myRef.child(userid).child("Balance").setValue(String.valueOf(a+amount-due));
//                    }
//                    else{
//                        double a = Double.parseDouble(users.get(userid));
//                        myRef.child(userid).child("Balance").setValue(String.valueOf(a-due));
//                    }
//                }
//
//                for(String id: onlyusers){
//                    if(id.equalsIgnoreCase(userid)){
//                        double a = Double.parseDouble(users.get(id));
//                        myRef.child(userid).child("Balance").setValue(String.valueOf(a+amount-due));
//                    }
//                    else{
//                        double a = Double.parseDouble(users.get(id));
//                        myRef.child(userid).child("Balance").setValue(String.valueOf(a-due));
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        for(String id: onlyusers){
//            if(id.equalsIgnoreCase(userid)){
//                double a = Double.parseDouble(users.get(id));
//
//               myRef.child(userid).child("Balance").setValue(String.valueOf(a+amount-due));
////               updatebalance.setText(String.valueOf(a+amount-due));
//            }
//            else{
//                double a = Double.parseDouble(users.get(id));
////                updatebalance.setText(String.valueOf(a-due));
//                myRef.child(userid).child("Balance").setValue(String.valueOf(10));
//
//            }
//        }

    }


    private void splittheBill() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noOfParticipants = (int) snapshot.getChildrenCount();

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String bala = String.valueOf(dsp.child("Balance").getValue());
                    onlyusers.add(dsp.getKey());
                    users.put(dsp.getKey(), bala);
                }
//                System.out.println(users);
                System.out.println("this is users: " + users);
                System.out.println("this is onlyusers: " + onlyusers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



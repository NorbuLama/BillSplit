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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void splittheBill() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noOfParticipants = (int) snapshot.getChildrenCount();
                users.clear();
                onlyusers.clear();

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String bala = String.valueOf(dsp.child("Balance").getValue());
                    onlyusers.add(dsp.getKey());

                    users.put(dsp.getKey(), bala);
                }


                double due = amount/noOfParticipants;
                System.out.println("this is due: " + due);
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String uid = dsp.getKey();
                    if (uid.equalsIgnoreCase(userid)){
                        double a = Double.parseDouble(users.get(userid));
                        System.out.println(a + " " + " " + amount + " " + due);
                        double balance = a+amount-due;
                        System.out.println("the balance will update for" +uid + " to : " + balance );

                        myRef.child(uid).child("Balance").setValue(String.valueOf(balance));


                    }else {
                        double a = Double.parseDouble(users.get(uid));
                        double balance = a-due;
                        System.out.println(a + " " + " " + amount + " " + due);

                        System.out.println("the balance will update for" +uid + " to : " + balance );

                        myRef.child(uid).child("Balance").setValue(String.valueOf(balance));

                    }

                }
                users.clear();
                onlyusers.clear();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}



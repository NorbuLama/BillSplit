package com.example.billsplit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class groupExpenseActivity extends AppCompatActivity {
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expense);

        groupName = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(groupExpenseActivity.this, groupName, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(groupName);


    }
}
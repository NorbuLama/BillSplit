package com.example.billsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    Button creategroup;
    FirebaseAuth fireAuth;
    private ViewPager myView;
    private TabLayout mytablayout;
    private TabAccessorAdaptor mytabaccessor;
    private FirebaseUser currentUser;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creategroup = findViewById(R.id.create_group);
        fireAuth = FirebaseAuth.getInstance();
        currentUser = fireAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();



        myView = (ViewPager) findViewById(R.id.main_tabs_pager);
        mytabaccessor = new TabAccessorAdaptor(getSupportFragmentManager());
        myView.setAdapter(mytabaccessor);

        mytablayout = (TabLayout) findViewById(R.id.main_tabs);
        mytablayout.setupWithViewPager(myView);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser == null){
            startActivity(new Intent(getApplicationContext(),login.class));
        }
        else{
            verifyUserExistance();
        }
    }

    private void verifyUserExistance() {
        String currentuserID = fireAuth.getCurrentUser().getUid();
        rootRef.child("Users").child(currentuserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((snapshot.child("name").exists())){
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else{
                    startSettingActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startSettingActivity() {
        Intent settingIntent = new Intent(MainActivity.this, profile.class);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_logout:
                fireAuth.signOut();
                startActivity(new Intent(getApplicationContext(),login.class)); break;
            case R.id.create_group:
                startActivity(new Intent(getApplicationContext(),createGroup.class)); break;
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), about.class)); break;
            case R.id.menu_profile:
                startActivity(new Intent(getApplicationContext(), profile.class)); break;

        }
        return super.onOptionsItemSelected(item);
    }
}
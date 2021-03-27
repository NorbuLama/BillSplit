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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    Button creategroup;
    FirebaseAuth fireAuth;
    private ViewPager myView;
    private TabLayout mytablayout;
    private TabAccessorAdaptor mytabaccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creategroup = findViewById(R.id.create_group);
        fireAuth = FirebaseAuth.getInstance();



        myView = (ViewPager) findViewById(R.id.main_tabs_pager);
        mytabaccessor = new TabAccessorAdaptor(getSupportFragmentManager());
        myView.setAdapter(mytabaccessor);

        mytablayout = (TabLayout) findViewById(R.id.main_tabs);
        mytablayout.setupWithViewPager(myView);

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
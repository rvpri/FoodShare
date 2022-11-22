package com.example.foodshare;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("ALL")
public class Activity5 extends AppCompatActivity {

    TextView mailid;
    private String emailid;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        emailid=getIntent().getStringExtra("message_key");

        Fragment selectedFragment1 = new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle data =new Bundle();
        data.putString("mydata",emailid);
        selectedFragment1.setArguments(data);
        fragmentTransaction.replace(R.id.fragment_container,selectedFragment1).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                            Bundle data =new Bundle();
                            data.putString("mydata",emailid);
                            selectedFragment.setArguments(data);
                            fragmentTransaction.replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                        case R.id.nav_map:
                            selectedFragment = new MapsFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                            Bundle data2 =new Bundle();
                            data2.putString("mydata",emailid);
                            selectedFragment.setArguments(data2);
                            fragmentTransaction2.replace(R.id.fragment_container,selectedFragment).commit();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }
}
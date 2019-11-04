package com.titi.mj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.titi.mj.R;
import com.titi.mj.fragment.history.HistoryFragment;
import com.titi.mj.fragment.home.DonationFragment;
import com.titi.mj.fragment.mydonation.MyDonationFragment;
import com.titi.mj.fragment.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView mNavBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loadFragment(new DonationFragment());
        mNavBottom = findViewById(R.id.nav_bottom_mainmenu);
        mNavBottom.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.home_menu:
                fragment = new DonationFragment();
                break;
            case R.id.my_donation_menu:
                fragment = new MyDonationFragment();
                break;
            case R.id.history_menu:
                fragment = new HistoryFragment();
                break;
            case R.id.profile_menu:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_main, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

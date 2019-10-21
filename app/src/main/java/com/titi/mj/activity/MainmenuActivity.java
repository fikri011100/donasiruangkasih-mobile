package com.titi.mj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.titi.mj.R;
import com.titi.mj.fragment.AddonationFragment;
import com.titi.mj.fragment.HelpFragment;
import com.titi.mj.fragment.MainDonationFragment;
import com.titi.mj.fragment.ProfileFragment;

public class MainmenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView mNavBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        getSupportActionBar().hide();

        loadFragment(new MainDonationFragment());
        mNavBottom = findViewById(R.id.nav_bottom_mainmenu);
        mNavBottom.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.home_menu:
                fragment = new MainDonationFragment();
                break;
            case R.id.help_menu:
                fragment = new HelpFragment();
                break;
            case R.id.add_menu:
                fragment = new AddonationFragment();
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
                    .replace(R.id.container_mainmenu, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

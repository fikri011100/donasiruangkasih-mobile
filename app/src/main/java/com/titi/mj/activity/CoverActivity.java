package com.titi.mj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.titi.mj.R;
import com.titi.mj.fragment.cover.LoginFragment;

public class CoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        getSupportActionBar().hide();

        Fragment mFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_cover, mFragment)
                .commit();
    }
}

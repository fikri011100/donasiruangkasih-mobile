package com.titi.mj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.StrictMode;

import com.titi.mj.R;
import com.titi.mj.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Fragment mFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_main, mFragment)
                .commit();
    }
}

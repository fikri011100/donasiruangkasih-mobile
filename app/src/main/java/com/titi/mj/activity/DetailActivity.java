package com.titi.mj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.titi.mj.R;

public class DetailActivity extends AppCompatActivity {

    String title, category;
    int image;
    TextView txt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        txt = findViewById(R.id.tv_title_detail);
        imageView = findViewById(R.id.iv_poster_detail);
        title = getIntent().getStringExtra("name");
        image = getIntent().getIntExtra("image", R.drawable.zah);
        txt.setText(title);
        imageView.setImageResource(image);
    }
}

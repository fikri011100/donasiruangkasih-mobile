package com.titi.mj.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.titi.mj.R;
import com.titi.mj.fragment.home.DonatedFragment;
import com.titi.mj.fragment.mydonation.AddonationFragment;

public class SubdetailActivity extends AppCompatActivity {
    public static final String EXTRA_SUBDETAIL = "extra_subdetail";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_DATA2 = "extra_data2";
    public static final String EXTRA_DATA3 = "extra_data3";
    public static final String EXTRA_DATA4 = "extra_data4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String ext_fragment = getIntent().getStringExtra(EXTRA_SUBDETAIL);

        assert ext_fragment != null;
        fragmentValidation(ext_fragment);

    }

    private void fragmentValidation(String ext_fragment) {
        Fragment fragment = null;
        Bundle to_detail = new Bundle();

        switch (ext_fragment) {
            //donated-menu
            case "dntd":
                fragment = new DonatedFragment();
                break;

            //add-donation-menu
            case "dd":
                fragment = new AddonationFragment();
                break;

            //list-donation-menu
            case "dntn":
                fragment = new com.titi.mj.fragment.home.DetailFragment();

                to_detail.putParcelable("xxx", getIntent().getParcelableExtra(EXTRA_DATA));
                to_detail.putParcelable("xxx2", getIntent().getParcelableExtra(EXTRA_DATA2));
                to_detail.putParcelable("xxx3", getIntent().getParcelableExtra(EXTRA_DATA3));
                fragment.setArguments(to_detail);
                break;

            case "hstry":
                fragment = new com.titi.mj.fragment.history.DetailFragment();

                to_detail.putParcelable("xxx", getIntent().getParcelableExtra(EXTRA_DATA));
                to_detail.putParcelable("xxx2", getIntent().getParcelableExtra(EXTRA_DATA2));
                to_detail.putParcelable("xxx3", getIntent().getParcelableExtra(EXTRA_DATA3));
                to_detail.putParcelable("xxx4", getIntent().getParcelableExtra(EXTRA_DATA4));
                fragment.setArguments(to_detail);
                break;

            //list-my-donation-menu
            case "my":
                fragment = new com.titi.mj.fragment.mydonation.DetailFragment();

                to_detail.putParcelable("xxx", getIntent().getParcelableExtra(EXTRA_DATA));
                to_detail.putParcelable("xxx2", getIntent().getParcelableExtra(EXTRA_DATA2));
                fragment.setArguments(to_detail);
                break;
            default:
                break;
        }


        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_subdetail, fragment)
                .commit();
    }

}

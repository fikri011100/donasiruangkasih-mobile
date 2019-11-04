package com.titi.mj.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.titi.mj.R;
import com.titi.mj.fragment.home.BillFragment;
import com.titi.mj.fragment.home.CountDonateFragment;
import com.titi.mj.fragment.home.DetailFragment;
import com.titi.mj.model.DonatedResponse;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FRAGMENT = "extra_fragment";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_DATA2 = "extra_data2";
    public static final String EXTRA_DATA3 = "extra_data3";
    public static final String EXTRA_DATA4 = "extra_data4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String ext_fragment = getIntent().getStringExtra(EXTRA_FRAGMENT);

        fragmentValidation(ext_fragment);
    }

    private void fragmentValidation(String ext_fragment) {
        Fragment fragment = null;
        Bundle to_detail = new Bundle();

        switch (ext_fragment) {

            //list-donation-menu
            case "dtl-dntn":
                fragment = new CountDonateFragment();
                break;

            //list-my-donation-menu
            case "my":
                fragment = new com.titi.mj.fragment.mydonation.DetailFragment();
                break;

            //bill-fragment
            case "bll":
                fragment = new BillFragment();
                to_detail.putString(BillFragment.EXTRA_B, "list");
                break;
            default:
                break;
        }

        to_detail.putParcelable("xxx", getIntent().getParcelableExtra(EXTRA_DATA));
        to_detail.putParcelable("xxx2", getIntent().getParcelableExtra(EXTRA_DATA2));
        to_detail.putParcelable("xxx3", getIntent().getParcelableExtra(EXTRA_DATA3));
        to_detail.putParcelable("xxx4", getIntent().getParcelableExtra(EXTRA_DATA4));

        assert fragment != null;
        fragment.setArguments(to_detail);

        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_detail, fragment)
                .commit();
    }

}

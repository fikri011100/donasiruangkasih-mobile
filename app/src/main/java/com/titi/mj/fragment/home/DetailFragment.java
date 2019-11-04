package com.titi.mj.fragment.home;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.activity.DetailActivity;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private ImageView mImgPoster, mImgBack, mImgShare, mImgLike;
    private CircleImageView mImgFacill;
    private TextView mTxtTittle, mTxtTotalDonation, mTxtCountDonation,
            mTxtDeadline, mTxtDesc, mTxtNameFacill, mTxtPhoneFacill, mTxtCategory;
    private Button mBtnDonate;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final DonationResponse.Data xxyz = getArguments().getParcelable("xxx");
        final DonationResponse.Users xxyz2 = getArguments().getParcelable("xxx2");
        final DonationResponse.Categories xxyz3 = getArguments().getParcelable("xxx3");

        init(view);

        assert xxyz != null;
        mTxtTittle.setText(xxyz.donationTitle);
        mTxtCountDonation.setText(String.valueOf(xxyz.donationTotal));
        mTxtTotalDonation.setText("Rp. " + xxyz.donationReceived + " telah terkumpul");
        mTxtNameFacill.setText(xxyz2.name);
        mTxtPhoneFacill.setText(xxyz2.email);
        mTxtDesc.setText(xxyz.donationDescription);
        mTxtCategory.setText(xxyz3.nameCategory);
        Glide.with(getContext()).load(BuildConfig.HST + "image/" + xxyz.donationImage).into(mImgPoster);
        Glide.with(getContext()).load(BuildConfig.HST + "image/" + xxyz2.profilePhoto).into(mImgFacill);

        Calendar calendar = Calendar.getInstance();
        Calendar ccalendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(xxyz.donationEnd.substring(0, 10)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int int_deadline = calendar.get(Calendar.DAY_OF_MONTH) - ccalendar.get(Calendar.DAY_OF_MONTH);

        if (int_deadline < 0) {
            mTxtDeadline.setText(String.valueOf(int_deadline).substring(1) + " Days ago");
        } else {
            mTxtDeadline.setText(int_deadline + " Days left");
        }

        mBtnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_detail = new Intent(getContext(), DetailActivity.class);
                go_detail.putExtra(DetailActivity.EXTRA_FRAGMENT, "dtl-dntn");
                go_detail.putExtra(DetailActivity.EXTRA_DATA, xxyz);
//                go_detail.putExtra(DetailActivity.EXTRA_DATA2, xxyz2);
                getActivity().startActivity(go_detail);
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }

    private void init(View view) {
        mTxtTittle = view.findViewById(R.id.tv_title_detail);
        mTxtDeadline = view.findViewById(R.id.tv_deadline_detail);
        mTxtCountDonation = view.findViewById(R.id.tv_count_detail);
        mTxtTotalDonation = view.findViewById(R.id.tv_donation_detail);
        mTxtNameFacill = view.findViewById(R.id.tv_name_detail);
        mTxtPhoneFacill = view.findViewById(R.id.tv_phono_detail);
        mTxtCategory = view.findViewById(R.id.tv_category_detail);
        mTxtDesc = view.findViewById(R.id.tv_desc_detail);
        mImgPoster = view.findViewById(R.id.iv_poster_detail);
        mImgBack = view.findViewById(R.id.iv_back_detail);
        mImgFacill = view.findViewById(R.id.iv_profile_detail);
        mBtnDonate = view.findViewById(R.id.btn_donation_detail);
    }

}

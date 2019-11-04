package com.titi.mj.fragment.history;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.model.DonatedResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private ImageView mImgPoster, mImgBack, mImgShare;
    private CircleImageView mImgFacill;
    private TextView mTxtTittle, mTxtTotalDonation, mTxtCountDonation, mTxtDeadline, mTxtDesc, mTxtNameFacill;
    private Button mBtnDonate;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final DonatedResponse.Data xxyz = getArguments().getParcelable("xxx");
        final DonatedResponse.User xxyz2 = getArguments().getParcelable("xxx2");
        final DonatedResponse.Donation xxyz3 = getArguments().getParcelable("xxx3");
        final DonatedResponse.Paymentmethod xxyz4 = getArguments().getParcelable("xxx4");

        init(view);

        mTxtTittle.setText(xxyz3.donationTitle);
        mTxtTotalDonation.setText(String.valueOf(xxyz.donationAmount));
        mTxtNameFacill.setText(" by " + xxyz2.name);
        Glide.with(getContext()).load(xxyz3.donationImage).into(mImgPoster);
//        Glide.with(getContext()).load(BuildConfig.HST + xxyz2.profilePhoto.substring(1)).into(mImgFacill);

        Calendar calendar = Calendar.getInstance();
        Calendar ccalendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(xxyz.createdAt.substring(0, 9)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int int_deadline = calendar.get(Calendar.DAY_OF_MONTH) - ccalendar.get(Calendar.DAY_OF_MONTH);

        if (int_deadline < 0) {
            mTxtDeadline.setText(String.valueOf(int_deadline).substring(1) + " Days ago");
        } else {
            mTxtDeadline.setText(int_deadline + " Days left");
        }
    }

    private void init(View view) {
        mTxtTittle = view.findViewById(R.id.tv_title_detail);
        mTxtDeadline = view.findViewById(R.id.tv_date_detail);
        mTxtCountDonation = view.findViewById(R.id.tv_count_detail);
        mTxtTotalDonation = view.findViewById(R.id.tv_nominal_detail);
        mTxtNameFacill = view.findViewById(R.id.tv_facilitator_detail);
        mImgPoster = view.findViewById(R.id.iv_poster_detail);
        mImgFacill = view.findViewById(R.id.iv_profile_detail);
        mBtnDonate = view.findViewById(R.id.btn_donation_detail);
    }

}

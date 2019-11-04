package com.titi.mj.fragment.mydonation;


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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.model.DonatingResponse;
import com.titi.mj.model.MydonationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    ImageView mImgPoster, mImgBack;
    TextView mTxtTitle, mTxtStatus, mTxtCategory, mTxtNominal, mTxtCount, mTxtDeadline, mTxtDesc;
    Button mBtnTake;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        assert getArguments() != null;
        DonatingResponse.Data xxyz = getArguments().getParcelable("xxx");
        DonatingResponse.Categories xxyz2 = getArguments().getParcelable("xxx2");

        init(view);

        mTxtTitle.setText(xxyz.donationTitle);
        mTxtCategory.setText(xxyz2.nameCategory);
        mTxtStatus.setText("Complete");
        mTxtNominal.setText("Rp. " + xxyz.donationReceived + ",00");
        mTxtDesc.setText(xxyz.donationDescription);
        mTxtCount.setText(String.valueOf(xxyz.donationTotal));

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


        Glide.with(getContext()).load(BuildConfig.HST + "image/" + xxyz.donationImage).into(mImgPoster);

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void init(View view) {
        mImgPoster = view.findViewById(R.id.iv_poster_detail_2);
        mImgBack = view.findViewById(R.id.iv_back_detail_2);
        mTxtTitle = view.findViewById(R.id.tv_title_detail_2);
        mTxtNominal = view.findViewById(R.id.tv_donation_detail_2);
        mTxtCategory = view.findViewById(R.id.tv_category_detail_2);
        mTxtStatus = view.findViewById(R.id.tv_status_detail_2);
        mTxtCount = view.findViewById(R.id.tv_count_detail_2);
        mTxtDeadline = view.findViewById(R.id.tv_deadline_detail_2);
        mTxtDesc = view.findViewById(R.id.tv_desc_detail_2);

    }
}

package com.titi.mj.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.mj.R;
import com.titi.mj.adapter.DonationAdapter;
import com.titi.mj.model.DonationResponse;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDonationFragment extends Fragment {
    RecyclerView mRecycler;
    ProgressBar mProgress;

    public MainDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_donation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        mRecycler.setVisibility(View.GONE);

        getResponse();

    }

    void init(View view) {
        mRecycler = view.findViewById(R.id.rv_donation);
        mProgress = view.findViewById(R.id.pb_donation);
    }

    private void getResponse(){
        mProgress.setVisibility(View.VISIBLE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DonationResponse> call = apiInterface.getDonation();
        call.enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(Call<DonationResponse> call, Response<DonationResponse> response) {
                if (response.isSuccessful()){
                    mProgress.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);

                    mRecycler.setHasFixedSize(true);
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecycler.setAdapter(new DonationAdapter(response.body().data, getContext()));

                }
            }

            @Override
            public void onFailure(Call<DonationResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
            }
        });
    }

}

package com.titi.mj.fragment.history;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.titi.mj.R;
import com.titi.mj.adapter.HistoryAdapter;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonationResponse;
import com.titi.mj.model.locale.PrefModel;
import com.titi.mj.utils.SharedPref;
import com.titi.mj.utils.network.APIClient;
import com.titi.mj.utils.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {
    RecyclerView mRecycler;
    ProgressBar mProgress;
    Button mBtnReload;
    ImageView mImgNot;
    int id;


    public PendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        SharedPref pref = new SharedPref(getContext());
        PrefModel model = pref.getPreferences();
        id = model.getId();

        mRecycler.setVisibility(View.GONE);

        getResponse();

        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNotFound();
                getResponse();
            }
        });
    }

    void init(View view) {
        mRecycler = view.findViewById(R.id.rv_pending);
        mProgress = view.findViewById(R.id.pb_pending);
        mBtnReload = view.findViewById(R.id.btn_reload_pending);
        mImgNot = view.findViewById(R.id.iv_not_pending);

        hideNotFound();
    }

    private void getResponse() {
        final HistoryAdapter adapter = new HistoryAdapter(getContext());
        mProgress.setVisibility(View.VISIBLE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DonatedResponse> call = apiInterface.getDonated(id, 1);
        call.enqueue(new Callback<DonatedResponse>() {
            @Override
            public void onResponse(Call<DonatedResponse> call, Response<DonatedResponse> response) {
                if (response.isSuccessful()) {
                    mProgress.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);

                    mRecycler.setHasFixedSize(true);
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecycler.setAdapter(adapter);
                    adapter.setListData(response.body().data);
                } else {
                    mProgress.setVisibility(View.GONE);
                    showNotfound();
                }
            }

            @Override
            public void onFailure(Call<DonatedResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
                showNotfound();
            }
        });
    }

    private void showNotfound() {
        mBtnReload.setVisibility(View.VISIBLE);
        mImgNot.setVisibility(View.VISIBLE);
    }

    private void hideNotFound(){
        mBtnReload.setVisibility(View.GONE);
        mImgNot.setVisibility(View.GONE);
    }
}
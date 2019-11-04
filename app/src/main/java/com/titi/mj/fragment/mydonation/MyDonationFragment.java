package com.titi.mj.fragment.mydonation;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.titi.mj.R;
import com.titi.mj.activity.SubdetailActivity;
import com.titi.mj.adapter.MydonationAdapter;
import com.titi.mj.model.DonatingResponse;
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
public class MyDonationFragment extends Fragment {
    RecyclerView mRecycler;
    ProgressBar mProgress;
    MydonationAdapter adapter;
    Button mBtnReload;
    ImageView mImgNot;
    int userId;

    public MyDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_donation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        SharedPref sharedPref = new SharedPref(getContext());
        PrefModel model = sharedPref.getPreferences();
        userId = model.getId();

        init(view);

        adapter = new MydonationAdapter(getContext());
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
        mRecycler = view.findViewById(R.id.rv_my);
        mProgress = view.findViewById(R.id.pb_my);
        mBtnReload = view.findViewById(R.id.btn_reload_my);
        mImgNot = view.findViewById(R.id.iv_not_my);

        hideNotFound();
    }

    private void getResponse() {
        mProgress.setVisibility(View.VISIBLE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DonatingResponse> call = apiInterface.getDonating(userId);
        call.enqueue(new Callback<DonatingResponse>() {
            @Override
            public void onResponse(Call<DonatingResponse> call, Response<DonatingResponse> response) {
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
            public void onFailure(Call<DonatingResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
                showNotfound();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_navigation_top, menu);
        menu.findItem(R.id.donated_menu).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_menu) {
            Intent go_option = new Intent(getContext(), SubdetailActivity.class);
            go_option.putExtra(SubdetailActivity.EXTRA_SUBDETAIL, "dd");
            startActivity(go_option);
        }

        return super.onOptionsItemSelected(item);
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

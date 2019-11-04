package com.titi.mj.fragment.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.mj.R;
import com.titi.mj.activity.SubdetailActivity;
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
public class DonationFragment extends Fragment {
    RecyclerView mRecycler;
    ProgressBar mProgress;
    Button mBtnReload;
    ImageView mImgNot;

    public DonationFragment() {
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        init(view);

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
        mRecycler = view.findViewById(R.id.rv_donation);
        mProgress = view.findViewById(R.id.pb_donation);
        mBtnReload = view.findViewById(R.id.btn_reload_donation);
        mImgNot = view.findViewById(R.id.iv_not_donation);

        hideNotFound();
    }

    private void getResponse() {
        final DonationAdapter adapter = new DonationAdapter(getContext());
        mProgress.setVisibility(View.VISIBLE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<DonationResponse> call = apiInterface.getDonation();
        call.enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(Call<DonationResponse> call, Response<DonationResponse> response) {
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
            public void onFailure(Call<DonationResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
                showNotfound();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_navigation_top, menu);
        menu.findItem(R.id.add_menu).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.donated_menu){
            Intent go_option = new Intent(getContext(), SubdetailActivity.class);
            go_option.putExtra(SubdetailActivity.EXTRA_SUBDETAIL, "dntd");
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

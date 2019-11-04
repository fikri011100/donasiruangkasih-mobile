package com.titi.mj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.activity.DetailActivity;
import com.titi.mj.activity.SubdetailActivity;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonationResponse;

import java.util.List;

public class DonatedAdapter extends RecyclerView.Adapter<DonatedAdapter.MyViewHolder> {
    private List<DonatedResponse.Data> list;
    private Context context;

    public DonatedAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_donated, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setListData(List<DonatedResponse.Data> data){
        this.list = data;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle, mTextNominal;
        ImageView ivPosterItem;
        ConstraintLayout background;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.background);
            mTextTitle = itemView.findViewById(R.id.tv_title_item_donated);
            mTextNominal = itemView.findViewById(R.id.tv_nominal_item_donated);
            ivPosterItem = itemView.findViewById(R.id.iv_poster_item_donated);
        }

        void onBind(final DonatedResponse.Data data) {
            mTextNominal.setText("Rp. " + data.donationAmount + ",00");
            mTextTitle.setText(data.donation.donationTitle);
            Glide.with(context).load(BuildConfig.HST + "image/" + data.donation.donationImage).into(ivPosterItem);

            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go_subdetail = new Intent(context, DetailActivity.class);
                    go_subdetail.putExtra(DetailActivity.EXTRA_FRAGMENT, "bll");
                    go_subdetail.putExtra(DetailActivity.EXTRA_DATA, data);
                    go_subdetail.putExtra(DetailActivity.EXTRA_DATA2, data.user);
                    go_subdetail.putExtra(DetailActivity.EXTRA_DATA3, data.donation);
                    go_subdetail.putExtra(DetailActivity.EXTRA_DATA4, data.paymentmethod);
                    context.startActivity(go_subdetail);
                }
            });
        }
    }
}

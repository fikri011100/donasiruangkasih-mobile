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
import com.titi.mj.R;
import com.titi.mj.activity.DetailActivity;
import com.titi.mj.model.DonationResponse;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.MyViewHolder> {
    private List<DonationResponse.Data> list;
    private Context context;

    public DonationAdapter(List<DonationResponse.Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_donation, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTextCategory.setText(list.get(position).categories.nameCategory);
        holder.mTextTitle.setText(list.get(position).donationTitle);
        Glide.with(context).load(list.get(position).donationImage).into(holder.ivPosterItem);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle, mTextCategory;
        ImageView ivPosterItem;
        ConstraintLayout background;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.background);
            mTextTitle = itemView.findViewById(R.id.tv_title_item);
            mTextCategory = itemView.findViewById(R.id.tv_category_item);
            ivPosterItem = itemView.findViewById(R.id.iv_poster_item);
        }
    }
}

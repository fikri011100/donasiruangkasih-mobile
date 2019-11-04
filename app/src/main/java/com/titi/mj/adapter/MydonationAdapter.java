package com.titi.mj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.titi.mj.BuildConfig;
import com.titi.mj.R;
import com.titi.mj.activity.SubdetailActivity;
import com.titi.mj.model.DonatingResponse;

import java.util.List;

public class MydonationAdapter extends RecyclerView.Adapter<MydonationAdapter.MyViewHolder> {

    private Context context;
    private List<DonatingResponse.Data> list;

    public MydonationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_mydonation, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.onBind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListData(List<DonatingResponse.Data> data){
        this.list = data;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle, mTextStatus;
        ImageView ivPosterItem;
        ConstraintLayout mBg;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextTitle = itemView.findViewById(R.id.tv_title_item_my);
            mTextStatus = itemView.findViewById(R.id.tv_status_item_my);
            ivPosterItem = itemView.findViewById(R.id.iv_poster_item_my);
            mBg = itemView.findViewById(R.id.bg_item_my);
        }

        @SuppressLint("SetTextI18n")
        void onBind(final DonatingResponse.Data data, int position) {
            mTextTitle.setText(data.donationTitle);
            Glide.with(context).load(BuildConfig.HST + "image" + data.donationImage).into(ivPosterItem);

            mBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go_subdetail = new Intent(context, SubdetailActivity.class);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_SUBDETAIL, "my");
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA, data);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA2, data.categories);
                    context.startActivity(go_subdetail);
                }
            });
        }
    }
}

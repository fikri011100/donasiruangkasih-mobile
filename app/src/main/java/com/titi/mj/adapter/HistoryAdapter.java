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
import com.titi.mj.activity.SubdetailActivity;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonationResponse;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<DonatedResponse.Data> list;
    private Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_history, parent, false);
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
        TextView mTextTitle, mTextNominal, mTextDate;
        ImageView ivPosterItem;
        ConstraintLayout mBg;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextTitle = itemView.findViewById(R.id.tv_title_item_history);
            mTextDate = itemView.findViewById(R.id.tv_date_item_history);
            mTextNominal = itemView.findViewById(R.id.tv_nominal_item_history);
            ivPosterItem = itemView.findViewById(R.id.iv_poster_item_history);
            mBg = itemView.findViewById(R.id.background);
        }

        void onBind(final DonatedResponse.Data data) {
            mTextTitle.setText(data.donation.donationTitle);
            mTextDate.setText(String.valueOf(data.donatorStatus));
            mTextNominal.setText("Rp. " + data.donationAmount + "000,00");
            Glide.with(context).load(data.donation.donationImage).into(ivPosterItem);

            mBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go_subdetail = new Intent(context, SubdetailActivity.class);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_SUBDETAIL, "hstry");
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA, data);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA2, data.user);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA3, data.donation);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA4, data.paymentmethod);
                    context.startActivity(go_subdetail);
                }
            });
        }
    }
}

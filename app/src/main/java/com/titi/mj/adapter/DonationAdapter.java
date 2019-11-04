package com.titi.mj.adapter;

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
import com.titi.mj.activity.DetailActivity;
import com.titi.mj.activity.SubdetailActivity;
import com.titi.mj.fragment.home.DetailFragment;
import com.titi.mj.model.DonationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.MyViewHolder> {
    private List<DonationResponse.Data> list;
    private Context context;

    public DonationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_donation, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setListData(List<DonationResponse.Data> data) {
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
        TextView mTextTitle, mTextNominal, mTextDeadline, mTextDeadlineDummy;
        ImageView ivPosterItem;
        ConstraintLayout background;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.background_item);
            mTextTitle = itemView.findViewById(R.id.tv_title_item);
            mTextNominal = itemView.findViewById(R.id.tv_nominal_item);
            mTextDeadline = itemView.findViewById(R.id.tv_deadline_item);
            mTextDeadlineDummy = itemView.findViewById(R.id.tv_dummy_2_item);
            ivPosterItem = itemView.findViewById(R.id.iv_poster_item);
        }

        void onBind(final DonationResponse.Data data) {
            Calendar calendar = Calendar.getInstance();
            Calendar ccalendar = Calendar.getInstance();
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-mm-dd").parse(data.donationEnd.substring(0, 10)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int int_deadline = calendar.get(Calendar.DAY_OF_MONTH) - ccalendar.get(Calendar.DAY_OF_MONTH);

            if (int_deadline < 0) {
                mTextDeadline.setText(String.valueOf(int_deadline).substring(1));
                mTextDeadlineDummy.setText("Hari lalu");
            } else {
                mTextDeadline.setText(String.valueOf(int_deadline));
                mTextDeadlineDummy.setText("Hari Lagi");
            }

            mTextNominal.setText("Rp. " + data.donationReceived);
            mTextTitle.setText(data.donationTitle);
            Glide.with(context).load(BuildConfig.HST + "image/" + data.donationImage).into(ivPosterItem);

            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go_subdetail = new Intent(context, SubdetailActivity.class);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_SUBDETAIL, "dntn");
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA, data);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA2, data.users);
                    go_subdetail.putExtra(SubdetailActivity.EXTRA_DATA3, data.categories);
                    context.startActivity(go_subdetail);
                }
            });
        }
    }
}

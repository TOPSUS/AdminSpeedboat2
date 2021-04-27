package com.espeedboat.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.fragment.ReviewDetailFragment;
import com.espeedboat.admin.holders.ReviewViewHolder;
import com.espeedboat.admin.model.ReviewList;
import com.espeedboat.admin.utils.Constants;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder>{

    private final List<ReviewList> dataList;
    private Context context;

    public ReviewAdapter(List<ReviewList> list) {
        this.dataList = list;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_review, parent, false);
        this.context = view.getContext();

        return new ReviewViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        setData(holder, dataList.get(position));
        holder.itemView.setOnClickListener(v -> { itemClickListener(v, dataList.get(position)); });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    private void setData(@NonNull ReviewViewHolder holder, ReviewList data) {
        holder.setEmail(data.getEmail());
        holder.setTanggal(data.getTanggal());
        holder.setReview(data.getReview());
        holder.setScoreText(data.getScore().toString());
        holder.setScore(data.getScore());
        holder.setImageUser(data.getImage());
    }

    private void itemClickListener(View v, ReviewList item) {
        ReviewDetailFragment rdFragment = new ReviewDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RD_ID, item.getId());
        rdFragment.setArguments(bundle);

        FragmentManager mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.content, rdFragment, rdFragment.getTag());

        mFragmentTransaction.commit();
    }
}

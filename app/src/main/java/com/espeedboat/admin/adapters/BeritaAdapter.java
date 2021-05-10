package com.espeedboat.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.WebViewActivity;
import com.espeedboat.admin.fragment.ReviewDetailFragment;
import com.espeedboat.admin.holders.BeritaHolder;
import com.espeedboat.admin.holders.ReviewViewHolder;
import com.espeedboat.admin.holders.TransaksiViewHolder;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Berita;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.ReviewList;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaHolder> {
    private final List<Berita> beritas;
    private Context context;

    public BeritaAdapter (List<Berita> beritas) {
        this.beritas = beritas;
    }

    @NonNull
    @Override
    public BeritaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_berita, parent, false);
        this.context = view.getContext();

        return new BeritaHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BeritaHolder holder, int position) {
        setData(holder, beritas.get(position));
    }

    @Override
    public int getItemCount() {
        return (beritas != null) ? beritas.size() : 0;
    }

    private void setData(@NonNull BeritaHolder holder, Berita data) {
        holder.setJudul(data.getJudul());
        holder.setCreated(data.getCreated());
        holder.setImage(data.getFotoBeritaUrl());
        holder.itemView.findViewById(R.id.lihatberita).setOnClickListener(v -> { itemClickListener(v, data); });
    }

    private void itemClickListener(View v, Berita item) {
        Intent i = new Intent(context, WebViewActivity.class);
        String strName = "admin.espeedboat.xyz/berita/public/pelabuhan/"+item.getId();
        i.putExtra("URL", strName);
        context.startActivity(i);
    }
}

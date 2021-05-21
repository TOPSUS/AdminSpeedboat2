package com.espeedboat.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.fragment.TransaksiDetailFragment;
import com.espeedboat.admin.holders.HomeTransaksiViewHolder;
import com.espeedboat.admin.holders.TransaksiViewHolder;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.utils.Constants;

import java.util.List;

public class HomeTransaksiAdapter extends RecyclerView.Adapter<HomeTransaksiViewHolder> {
    private final List<Transaksi> dataList;
    private Context context;

    public HomeTransaksiAdapter (List<Transaksi> list) {
        this.dataList = list;
        Log.d("list count", String.valueOf(list.size()));
    }

    @NonNull
    @Override
    public HomeTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaksi_data, parent, false);
        this.context = view.getContext();

        return new HomeTransaksiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeTransaksiViewHolder holder, int position) {
        setData(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setData(@NonNull HomeTransaksiViewHolder holder, Transaksi item, int pos) {
        holder.setUsername(item.getUsername());
        holder.setJumlah(item.getPerson());
        holder.setStatus(item.getStatus());
        holder.setKodeAsal(item.getAsal());
        holder.setKodeTujuan(item.getTujuan());
        holder.setTanggalAsal(item.getTanggalBerangkat());
        holder.setTanggalTujuan(item.getTanggalSampai());
        holder.setWaktuAsal(item.getJamBerangkat());
        holder.setWaktuTujuan(item.getJamSampai());
        holder.itemView.findViewById(R.id.itemWrapper).setOnClickListener(v -> { itemClickListener(v, item); });
        holder.setMarginWrapper(pos, context, getItemCount());
    }

    private void itemClickListener(View view, Transaksi transaksi) {
        TransaksiDetailFragment tdFragment = new TransaksiDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TRANSAKSI_ID, transaksi.getId());
        tdFragment.setArguments(bundle);

        FragmentManager mFragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction()
                .replace(R.id.content, tdFragment, tdFragment.getTag());
        mFragmentTransaction.addToBackStack(Constants.FRAG_MOVE);
        mFragmentTransaction.commit();
    }
}

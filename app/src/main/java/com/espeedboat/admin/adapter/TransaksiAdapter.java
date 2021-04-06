package com.espeedboat.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.holder.TransaksiViewHolder;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.ui.transaksi.TransaksiDetailFragment;
import com.espeedboat.admin.utils.Utils;

import java.util.List;


public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiViewHolder>{
    private final List<Transaksi> dataList;
    private Context context;

    public TransaksiAdapter (List<Transaksi> list) {
        this.dataList = list;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_transaksi, parent, false);
        this.context = view.getContext();

        return new TransaksiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        setData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setData(@NonNull TransaksiViewHolder holder, Transaksi item) {
        holder.setUsername(item.getUsername());
        holder.setEmail(item.getEmail());
        holder.setOrderDate(item.getTanggal());
        holder.setPerson(item.getPerson() + " Persons");
        holder.setStatus(item.getStatus());
        holder.setFrom(item.getAsal());
        holder.setFromDate(item.getTanggalBerangkat());
        holder.setFromTime(item.getJamBerangkat());
        holder.setTo(item.getTujuan());
        holder.setToDate(item.getTanggalSampai());
        holder.setToTime(item.getJamSampai());
        holder.itemView.setOnClickListener(v -> { itemClickListener(v, item); });
    }

    private void itemClickListener(View view, Transaksi transaksi) {
        TransaksiDetailFragment tdFragment = new TransaksiDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Utils.TRANSAKSI_ID, transaksi.getId());
        tdFragment.setArguments(bundle);

//        FragmentManager mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction()
//                .replace(R.id.c, tdFragment, tdFragment.getTag());
//        mFragmentTransaction.commit();
    }
}

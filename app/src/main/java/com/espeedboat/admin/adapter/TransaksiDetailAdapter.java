package com.espeedboat.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.espeedboat.admin.R;
import com.espeedboat.admin.holder.TicketHolder;
import com.espeedboat.admin.model.TransaksiDetail;

import java.util.List;

public class TransaksiDetailAdapter  extends RecyclerView.Adapter<TicketHolder>{
    private final List<TransaksiDetail> dataList;

    public TransaksiDetailAdapter (List<TransaksiDetail> list) {
        this.dataList = list;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_ticket, parent, false);

        return new TicketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        setData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    private void setData(TicketHolder holder, TransaksiDetail data) {
        holder.setName(data.getNama());
        holder.setCode(data.getKodeTiket());
        holder.setNumberIdCard(data.getNomorId());
        holder.setStatus(data.getStatus());
    }
}

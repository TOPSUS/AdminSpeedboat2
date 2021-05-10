package com.espeedboat.admin.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.utils.Util;

public class HomeTransaksiViewHolder extends RecyclerView.ViewHolder {
    private TextView username, jumlah, kodeAsal, kodeTujuan, tanggalAsal, tanggalTujuan, waktuAsal, waktuTujuan;
    private ImageView status;
    private CardView wrapper;

    public HomeTransaksiViewHolder(@NonNull View itemView) {
        super(itemView);
        getIds();
    }

    private void getIds() {
        username = (TextView) itemView.findViewById(R.id.username);
        jumlah = (TextView) itemView.findViewById(R.id.jumlah);
        kodeAsal = (TextView) itemView.findViewById(R.id.kode_asal);
        kodeTujuan = (TextView) itemView.findViewById(R.id.kode_tujuan);
        tanggalAsal = (TextView) itemView.findViewById(R.id.tanggal_asal);
        tanggalTujuan = (TextView) itemView.findViewById(R.id.tanggal_tujuan);
        waktuAsal = (TextView) itemView.findViewById(R.id.jam_asal);
        waktuTujuan = (TextView) itemView.findViewById(R.id.jam_tujuan);
        status = (ImageView) itemView.findViewById(R.id.status);
        wrapper = (CardView) itemView.findViewById(R.id.wrapper);
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public TextView getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah.setText(jumlah + " Person");
    }

    public TextView getKodeAsal() {
        return kodeAsal;
    }

    public void setKodeAsal(String kodeAsal) {
        this.kodeAsal.setText(kodeAsal);
    }

    public TextView getKodeTujuan() {
        return kodeTujuan;
    }

    public void setKodeTujuan(String kodeTujuan) {
        this.kodeTujuan.setText(kodeTujuan);
    }

    public TextView getTanggalAsal() {
        return tanggalAsal;
    }

    public void setTanggalAsal(String tanggalAsal) {
        this.tanggalAsal.setText(tanggalAsal);
    }

    public TextView getTanggalTujuan() {
        return tanggalTujuan;
    }

    public void setTanggalTujuan(String tanggalTujuan) {
        this.tanggalTujuan.setText(tanggalTujuan);
    }

    public TextView getWaktuAsal() {
        return waktuAsal;
    }

    public void setWaktuAsal(String waktuAsal) {
        this.waktuAsal.setText(waktuAsal);
    }

    public TextView getWaktuTujuan() {
        return waktuTujuan;
    }

    public void setWaktuTujuan(String waktuTujuan) {
        this.waktuTujuan.setText(waktuTujuan);
    }

    public ImageView getStatus() {
        return status;
    }

    public void setStatus(String status) {
        int source = R.drawable.ic_transaksi_used;
        if (status.equals("terkonfirmasi")) {
            source = R.drawable.ic_transaksi_success;
        } else if (status.equals("digunakan")) {
            source = R.drawable.ic_transaksi_used;
        } else if (status.equals("menunggu konfirmasi")) {
            source = R.drawable.ic_transaksi_waiting;
        } else if (status.equals("menunggu pembayaran")) {
            source = R.drawable.ic_transaksi_payment;
        } else if (status.equals("dibatalkan")) {
            source = R.drawable.ic_transaksi_canceled;
        } else if (status.equals("expired")) {
            source = R.drawable.ic_transaksi_expired;
        }
        this.status.setImageResource(source);
    }

    public void setMarginWrapper(int pos, Context con, int last) {
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) wrapper.getLayoutParams();
        if (pos == 0) {
            layoutParams.setMargins(Util.dpToPx(24, con), Util.dpToPx(4, con), Util.dpToPx(12, con), Util.dpToPx(12, con));
        } else if (pos == (last - 1)) {
            layoutParams.setMargins(Util.dpToPx(12, con), Util.dpToPx(4, con), Util.dpToPx(24, con), Util.dpToPx(12, con));
        } else {
            layoutParams.setMargins(Util.dpToPx(12, con), Util.dpToPx(4, con), Util.dpToPx(12, con), Util.dpToPx(12, con));
        }
        wrapper.requestLayout();
    }
}

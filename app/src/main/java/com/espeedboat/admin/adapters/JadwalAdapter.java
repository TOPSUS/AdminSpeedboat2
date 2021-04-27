package com.espeedboat.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Jadwal;
import com.espeedboat.admin.model.Kapal;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JadwalAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private UpdateListener updateListener;
    private List<Jadwal> listJadwal;
    public JadwalAdapter (Context context, List<Jadwal> jadwals, UpdateListener updateListener) {
        this.context = context;
        this.listJadwal = jadwals;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.updateListener = updateListener;
    }

    @Override
    public int getCount() {
        return listJadwal.size();
    }

    @Override
    public Object getItem(int position) {
        return listJadwal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listJadwal.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JadwalAdapter.Holder holder = new JadwalAdapter.Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.list_jadwal, null);

        // init
        holder.itemLay = rowView.findViewById(R.id.card_wrapper);
        holder.nama = rowView.findViewById(R.id.nama_kapal);
        holder.estimasi = rowView.findViewById(R.id.estimasi_waktu);
        holder.tanggal = rowView.findViewById(R.id.tanggal);
        holder.jamBerangkat = rowView.findViewById(R.id.jam_berangkat);
        holder.namaTujuan = rowView.findViewById(R.id.nama_pelabuhan_tujuan);
        holder.namaAsal = rowView.findViewById(R.id.nama_pelabuhan_asal);
        holder.kodeTujuan = rowView.findViewById(R.id.kode_pelabuhan_tujuan);
        holder.kodeAsal = rowView.findViewById(R.id.kode_pelabuhan_asal);
        holder.harga = rowView.findViewById(R.id.harga);

        holder.nama.setText(listJadwal.get(position).getNamaKapal());
        holder.estimasi.setText(String.valueOf(listJadwal.get(position).getEstimasiWaktu()) + " Minutes");
        holder.tanggal.setText(listJadwal.get(position).getTanggal());
        holder.jamBerangkat.setText(listJadwal.get(position).getWaktu());
        holder.namaTujuan.setText(listJadwal.get(position).getNamaTujuan());
        holder.kodeTujuan.setText(listJadwal.get(position).getKodeTujuan());
        holder.namaAsal.setText(listJadwal.get(position).getNamaAsal());
        holder.kodeAsal.setText(listJadwal.get(position).getKodeAsal());
        holder.harga.setText("IDR " + String.valueOf(listJadwal.get(position).getHarga()));


        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 0, 24, 16);
        rowView.setLayoutParams(layoutParams);

        return rowView;
    }

    public class Holder {
        MaterialCardView itemLay;
        TextView nama, estimasi, tanggal, jamBerangkat, namaTujuan, namaAsal, kodeTujuan, kodeAsal, harga;
    }

    public void updateData (List<Jadwal> jadwals) {
        this.listJadwal = jadwals;
    }
}

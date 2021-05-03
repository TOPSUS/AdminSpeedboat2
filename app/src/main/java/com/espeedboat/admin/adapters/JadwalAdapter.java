package com.espeedboat.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.CreateJadwalActivity;
import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Jadwal;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.JadwalService;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

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
        holder.expand = rowView.findViewById(R.id.expandCard);
        holder.nama = rowView.findViewById(R.id.nama_kapal);
        holder.estimasi = rowView.findViewById(R.id.estimasi_waktu);
        holder.tanggal = rowView.findViewById(R.id.tanggal);
        holder.jamBerangkat = rowView.findViewById(R.id.jam_berangkat);
        holder.namaTujuan = rowView.findViewById(R.id.nama_pelabuhan_tujuan);
        holder.namaAsal = rowView.findViewById(R.id.nama_pelabuhan_asal);
        holder.kodeTujuan = rowView.findViewById(R.id.kode_pelabuhan_tujuan);
        holder.kodeAsal = rowView.findViewById(R.id.kode_pelabuhan_asal);
        holder.harga = rowView.findViewById(R.id.harga);
        holder.delete = rowView.findViewById(R.id.delete);
        holder.edit = rowView.findViewById(R.id.edit);

        holder.nama.setText(listJadwal.get(position).getNamaKapal());
        holder.estimasi.setText(String.valueOf(listJadwal.get(position).getEstimasiWaktu()) + " Minutes");
        holder.tanggal.setText(listJadwal.get(position).getTanggal());
        holder.jamBerangkat.setText(listJadwal.get(position).getWaktu());
        holder.namaTujuan.setText(listJadwal.get(position).getNamaTujuan());
        holder.kodeTujuan.setText(listJadwal.get(position).getKodeTujuan());
        holder.namaAsal.setText(listJadwal.get(position).getNamaAsal());
        holder.kodeAsal.setText(listJadwal.get(position).getKodeAsal());
        holder.harga.setText("IDR " + String.valueOf(listJadwal.get(position).getHarga()));

        holder.itemLay.setOnClickListener(v -> {
            if (holder.expand.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.itemLay, new AutoTransition());
                holder.expand.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(holder.itemLay, new AutoTransition());
                holder.expand.setVisibility(View.GONE);
            }
        });

        holder.delete.setOnClickListener(v -> {
            confirmDelete(listJadwal.get(position).getId());
        });

        holder.edit.setOnClickListener(v -> {
            viewJadwal(listJadwal.get(position).getId());
        });

        return rowView;
    }

    public class Holder {
        MaterialCardView itemLay;
        RelativeLayout expand;
        MaterialButton edit, delete;
        TextView nama, estimasi, tanggal, jamBerangkat, namaTujuan, namaAsal, kodeTujuan, kodeAsal, harga;
    }

    public void updateData (List<Jadwal> jadwals) {
        this.listJadwal = jadwals;
    }

    private void viewJadwal(final int id) {
        Intent intent = new Intent(context, CreateJadwalActivity.class);
        intent.putExtra(Constants.JADWAL_ID, id);
        context.startActivity(intent);
    }

    private void confirmDelete(final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Hapus Jadwal");
        alert.setMessage("Yakin hapus Jadwal?");
        alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            // continue with delete
            JadwalService service = RetrofitClient.getClient().create(JadwalService.class);
            Call<Response> deleteJadwal = service.deleteJadwal(new SessionManager(context).getAuthToken(), id);

            deleteJadwal.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            updateListener.onValueChangedListener();
                            Toast.makeText(context, response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, response.body().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("ERROR [JadwalAdapter] ", t.getMessage());
                    Toast.makeText(context,  t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        alert.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }
}

package com.espeedboat.admin.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Kapal;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import com.espeedboat.admin.R;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;

public class KapalAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private UpdateListener updateListener;
    private List<Kapal> kapals;

    public KapalAdapter (Context context, List<Kapal> kapals, UpdateListener updateListener) {
        this.context = context;
        this.kapals = kapals;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.updateListener = updateListener;
    }

    @Override
    public int getCount() {
        return kapals.size();
    }

    @Override
    public Object getItem(int position) {
        return kapals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return kapals.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.list_kapal, null);

        // init
        holder.nama = rowView.findViewById(R.id.nama);
        holder.kapasitas = rowView.findViewById(R.id.kapasitas);
        holder.tipe = rowView.findViewById(R.id.tipe);
        holder.golongan = rowView.findViewById(R.id.golongan);
        holder.price = rowView.findViewById(R.id.harga);
        holder.remove = rowView.findViewById(R.id.remove);
        holder.itemLayout = rowView.findViewById(R.id.itemLay);
        holder.image = rowView.findViewById(R.id.img_kapal);

        // set text
        holder.nama.setText(kapals.get(position).getNama());
        holder.kapasitas.setText("Kapasitas " + kapals.get(position).getKapasitas().toString());
        holder.tipe.setText(kapals.get(position).getTipe());
        holder.golongan.setText(kapals.get(position).getGolongan().getGolongan());
        holder.price.setText("Rp. " + kapals.get(position).getGolongan().getHarga().toString() + ",-");

        //set Image
        Glide.with(context).load(kapals.get(position).getFotoUrl()).into(holder.image);

        // listener
        holder.remove.setOnClickListener(v -> {
            // do something
            confirmDelete(kapals.get(position).getId());
        });

        holder.itemLayout.setOnClickListener(v -> {
            viewKapal(kapals.get(position).getId());
        });

        return rowView;
    }

    public class Holder {
        RelativeLayout itemLayout, remove;
        CircleImageView image;
        TextView nama, kapasitas, tipe, golongan, price;
    }

    public void updateData (List<Kapal> kapals) {
        this.kapals = kapals;
    }


    private void viewKapal(final int id) {
        Intent intent = new Intent(context, CreateKapalActivity.class);
        intent.putExtra(Constants.KAPAL_ID, id);
        context.startActivity(intent);
    }

    private void confirmDelete(final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Hapus Kapal");
        alert.setMessage("Yakin hapus kapal?");
        alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            // continue with delete
            KapalService service = RetrofitClient.getClient().create(KapalService.class);
            Call<Response> deleteKapal = service.deleteKapal(new SessionManager(context).getAuthToken(), id);

            deleteKapal.enqueue(new Callback<Response>() {
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
                    Log.e("ERROR [KapalAdapter] ", t.getMessage());
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

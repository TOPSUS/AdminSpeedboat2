package com.espeedboat.admin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.UserReward;
import com.espeedboat.admin.service.RewardService;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UserRewardAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private UpdateListener updateListener;
    private List<UserReward> userRewards;
    private RewardService service;

    public UserRewardAdapter (Context context, List<UserReward> datas, UpdateListener updateListener) {
        this.context = context;
        this.userRewards = datas;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.updateListener = updateListener;
    }

    @Override
    public int getCount() {
        return userRewards.size();
    }

    @Override
    public Object getItem(int position) {
        return userRewards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userRewards.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserRewardAdapter.Holder holder = new UserRewardAdapter.Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.list_user_reward, null);

        holder.nama = rowView.findViewById(R.id.nama);
        holder.telepon = rowView.findViewById(R.id.telp);
        holder.alamat = rowView.findViewById(R.id.alamat);
        holder.status = rowView.findViewById(R.id.status);
        holder.btn_send = rowView.findViewById(R.id.btn_send);
        holder.btn_done = rowView.findViewById(R.id.btn_done);
        holder.wrapper = rowView.findViewById(R.id.wrapper);

        service = RetrofitClient.getClient().create(RewardService.class);

        String status = userRewards.get(position).getStatus();
        if (status.equals("selesai")) {
            holder.wrapper.setVisibility(View.GONE);
        } else if (status.equals("dikirim")) {
            holder.btn_send.setVisibility(View.GONE);
            holder.wrapper.setVisibility(View.VISIBLE);
            holder.btn_done.setVisibility(View.VISIBLE);
        } else if (status.equals("menunggu konfirmasi")) {
            holder.btn_done.setVisibility(View.GONE);
            holder.wrapper.setVisibility(View.VISIBLE);
            holder.btn_send.setVisibility(View.VISIBLE);
        }

        holder.nama.setText(userRewards.get(position).getNama());
        holder.telepon.setText(userRewards.get(position).getTelepon());
        holder.alamat.setText(userRewards.get(position).getAlamat());
        holder.status.setText("status : "+userRewards.get(position).getStatus());

        holder.btn_send.setOnClickListener(v -> {
            send(position);
        });

        holder.btn_done.setOnClickListener(v -> {
            done(position);
        });

        return rowView;
    }

    public class Holder {
        TextView nama, telepon, alamat, status;
        MaterialButton btn_send, btn_done;
        RelativeLayout wrapper;
    }

    public void updateData (List<UserReward> datas) {
        this.userRewards = datas;
    }

    private void send(int pos) {
        Call<Response> sendData = service.sendUserRewards(new SessionManager(context).getAuthToken(), userRewards.get(pos).getId());

        sendData.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getStatus() == 200) {
                    updateListener.onValueChangedListener();
                    Toast.makeText(context, response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response.body().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void done(int pos) {
        Call<Response> sendData = service.doneUserRewards(new SessionManager(context).getAuthToken(), userRewards.get(pos).getId());

        sendData.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getStatus() == 200) {
                    updateListener.onValueChangedListener();
                    Toast.makeText(context, response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response.body().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

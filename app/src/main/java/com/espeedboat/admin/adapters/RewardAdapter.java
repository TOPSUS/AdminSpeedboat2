package com.espeedboat.admin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Slide;
import android.transition.Transition;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.fragment.TransaksiDetailFragment;
import com.espeedboat.admin.fragment.UserRewardFragment;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Reward;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.RewardService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.Endpoint;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class RewardAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private UpdateListener updateListener;
    private List<Reward> rewards;

    public RewardAdapter (Context context, List<Reward> rewards, UpdateListener updateListener) {
        this.context = context;
        this.rewards = rewards;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.updateListener = updateListener;
    }
    @Override
    public int getCount() {
        return rewards.size();
    }

    @Override
    public Object getItem(int position) {
        return rewards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rewards.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RewardAdapter.Holder holder = new RewardAdapter.Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.list_reward, null);

        // init
        holder.itemLay = rowView.findViewById(R.id.card);
        holder.expand = rowView.findViewById(R.id.expandCard);
        holder.nama = rowView.findViewById(R.id.nama);
        holder.poin = rowView.findViewById(R.id.poin);
        holder.reward = rowView.findViewById(R.id.reward);
        holder.masa_berlaku = rowView.findViewById(R.id.masa_berlaku);
        holder.imageReward = rowView.findViewById(R.id.image_reward);
        holder.show = rowView.findViewById(R.id.btn_show);
        holder.delete = rowView.findViewById(R.id.btn_delete);

        // set text
        holder.nama.setText(rewards.get(position).getSpeedboat());
        holder.poin.setText("Minimal " + rewards.get(position).getMinimalPoint() + " Poin");
        holder.reward.setText(rewards.get(position).getReward());
        holder.masa_berlaku.setText(rewards.get(position).getReward());

        holder.itemLay.setOnClickListener(v -> {
            if (holder.expand.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.itemLay, new AutoTransition());
                holder.expand.setVisibility(View.VISIBLE);
            } else {
//                TransitionManager.beginDelayedTransition(holder.itemLay, new AutoTransition());
                holder.expand.setVisibility(View.GONE);
            }
        });

        //set Image
        Glide.with(context).load(Endpoint.URL + rewards.get(position).getFoto()).error(R.drawable.no_profile).into(holder.imageReward);

        holder.delete.setOnClickListener(v -> {
            // do something
            confirmDelete(rewards.get(position).getId());
        });

        holder.show.setOnClickListener(v -> {
            UserRewardFragment uRFragment = new UserRewardFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.REWARD_ID, rewards.get(position).getId());
            uRFragment.setArguments(bundle);

            FragmentManager mFragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, uRFragment, uRFragment.getTag());
            mFragmentTransaction.addToBackStack("list reward");
            mFragmentTransaction.commit();
        });

        return rowView;
    }

    public class Holder {
        MaterialCardView itemLay;
        MaterialButton show, delete;
        ImageView imageReward;
        RelativeLayout expand;
        TextView nama, poin, reward, masa_berlaku;
    }

    public void updateData (List<Reward> rewards) {
        this.rewards = rewards;
    }

    private void confirmDelete(final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Hapus Reward");
        alert.setMessage("Yakin hapus Reward?");
        alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            // continue with delete
            RewardService service = RetrofitClient.getClient().create(RewardService.class);
            Call<Response> deleteReward = service.deleteReward(new SessionManager(context).getAuthToken(), id);

            deleteReward.enqueue(new Callback<Response>() {
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

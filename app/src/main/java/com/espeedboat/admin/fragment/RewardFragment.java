package com.espeedboat.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.espeedboat.admin.CreateRewardActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.adapters.KapalAdapter;
import com.espeedboat.admin.adapters.RewardAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ChangeBottomNav;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Reward;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.RewardService;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RewardFragment extends Fragment implements UpdateListener {
    private View view, empty;
    private RewardAdapter rewardAdapter;
    private List<Reward> rewards;
    private RewardService service;
    private FloatingActionButton add;
    private SessionManager sessionManager;
    private ListView listView;
    private Context context;
    ShowBackButton showBackButton;
    ChangeBottomNav changeBottomNav;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        showBackButton = (ShowBackButton) context;
        changeBottomNav = (ChangeBottomNav) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reward, container, false);

        init();
        getReward();
        clickListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getReward();
    }

    private void init() {
        showBackButton.showBackButton(false);
        listView = view.findViewById(R.id.listview);
        sessionManager = new SessionManager(view.getContext());
        empty = view.findViewById(R.id.empty_wrapper);
        add = view.findViewById(R.id.fab);
        context = getActivity();
        service = RetrofitClient.getClient().create(RewardService.class);
        rewards = new ArrayList<>();
        rewardAdapter = new RewardAdapter(context, rewards, RewardFragment.this);
        add = view.findViewById(R.id.fabAdd);
        changeBottomNav.setBottomNav(R.id.spacer);
    }

    private void clickListener() {
        add.setOnClickListener(v -> {
            Intent i = new Intent(context, CreateRewardActivity.class);
            startActivity(i);
        });
    }

    private void getReward() {
        Call<Response> getReward = service.getRewards(sessionManager.getAuthToken());

        getReward.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        if (data.getReward().size() > 0) {
                            listView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);

                            listView.setAdapter(rewardAdapter);
                            rewardAdapter.updateData(data.getReward());
                            rewardAdapter.notifyDataSetChanged();
                        } else {
                            listView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(context, response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error Status !200",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [RewardFragment] ", t.getMessage());
                Toast.makeText(context,  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValueChangedListener() {
        getReward();
    }
}
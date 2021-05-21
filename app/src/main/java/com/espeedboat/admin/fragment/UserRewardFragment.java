package com.espeedboat.admin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.espeedboat.admin.R;
import com.espeedboat.admin.adapters.TransaksiDetailAdapter;
import com.espeedboat.admin.adapters.UserRewardAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ChangeBottomNav;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.UserReward;
import com.espeedboat.admin.service.RewardService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UserRewardFragment extends Fragment implements UpdateListener {
    private Context context;
    private View view, empty;
    private int reward_id = 0;
    private RewardService service;
    private ListView listView;
    private UserRewardAdapter adapter;
    private List<UserReward> userRewardList;
    ShowBackButton showBackButton;
    ChangeBottomNav changeBottomNav;
    ToolbarTitle toolbarTitleCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        showBackButton = (ShowBackButton) context;
        changeBottomNav = (ChangeBottomNav) context;
        toolbarTitleCallback = (ToolbarTitle) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.reward_id = getArguments().getInt(Constants.REWARD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_reward, container, false);

        init();

        if (this.reward_id != 0) {
            getUserReward(this.reward_id);
        }

        return view;
    }

    private void init() {
        showBackButton.showBackButton(true);
        context = getActivity();
        listView = view.findViewById(R.id.listview);
        empty = view.findViewById(R.id.empty_wrapper);
        userRewardList = new ArrayList<>();
        toolbarTitleCallback.setToolbarTitle("User Reward");
        service = RetrofitClient.getClient().create(RewardService.class);
        adapter = new UserRewardAdapter(context, userRewardList, UserRewardFragment.this);
    }

    private void getUserReward(int id) {
        Call<Response> getUsers = service.getUserRewards(new SessionManager(view.getContext()).getAuthToken(), id);

        getUsers.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        if (data.getUserReward().size() > 0) {
                            listView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);

                            listView.setAdapter(adapter);
                            adapter.updateData(data.getUserReward());
                            adapter.notifyDataSetChanged();
                        } else {
                            listView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(context, response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        listView.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listView.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValueChangedListener() {
        if (this.reward_id != 0) {
            getUserReward(this.reward_id);
        }
    }
}

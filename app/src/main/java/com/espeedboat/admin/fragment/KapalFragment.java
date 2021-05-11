package com.espeedboat.admin.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.adapters.KapalAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ChangeBottomNav;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.ReviewService;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class KapalFragment extends Fragment implements UpdateListener {

    private View view, empty;
    private ListView listView;
    private KapalService service;
    private SessionManager sessionManager;
    private Context context;
    private KapalAdapter kapalAdapter;
    private List<Kapal> kapals;
    private FloatingActionButton add;
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
        view = inflater.inflate(R.layout.fragment_kapal, container, false);

        init();

        getKapal();

        clickListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getKapal();
    }

    private void init() {
        showBackButton.showBackButton(false);
        listView = view.findViewById(R.id.listview);
        empty = view.findViewById(R.id.empty_wrapper);
        add = view.findViewById(R.id.fab);
        context = getActivity();
        service = RetrofitClient.getClient().create(KapalService.class);
        sessionManager = new SessionManager(view.getContext());
        kapals = new ArrayList<>();
        kapalAdapter = new KapalAdapter(context, kapals, KapalFragment.this);
        changeBottomNav.setBottomNav(R.id.nav_kapal);
    }

    private void clickListener() {
//        add.setOnClickListener(v -> {
//            Intent i = new Intent(context, CreateKapalActivity.class);
//            startActivity(i);
//        });
    }

    private void getKapal() {
        Call<Response> getKapalList = service.showKapal(sessionManager.getAuthToken());

        getKapalList.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        Data data = response.body().getData();
                        if (data.getListKapal().size() > 0) {
                            listView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);

                            listView.setAdapter(kapalAdapter);
                            kapalAdapter.updateData(data.getListKapal());
                            kapalAdapter.notifyDataSetChanged();
                        } else {
                            listView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }

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
                Log.e("ERROR [KapalFragment] ", t.getMessage());
                Toast.makeText(context,  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValueChangedListener() {
        getKapal();
    }
}
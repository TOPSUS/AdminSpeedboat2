package com.espeedboat.admin.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.CreateJadwalActivity;
import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.adapters.JadwalAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Jadwal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.JadwalService;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class JadwalFragment extends Fragment implements UpdateListener {

    private View view;
    private ListView jadwalList;
    private SessionManager sessionManager;
    private Context context;
    private JadwalService service;
    private LinearLayout empty;
    private JadwalAdapter jadwalAdapter;
    private List<Jadwal> jadwals;
    private FloatingActionButton add;
    ShowBackButton showBackButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        showBackButton = (ShowBackButton) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jadwal, container, false);
        context = getActivity();
        jadwals = new ArrayList<>();

        init();
        getJadwal();
        clickListener();

        return view;
    }

    private void init () {
        sessionManager = new SessionManager(view.getContext());
        service = RetrofitClient.getClient().create(JadwalService.class);
        jadwalList = view.findViewById(R.id.listview);
        empty = view.findViewById(R.id.empty_wrapper);
        add = view.findViewById(R.id.fab);
        jadwalAdapter = new JadwalAdapter(getActivity(), jadwals, JadwalFragment.this);
        showBackButton.showBackButton(false);
    }

    private void clickListener() {
        add.setOnClickListener(v -> {
            Intent i = new Intent(context, CreateJadwalActivity.class);
            startActivity(i);
        });
    }

    private void getJadwal() {
        Call<Response> getJadwals = service.listJadwal(sessionManager.getAuthToken());

        getJadwals.enqueue(new Callback<Response>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();

                        if (data.getListJadwal().size() > 0) {
                            empty.setVisibility(View.GONE);
                            jadwalList.setVisibility(View.VISIBLE);

                            jadwalList.setDivider(new ColorDrawable(R.color.transparent));
                            jadwalList.setDividerHeight(0);

                            jadwalList.setAdapter(jadwalAdapter);
                            jadwalAdapter.updateData(data.getListJadwal());
                            jadwalAdapter.notifyDataSetChanged();
                        } else {
                            jadwalList.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context,  "Response Status Code Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context,  "Failed to get Jadwal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("Failure Jadwal", t.getMessage().toString());
                Toast.makeText(context,  "Failure Jadwal", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValueChangedListener() {
        getJadwal();
    }
}
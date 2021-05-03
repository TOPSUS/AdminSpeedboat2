package com.espeedboat.admin.fragment;

import android.content.Context;
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
import com.espeedboat.admin.adapters.JadwalAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Jadwal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.JadwalService;
import com.espeedboat.admin.utils.SessionManager;

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

        return view;
    }

    private void init () {
        sessionManager = new SessionManager(view.getContext());
        service = RetrofitClient.getClient().create(JadwalService.class);
        jadwalList = view.findViewById(R.id.listview);
        empty = view.findViewById(R.id.empty_wrapper);
        jadwalAdapter = new JadwalAdapter(getActivity(), jadwals, JadwalFragment.this);
        showBackButton.showBackButton(false);
    }

    private void getJadwal() {
        Call<Response> getJadwals = service.listJadwal(sessionManager.getAuthToken());

        getJadwals.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();

                        Log.d("error jadwal", data.getListJadwal().toString());
                        if (data.getListJadwal().size() > 0) {
                            empty.setVisibility(View.GONE);
                            jadwalList.setVisibility(View.VISIBLE);

                            jadwalList.setAdapter(jadwalAdapter);
                            jadwalAdapter.updateData(data.getListJadwal());
                            jadwalAdapter.notifyDataSetChanged();
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
                Toast.makeText(context,  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValueChangedListener() {
        getJadwal();
    }
}
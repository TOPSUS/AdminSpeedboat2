package com.espeedboat.admin.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.espeedboat.admin.R;
import com.espeedboat.admin.adapters.BeritaAdapter;
import com.espeedboat.admin.adapters.KapalAdapter;
import com.espeedboat.admin.adapters.TransaksiAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.ChangeBottomNav;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.interfaces.UpdateListener;
import com.espeedboat.admin.model.Berita;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.service.BeritaService;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.TransaksiService;
import com.espeedboat.admin.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class BeritaFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private BeritaService service;
    ShowBackButton showBackButton;
    ToolbarTitle toolbarTitleCallback;
    ChangeBottomNav changeBottomNav;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        showBackButton = (ShowBackButton) context;
        toolbarTitleCallback = (ToolbarTitle) context;
        changeBottomNav = (ChangeBottomNav) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_berita, container, false);
        toolbarTitleCallback.setToolbarTitle("Berita Pelabuhan");
        recyclerView = view.findViewById(R.id.recycle_berita);
        service = RetrofitClient.getClient().create(BeritaService.class);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        showBackButton.showBackButton(true);
        changeBottomNav.setBottomNav(R.id.spacer);

        getData();

        return view;
    }

    private void getData() {
        Call<Response> getBeritaList = service.showBerita(new SessionManager(view.getContext()).getAuthToken());

        getBeritaList.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {

                        List<Berita> beritaList = response.body().getData().getListBerita();
                        recyclerView.setAdapter(new BeritaAdapter(beritaList));
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
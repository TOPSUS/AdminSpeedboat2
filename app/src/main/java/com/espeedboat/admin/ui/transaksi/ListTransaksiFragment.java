package com.espeedboat.admin.ui.transaksi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.espeedboat.admin.R;
import com.espeedboat.admin.adapter.TransaksiAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.service.TransaksiService;
import com.espeedboat.admin.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListTransaksiFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private TransaksiService service;
    private String type;

    public ListTransaksiFragment(String type) {
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_transaksi, container, false);
        recyclerView = view.findViewById(R.id.recycle_transaksi);
        service = RetrofitClient.getClient().create(TransaksiService.class);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        getData();

        return view;
    }

    private void getData() {
        Call<Response> getTransaksi;
        if (type == "done") {
            getTransaksi = service.getTransaksiSelesai(new SessionManager(view.getContext()).getAuthToken());
        } else {
            getTransaksi = service.getTransaksiProses(new SessionManager(view.getContext()).getAuthToken());
        }

        getTransaksi.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        List<Transaksi> transaksiList = response.body().getData().getTransaksiList();
                        recyclerView.setAdapter(new TransaksiAdapter(transaksiList));
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
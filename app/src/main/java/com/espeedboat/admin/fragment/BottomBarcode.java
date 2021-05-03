package com.espeedboat.admin.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Tiket;
import com.espeedboat.admin.service.TransaksiService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;
import com.espeedboat.admin.utils.Util;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;

public class BottomBarcode  extends BottomSheetDialogFragment {
    private View view;
    private TextView username, noId, status, from, fromDate, fromTime, to, toDate,
            toTime, priceTiket, infoTiket;
    private String kode_tiket;
    private Boolean canApprove = false;
    private TransaksiService service;
    private LinearLayout status_wrapper;
    private Button btn_approve;
    private int tiket_id = 0;

    public static BottomBarcode newInstance() {
        return new BottomBarcode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_barcode_data, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = RetrofitClient.getClient().create(TransaksiService.class);

        getIds();

        if (getArguments() != null) {
            this.kode_tiket = getArguments().getString(Constants.KODE_TIKET);
            updateData(kode_tiket);
        }
    }

    private void updateData(String url) {
        Call<Response> showTiket = service.showTiket(new SessionManager(view.getContext()).getAuthToken(), this.kode_tiket);

        showTiket.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        setValue(response.body().getData().getTiket());
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("Failure [Bottom Barcode]", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        this.priceTiket.setText(url);
    }

    private void getIds() {
        username = (TextView) view.findViewById(R.id.qr_username);
        noId = (TextView) view.findViewById(R.id.qr_id_card);
        status = (TextView) view.findViewById(R.id.qr_status);
        from = (TextView) view.findViewById(R.id.qr_from);
        fromDate = (TextView) view.findViewById(R.id.qr_from_date);
        fromTime = (TextView) view.findViewById(R.id.qr_from_time);
        to = (TextView) view.findViewById(R.id.qr_to);
        toDate = (TextView) view.findViewById(R.id.qr_to_date);
        toTime = (TextView) view.findViewById(R.id.qr_to_time);
        status_wrapper = (LinearLayout) view.findViewById(R.id.qr_status_wrapper);
        priceTiket = view.findViewById(R.id.qr_ticket_price);
        infoTiket = view.findViewById(R.id.qr_ticket_info);
        btn_approve = view.findViewById(R.id.qr_approve);
    }

    private void setValue(Tiket tiket) {
        tiket_id = tiket.getId();
        username.setText(tiket.getNama());
        noId.setText(tiket.getNomorId());
        status.setText(tiket.getStatusOrder());
        from.setText(tiket.getAsal());
        fromDate.setText(tiket.getTanggalBerangkat());
        fromTime.setText(tiket.getJamBerangkat());
        to.setText(tiket.getTujuan());
        toDate.setText(tiket.getTanggalSampai());
        toTime.setText(tiket.getJamSampai());
        if (tiket.getStatusTiket().equals("Used")) {
            canApprove = false;
        } else if (tiket.getStatusTiket().equals("Expired")) {
            canApprove = false;
        } else {
            canApprove = true;
        }
        setStatus(tiket.getStatusOrder());
        infoTiket.setText(tiket.getKodeTiket() + " - " + tiket.getStatusTiket());
        priceTiket.setText("IDR "+tiket.getHarga()+",-");
        buttonListener();
    }

    public void setStatus(String status) {
        String text = "Default";
        int color = R.drawable.status_default;
        if (status.equals("terkonfirmasi")) {
            text = "Success";
            color = R.drawable.status_green;
        } else if (status.equals("digunakan")) {
            text = "Used";
            color = R.drawable.status_default;
        } else if (status.equals("menunggu konfirmasi")) {
            text = "Waiting Confirm";
            color = R.drawable.status_yellow;
        } else if (status.equals("menunggu pembayaran")) {
            text = "Waiting Payment";
            color = R.drawable.status_yellow;
        } else if (status.equals("dibatalkan")) {
            text = "Canceled";
            color = R.drawable.status_red;
        } else if (status.equals("expired")) {
            text = "Expired";
            color = R.drawable.status_orange;
        }
        this.status.setText(text);
        this.status_wrapper.setBackgroundResource(color);

        if (text.equals("Success")) {
            if (canApprove) {
                btn_approve.setVisibility(View.VISIBLE);
            } else {
                btn_approve.setVisibility(View.INVISIBLE);
            }
        } else {
            btn_approve.setVisibility(View.INVISIBLE);
        }
    }

    private void buttonListener() {
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiket_id > 0) {
                    TransaksiService service = RetrofitClient.getClient().create(TransaksiService.class);
                    Call<Response> approveTiket = service.approveTiket(new SessionManager(view.getContext()).getAuthToken(), tiket_id);

                    approveTiket.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    Toast.makeText(getActivity(), response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    infoTiket.setText(kode_tiket + " - Used");
                                    btn_approve.setVisibility(View.INVISIBLE);
                                } else {
                                    Toast.makeText(getActivity(), response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Tiket not found on Database",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

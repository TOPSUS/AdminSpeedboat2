package com.espeedboat.admin.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.espeedboat.admin.R;
import com.espeedboat.admin.adapters.HomeTransaksiAdapter;
import com.espeedboat.admin.adapters.TransaksiAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.fragment.KapalFragment;
import com.espeedboat.admin.fragment.ReviewFragment;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.Transaksi;
import com.espeedboat.admin.service.DashboardService;
import com.espeedboat.admin.service.JadwalService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DashboardFragment extends Fragment {

    View view;
    BarChart barChart;
    ToolbarTitle toolbarTitleCallback;
    CircularProgressBar transaksiProgress, ratingProgress;
    RecyclerView transaksiData;
    ShowBackButton showBackButton;
    DashboardService dashboardService;
    SessionManager sessionManager;
    Context context;
    TextView totalPendapatan, totalTransaksi, totalRate, transaksiPercentage, viewAllTransaksi;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarTitleCallback = (ToolbarTitle) context;
        showBackButton = (ShowBackButton) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context = getActivity();

        setIds();

        getDataDashboard();

        menuClickListener();

        showBackButton.showBackButton(false);

        return view;
    }

    private void getDataDashboard() {
        sessionManager = new SessionManager(view.getContext());
        dashboardService = RetrofitClient.getClient().create(DashboardService.class);

        Call<Response> getData = dashboardService.dashboardData(sessionManager.getAuthToken());
        getData.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        setBarChart(data.getDashboard().getTotalPendapatan(),  data.getDashboard().getRekapPendapatan());
                        setProgressInfo(data.getDashboard().getRateReview(), data.getDashboard().getTransaksiDone(),
                                data.getDashboard().getTransaksiCount());
                        setRecentTransaksi(data.getDashboard().getTransaksiList());
                    } else {
                        Toast.makeText(context,  "Response Status Code Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context,  "Failed to get Dashboard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("Failure Dashboard", t.getMessage().toString());
                Toast.makeText(context,  "Failure Dashboard", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setIds() {
        barChart = view.findViewById(R.id.total_chart);
        transaksiProgress = view.findViewById(R.id.transaksi_progress);
        ratingProgress = view.findViewById(R.id.rating_progress);
        transaksiData = view.findViewById(R.id.transaksi_data_horizontal);
        totalPendapatan = view.findViewById(R.id.total_pendapatan);
        totalTransaksi = view.findViewById(R.id.total_transaksi);
        totalRate = view.findViewById(R.id.total_rating);
        transaksiPercentage = view.findViewById(R.id.total_transaksi_percentage);
        linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, true);
        transaksiData.setLayoutManager(linearLayoutManager);
        viewAllTransaksi = view.findViewById(R.id.btn_all_transaksi);
    }

    private void menuClickListener() {
        Button mReview = view.findViewById(R.id.menu_review);
        mReview.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Review");
            Fragment fragment = new ReviewFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });

        Button mKapal = view.findViewById(R.id.menu_kapal);
        mKapal.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Kapal");
            Fragment fragment = new KapalFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });

        Button mJadwal = view.findViewById(R.id.menu_jadwal);
        mJadwal.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Jadwal");
            Fragment fragment = new JadwalFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });

        Button mTransaksi = view.findViewById(R.id.menu_transaksi);
        mJadwal.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Transaksi");
            Fragment fragment = new ListTransaksiFragment("all");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });

        viewAllTransaksi.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Transaksi");
            Fragment fragment = new ListTransaksiFragment("all");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });
    }

    private void setBarChart(Integer inTotal, List<Integer> inRekap) {
        // Data-data yang akan ditampilkan di Chart
        ArrayList<BarEntry> dataPendapatan = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dataPendapatan.add(new BarEntry(i, 0));
        }
        if (inRekap.size() > 0) {
            dataPendapatan = new ArrayList<>();
            for (int i = 0; i < inRekap.size(); i++) {
                dataPendapatan.add(new BarEntry(i, inRekap.get(i).longValue()));
            }
        }

        ArrayList<String> dataHari = new ArrayList<>();
        dataHari.add("Senin");
        dataHari.add("Selasa");
        dataHari.add("Rabu");
        dataHari.add("Kamis");
        dataHari.add("Jumat");
        dataHari.add("Sabtu");
        dataHari.add("Minggu");

        // Pengaturan atribut bar, seperti warna dan lain-lain
        BarDataSet dataSet = new BarDataSet(dataPendapatan, "Pendapatan");
        dataSet.setColor(getResources().getColor(R.color.primary_white));
        dataSet.setDrawValues(false);


        // Membuat Bar data yang akan di set ke Chart
        BarData barData = new BarData(dataSet);

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setData(barData);
        barChart.animateY(2000);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dataHari.get((int) value);
            }
        });
        barChart.getXAxis().setTextColor(getResources().getColor(R.color.primary_white));
        barChart.getXAxis().setAxisLineColor(getResources().getColor(R.color.primary_white));


        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        totalPendapatan.setText(kursIndonesia.format(inTotal.doubleValue()));
    }

    private void setProgressInfo(Float rate, Integer done, Integer count) {

        String textTotalTransaksi = done + " / " + count;
        String textTotalRate = "Rating " + rate;
        int percentageTrans = done * 100 / count;
        float percentageRate = rate * 100 / 5;
        String textTransaksiPercentage = percentageTrans + "%";

        totalTransaksi.setText(textTotalTransaksi);
        totalRate.setText(textTotalRate);
        transaksiPercentage.setText(textTransaksiPercentage);

        transaksiProgress.setProgress(percentageTrans);
        ratingProgress.setProgress(percentageRate);
    }

    private void setRecentTransaksi(List<Transaksi> datas) {
        transaksiData.setAdapter(new HomeTransaksiAdapter(datas));
        transaksiData.getLayoutManager().scrollToPosition(datas.size()-1);
    }
}
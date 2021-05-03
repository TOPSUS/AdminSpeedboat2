package com.espeedboat.admin.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.espeedboat.admin.R;
import com.espeedboat.admin.fragment.KapalFragment;
import com.espeedboat.admin.fragment.ReviewFragment;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.utils.Constants;
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

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    View view;
    BarChart barChart;
    ToolbarTitle toolbarTitleCallback;
    CircularProgressBar transaksiProgress, ratingProgress;
    LinearLayout transaksiData;
    ShowBackButton showBackButton;

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

        setIds();

        setBarChart();

        menuClickListener();

        showBackButton.showBackButton(false);

        transaksiProgress.setProgress(50f);
        ratingProgress.setProgress(50f);

        for (int i = 0; i < 5; i++) {
            View lview = LayoutInflater.from(getActivity()).inflate(R.layout.transaksi_data, null);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_24), 0,
                        getResources().getDimensionPixelSize(R.dimen.margin_8), 0);
            } else if (i == 4) {
                layoutParams.setMargins(0, 0,
                        getResources().getDimensionPixelSize(R.dimen.margin_24), 0);
            } else {
                layoutParams.setMargins(0, 0,
                        getResources().getDimensionPixelSize(R.dimen.margin_8), 0);
            }


            lview.setLayoutParams(layoutParams);

            transaksiData.addView(lview);
        }

        return view;
    }

    private void setIds() {
        barChart = view.findViewById(R.id.total_chart);
        transaksiProgress = view.findViewById(R.id.transaksi_progress);
        ratingProgress = view.findViewById(R.id.rating_progress);
        transaksiData = view.findViewById(R.id.transaksi_data_horizontal);
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
    }

    private void setBarChart() {
        // Data-data yang akan ditampilkan di Chart
        ArrayList<BarEntry> dataPendapatan = new ArrayList<>();
        dataPendapatan.add(new BarEntry(0, 1500000));
        dataPendapatan.add(new BarEntry(1, 1430000));
        dataPendapatan.add(new BarEntry(2, 1750000));
        dataPendapatan.add(new BarEntry(3, 1390000));
        dataPendapatan.add(new BarEntry(4, 1430000));
        dataPendapatan.add(new BarEntry(5, 1750000));
        dataPendapatan.add(new BarEntry(6, 1390000));

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
    }
}
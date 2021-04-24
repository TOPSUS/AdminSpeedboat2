package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Golongan;
import com.espeedboat.admin.model.Kapal;
import com.espeedboat.admin.model.Pelabuhan;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.GolonganService;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.PelabuhanService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateKapalActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    // layouts attribute
    private AutoCompleteTextView autoCompletePelabuhan, autoCompleteTipe, autoCompleteGolongan;
    private TextInputEditText nama, kapasitas, deskripsi, contact, lamaBeroperasi;
    private Button remove, save;
    private ImageView profileToolbar, notifToolbar, back;
    private TextView title;
    private Toolbar toolbar;
    private MaterialDatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kapal_form);

        init();

        setToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt(Constants.KAPAL_ID, 0);
            if (id > 0) {
                title.setText(R.string.menu_speedboat_edit);
                getDataKapal(id);
            }
        }

        setTipeValue(null);
        setPelabuhanValue(0, 0);
        setPelabuhanListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        sessionManager = new SessionManager(getApplicationContext());
        nama = findViewById(R.id.namaForm);
        kapasitas = findViewById(R.id.kapasitasForm);
        deskripsi = findViewById(R.id.deskripsiForm);
        contact = findViewById(R.id.contactForm);
        lamaBeroperasi = findViewById(R.id.lamaOperasiForm);

        lamaBeroperasi.setOnClickListener(v -> {
            setLamaOperasiListener();
        });
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);
        back = findViewById(R.id.toolbar_back);

        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.menu_speedboat_create);

        profileToolbar.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
    }

    private void getDataKapal(int id) {
        KapalService service = RetrofitClient.getClient().create(KapalService.class);

        Call<Response> getKapal = service.viewKapal(sessionManager.getAuthToken(), id);
        getKapal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("data", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        setKapalValue(data.getKapal());
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void setKapalValue(Kapal kapal) {
        nama.setText(kapal.getNama());
        kapasitas.setText(kapal.getKapasitas().toString());
        deskripsi.setText(kapal.getDeskripsi());
        contact.setText(kapal.getContact());
        lamaBeroperasi.setText(kapal.getTanggalBeroperasi());
        setTipeValue(kapal.getTipe());
        setPelabuhanValue(kapal.getGolongan().getIdPelabuhan(), kapal.getGolongan().getId());
    }

    private void setTipeValue(String tipe) {
        String[] items = {"Speedboat", "Feri"};
        autoCompleteTipe = findViewById(R.id.autoCompleteTipe);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, items);

        if (tipe != null) {
            if (tipe.equals("speedboat")) {
                autoCompleteTipe.setText(adapter.getItem(0).toString());
            } else if (tipe.equals("feri")) {
                autoCompleteTipe.setText(adapter.getItem(1).toString());
            }
        }

        autoCompleteTipe.setAdapter(adapter);
    }

    private void setPelabuhanValue(final int selectedId, final int golonganId) {
        PelabuhanService service = RetrofitClient.getClient().create(PelabuhanService.class);
        ArrayList<String> lp = new ArrayList<>();
        autoCompletePelabuhan = findViewById(R.id.autoCompletePelabuhan);
        Call<Response> getNamaPelabuhan = service.listNamaPelabuhan(sessionManager.getAuthToken());

        getNamaPelabuhan.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                String pelabuhanString = null;
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();

                        for (Pelabuhan list : data.getPelabuhan()) {
                            if (selectedId > 0) {
                                if (list.getId() == selectedId) {
                                    pelabuhanString = list.getNama();
                                }
                            }
                            lp.add(list.getNama());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(CreateKapalActivity.this, R.layout.item_dropdown, lp);
                        if (selectedId > 0 && pelabuhanString != null && golonganId > 0) {
                            autoCompletePelabuhan.setText(pelabuhanString);
                            setGolonganValue(pelabuhanString, golonganId);
                        }
                        autoCompletePelabuhan.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [KapalFragment] ", t.getMessage());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setPelabuhanListener() {
        autoCompletePelabuhan.setOnItemClickListener((parent, view, position, id) -> {
            String nama = parent.getItemAtPosition(position).toString();
            setGolonganValue(nama, 0);
        });
    }

    private void setGolonganValue(String namaPelabuhan, Integer golonganId) {
        GolonganService gservice = RetrofitClient.getClient().create(GolonganService.class);
        ArrayList<String> golongans = new ArrayList<>();

        Call<Response> getGolonganP = gservice.golonganPelabuhan(sessionManager.getAuthToken(), namaPelabuhan);

        getGolonganP.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                String namaGolongan = null;

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        for (Golongan list : data.getGolongan()) {
                            if (list.getId() == golonganId) {
                                namaGolongan = list.getGolongan() + " (" + list.getHarga().toString() + ")";
                            }
                            golongans.add(list.getGolongan() + " (" + list.getHarga().toString() + ")" );
                        }

                        autoCompleteGolongan = findViewById(R.id.autoCompleteGolongan);
                        ArrayAdapter adapter = new ArrayAdapter(CreateKapalActivity.this, R.layout.item_dropdown, golongans);
                        if (golonganId > 0 && namaGolongan != null) {
                            autoCompleteGolongan.setText(namaGolongan);
                        }
                        autoCompleteGolongan.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [CreateKapalActivity] ", t.getMessage());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLamaOperasiListener() {
        FragmentManager fm = this.getSupportFragmentManager();
        datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
        datePicker.show(fm, "open date picker");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String date = dateFormat.format(selection);
            lamaBeroperasi.setText(date);
        });
    }
}
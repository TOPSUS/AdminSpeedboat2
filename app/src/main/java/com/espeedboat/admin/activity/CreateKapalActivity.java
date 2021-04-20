package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Golongan;
import com.espeedboat.admin.model.Pelabuhan;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.GolonganService;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.PelabuhanService;
import com.espeedboat.admin.utils.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateKapalActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompletePelabuhan, autoCompleteTipe, autoCompleteGolongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kapal_form);

        setTipeValue();
        setPelabuhanValue();
        setPelabuhanListener();
    }

    private void setTipeValue() {
        String[] items = {"Speedboat", "Feri"};
        autoCompleteTipe = findViewById(R.id.autoCompleteTipe);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, items);
        autoCompleteTipe.setAdapter(adapter);
    }

    private void setPelabuhanValue() {
        PelabuhanService service = RetrofitClient.getClient().create(PelabuhanService.class);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        ArrayList<String> lp = new ArrayList<>();

        Call<Response> getNamaPelabuhan = service.listNamaPelabuhan(sessionManager.getAuthToken());

        getNamaPelabuhan.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();

                        for (Pelabuhan list : data.getPelabuhan()) {
                            lp.add(list.getNama());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [KapalFragment] ", t.getMessage());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        autoCompletePelabuhan = findViewById(R.id.autoCompletePelabuhan);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, lp);
        autoCompletePelabuhan.setAdapter(adapter);
    }

    private void setPelabuhanListener() {
        autoCompletePelabuhan.setOnItemClickListener((parent, view, position, id) -> {
            String nama = parent.getItemAtPosition(position).toString();
            setGolonganValue(nama);
        });
    }

    private void setGolonganValue(String nama_pelabuhan) {
        GolonganService gservice = RetrofitClient.getClient().create(GolonganService.class);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        ArrayList<String> golongans = new ArrayList<>();

        Call<Response> getGolonganP = gservice.golonganPelabuhan(sessionManager.getAuthToken(), nama_pelabuhan);

        getGolonganP.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        for (Golongan list : data.getGolongan()) {
                            golongans.add(list.getGolongan() + " (" + list.getHarga().toString() + ")" );
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [CreateKapalActivity] ", t.getMessage());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        autoCompleteGolongan = findViewById(R.id.autoCompleteGolongan);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, golongans);
        autoCompleteGolongan.setAdapter(adapter);
    }
}
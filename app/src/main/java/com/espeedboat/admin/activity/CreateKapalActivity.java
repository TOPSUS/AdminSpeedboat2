package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.espeedboat.admin.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CreateKapalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kapal_form);

        initValue();
    }

    private void initValue() {
        List<String> items = Arrays.asList("Speedboat", "Feri");
        TextInputLayout tipeTextField = findViewById(R.id.tipeTextField);
    }
}
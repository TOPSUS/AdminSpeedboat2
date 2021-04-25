package com.espeedboat.admin.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
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
import com.espeedboat.admin.utils.Util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CreateKapalActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    // layouts attribute
    private AutoCompleteTextView autoCompletePelabuhan, autoCompleteTipe, autoCompleteGolongan;
    private TextInputEditText nama, kapasitas, deskripsi, contact, lamaBeroperasi;
    private Button remove, submit;
    private ImageView profileToolbar, notifToolbar, back;
    private TextView title;
    private Toolbar toolbar;
    private MaterialDatePicker datePicker;
    private RelativeLayout imageBtn;
    private ProgressDialog dialog;
    private CircleImageView imageView;
    private Uri selectedImage;
    private int idKapal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kapal_form);

        init();

        setToolbar();

        eventListener();

        setRemoveVisible(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt(Constants.KAPAL_ID, 0);
            if (id > 0) {
                title.setText(R.string.menu_speedboat_edit);
                setRemoveVisible(true);
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
        imageBtn = findViewById(R.id.image_wrapper);
        imageView = findViewById(R.id.image_kapal);
        remove = findViewById(R.id.btn_remove);
        submit = findViewById(R.id.btn_submit);
    }

    private void eventListener() {
        imageBtn.setOnClickListener(v -> {
            setCameraListener();
        });

        lamaBeroperasi.setOnClickListener(v -> {
            setLamaOperasiListener();
        });

        submit.setOnClickListener(v -> {
            if (idKapal == 0) {
                createKapal();
            } else {
                updateKapal();
            }
        });

        remove.setOnClickListener(v -> {
            confirmDelete(idKapal);
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

    private void setRemoveVisible(Boolean con) {
        if (con) {
            remove.setVisibility(View.VISIBLE);
        } else {
            remove.setVisibility(View.GONE);
        }
    }

    private void getDataKapal(int id) {
        idKapal = id;
        KapalService service = RetrofitClient.getClient().create(KapalService.class);

        Call<Response> getKapal = service.viewKapal(sessionManager.getAuthToken(), id);
        getKapal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("data kapal", response.body().toString());
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

    private void updateKapal() {
        RequestBody r_nama, r_deskripsi, r_contact, r_tipe, r_tanggal_beroperasi, r_golongan, r_kapasitas, r_method;
        String in_nama, in_deskripsi, in_contact, in_tipe, in_tanggal_beroperasi = null, in_golongan;
        int in_kapasitas;
        long date = 0;

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            date = dateFormat.parse(lamaBeroperasi.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        in_nama = nama.getText().toString();
        in_deskripsi = deskripsi.getText().toString();
        in_contact = contact.getText().toString();
        in_tipe = autoCompleteTipe.getText().toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        in_tanggal_beroperasi = df.format(date);
        in_golongan = autoCompleteGolongan.getText().toString();
        Log.d("in_golongan", in_golongan);
        in_kapasitas = Integer.parseInt(kapasitas.getText().toString());
        KapalService service = RetrofitClient.getClient().create(KapalService.class);
        MultipartBody.Part body = null;
        if (selectedImage != null) {
            String filePath = Util.getRealPathFromURIPath(selectedImage, CreateKapalActivity.this);
            File fileImage = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), fileImage);
            body = MultipartBody.Part.createFormData("image_kapal", fileImage.getName(), mFile);
        }

        r_nama = RequestBody.create(MultipartBody.FORM, in_nama);
        r_kapasitas = RequestBody.create(MultipartBody.FORM, String.valueOf(in_kapasitas));
        r_deskripsi = RequestBody.create(MultipartBody.FORM, in_deskripsi);
        r_contact = RequestBody.create(MultipartBody.FORM, in_contact);
        r_tipe = RequestBody.create(MultipartBody.FORM, in_tipe);
        r_golongan = RequestBody.create(MultipartBody.FORM, in_golongan);
        r_tanggal_beroperasi = RequestBody.create(MultipartBody.FORM, in_tanggal_beroperasi);
        r_method = RequestBody.create(MultipartBody.FORM, "PUT");

        Call<Response> updateKapal =  service.updateKapal(sessionManager.getAuthToken(), idKapal, r_nama,
                r_kapasitas, r_deskripsi, r_contact, r_tipe, r_golongan, r_tanggal_beroperasi, body);

        updateKapal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(getApplicationContext(),  response.body().getMessage(), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Log.d("Response not 200", response.message().toString());
                        Toast.makeText(getApplicationContext(),  response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("Response not success", response.message()+" "+response.code());
                    Toast.makeText(getApplicationContext(),  "Response not success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("data input", t.getMessage().toString());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void createKapal () {
        RequestBody r_nama, r_deskripsi, r_contact, r_tipe, r_tanggal_beroperasi, r_golongan, r_kapasitas;
        String in_nama, in_deskripsi, in_contact, in_tipe, in_tanggal_beroperasi = null, in_golongan;
        int in_kapasitas;
        long date = 0;

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            date = dateFormat.parse(lamaBeroperasi.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        in_nama = nama.getText().toString();
        in_deskripsi = deskripsi.getText().toString();
        in_contact = contact.getText().toString();
        in_tipe = autoCompleteTipe.getText().toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        in_tanggal_beroperasi = df.format(date);
        in_golongan = autoCompleteGolongan.getText().toString();
        in_kapasitas = Integer.parseInt(kapasitas.getText().toString());
        KapalService service = RetrofitClient.getClient().create(KapalService.class);
        if (selectedImage == null) {
            Toast.makeText(getApplicationContext(),  "Pilih Image Kapal", Toast.LENGTH_LONG).show();
        } else {
            String filePath = Util.getRealPathFromURIPath(selectedImage, CreateKapalActivity.this);
            File fileImage = new File(filePath);

            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), fileImage);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image_kapal", fileImage.getName(), mFile);
            r_nama = RequestBody.create(MultipartBody.FORM, in_nama);
            r_kapasitas = RequestBody.create(MultipartBody.FORM, String.valueOf(in_kapasitas));
            r_deskripsi = RequestBody.create(MultipartBody.FORM, in_deskripsi);
            r_contact = RequestBody.create(MultipartBody.FORM, in_contact);
            r_tipe = RequestBody.create(MultipartBody.FORM, in_tipe);
            r_golongan = RequestBody.create(MultipartBody.FORM, in_golongan);
            r_tanggal_beroperasi = RequestBody.create(MultipartBody.FORM, in_tanggal_beroperasi);

            Call<Response> createPhoto =  service.createKapalPhoto(sessionManager.getAuthToken(), r_nama,
                    r_kapasitas, r_deskripsi, r_contact, r_tipe, r_golongan,
                    r_tanggal_beroperasi, body);

            createPhoto.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(getApplicationContext(),  response.body().getMessage(), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Log.d("Response not 200", response.message().toString());
                            Toast.makeText(getApplicationContext(),  "Response not 200", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("Response not success", response.message());
                        Toast.makeText(getApplicationContext(),  "Response not success", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("data input", t.getMessage().toString());
                    Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void setKapalValue(Kapal kapal) {
        nama.setText(kapal.getNama());
        kapasitas.setText(kapal.getKapasitas().toString());
        deskripsi.setText(kapal.getDeskripsi());
        contact.setText(kapal.getContact());
        setTipeValue(kapal.getTipe());
        setPelabuhanValue(kapal.getGolongan().getIdPelabuhan(), kapal.getGolongan().getId());
        lamaBeroperasi.setText(kapal.getTanggalBeroperasi());

        //set Image
        Glide.with(this).load(kapal.getFotoUrl()).into(imageView);
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
                                namaGolongan = list.getGolongan();
                            }
//                            golongans.add(list.getGolongan() + " (" + list.getHarga().toString() + ")" );
                            golongans.add(list.getGolongan());
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
        Long tanggal = MaterialDatePicker.todayInUtcMilliseconds();
        if (!lamaBeroperasi.getText().toString().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date formatDate = formatter.parse(lamaBeroperasi.getText().toString());
                tanggal = formatDate.getTime();
                Log.d("tanggal", formatDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        FragmentManager fm = this.getSupportFragmentManager();
        datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(tanggal)
                    .build();
        datePicker.show(fm, "open date picker");
        datePicker.addOnPositiveButtonClickListener(selection -> {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String date = dateFormat.format(selection);
            lamaBeroperasi.setText(date);
        });
    }

    private void setCameraListener() {
        final CharSequence[] options = {"Camera", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "IMG_FOLDER");
                    try {
                        if (!mediaStorageDir.exists()) {
                            mediaStorageDir.mkdirs();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    selectedImage = Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator +
//                            UUID.randomUUID().toString() + ".jpg"));
                    selectedImage = FileProvider.getUriForFile(CreateKapalActivity.this,
                            getPackageName()+".provider",
                            new File(mediaStorageDir.getPath() + File.separator + UUID.randomUUID().toString() + ".jpg"));
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);

                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != this.RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == this.RESULT_OK && data != null) {
                        if (selectedImage != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                Toast.makeText(this, "Image Loaded", Toast.LENGTH_SHORT).show();
                                imageView.setDrawingCacheEnabled(true);
                                imageView.buildDrawingCache();
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    break;
                case 1:
                    if (resultCode == this.RESULT_OK && data != null) {
                        selectedImage = data.getData();

                        if (selectedImage != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                Toast.makeText(this, "Image Loaded", Toast.LENGTH_SHORT).show();
                                imageView.setDrawingCacheEnabled(true);
                                imageView.buildDrawingCache();
                                imageView.setImageBitmap(bitmap);

                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void confirmDelete(final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hapus Kapal");
        alert.setMessage("Yakin hapus kapal?");
        alert.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            // continue with delete
            KapalService service = RetrofitClient.getClient().create(KapalService.class);
            Call<Response> deleteKapal = service.deleteKapal(new SessionManager(this).getAuthToken(), id);

            deleteKapal.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(CreateKapalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(CreateKapalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.e("ERROR [KapalAdapter] ", t.getMessage());
                    Toast.makeText(CreateKapalActivity.this,  t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        alert.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }
}
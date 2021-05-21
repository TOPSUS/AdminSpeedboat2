package com.espeedboat.admin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.activity.CreateKapalActivity;
import com.espeedboat.admin.activity.EditProfileActivity;
import com.espeedboat.admin.adapters.DropdownAdapter;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Dropdown;
import com.espeedboat.admin.model.Pelabuhan;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.service.PelabuhanService;
import com.espeedboat.admin.service.RewardService;
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
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CreateRewardActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    // layouts attribute
    private AutoCompleteTextView autoCompleteKapal;
    private TextInputEditText reward, poin, berlaku;
    private Button remove, submit;
    private ImageView profileToolbar, notifToolbar, fotoReward, back, uploadBtn;
    private TextView title;
    private Toolbar toolbar;
    private MaterialDatePicker datePicker;
    private List<Dropdown> listKapal;
    private ProgressDialog dialog;
    private Uri selectedImage;
    private int idReward = 0, idKapal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reward);

        init();

        setToolbar();

        eventListener();

        setRemoveVisible(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt(Constants.REWARD_ID, 0);
            if (id > 0) {
                title.setText("Edit Reward");
                setRemoveVisible(true);
                getDataReward(id);
            }
        }

        setKapalValue();
    }

    private void getDataReward(int id) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);
        back = findViewById(R.id.toolbar_back);

        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.menu_reward_create);

        profileToolbar.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
    }

    private void init() {
        sessionManager = new SessionManager(getApplicationContext());
        reward = findViewById(R.id.rewardForm);
        poin = findViewById(R.id.poinForm);
        berlaku = findViewById(R.id.tanggalForm);
        uploadBtn = findViewById(R.id.btn_upload);
        fotoReward = findViewById(R.id.reward_image);
        remove = findViewById(R.id.btn_remove);
        submit = findViewById(R.id.btn_submit);
        autoCompleteKapal = findViewById(R.id.autoCompleteKapal);
        listKapal = new ArrayList<>();
    }

    private void eventListener() {
        submit.setOnClickListener(v -> {
            if (idReward == 0) {
                createReward();
            } else {
//                updateReward();
            }
        });

        berlaku.setOnClickListener(v -> {
            setTanggalListener();
        });

        uploadBtn.setOnClickListener(v -> {
            setCameraListener();
        });

        autoCompleteKapal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idKapal = (int) parent.getItemIdAtPosition(position);
            }
        });
    }

    private void setRemoveVisible(Boolean con) {
        if (con) {
            remove.setVisibility(View.VISIBLE);
        } else {
            remove.setVisibility(View.GONE);
        }
    }

    private void createReward() {
        RequestBody r_reward, r_poin, r_berlaku, r_kapal;
        String in_reward, in_poin, in_berlaku;
        long date = 0;

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            date = dateFormat.parse(berlaku.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        in_reward = reward.getText().toString();
        in_poin = poin.getText().toString();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        in_berlaku = df.format(date);
        RewardService service = RetrofitClient.getClient().create(RewardService.class);
        if (selectedImage == null) {
            Toast.makeText(getApplicationContext(),  "Pilih Image", Toast.LENGTH_LONG).show();
        } else {
            if (idKapal == 0){
                Toast.makeText(getApplicationContext(),  "Pilih Kapal", Toast.LENGTH_LONG).show();
            } else {
                String filePath = Util.getRealPathFromURIPath(selectedImage, CreateRewardActivity.this);
                File fileImage = new File(filePath);

                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), fileImage);
                MultipartBody.Part body = MultipartBody.Part.createFormData("foto", fileImage.getName(), mFile);
                r_reward = RequestBody.create(MultipartBody.FORM, in_reward);
                r_poin = RequestBody.create(MultipartBody.FORM, String.valueOf(in_poin));
                r_berlaku = RequestBody.create(MultipartBody.FORM, in_berlaku);
                r_kapal = RequestBody.create(MultipartBody.FORM, String.valueOf(idKapal));

                Call<Response> createReward =  service.createReward(sessionManager.getAuthToken(), r_reward,
                        r_berlaku, r_poin, r_kapal, body);

                createReward.enqueue(new Callback<Response>() {
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
    }

    private void setKapalValue() {
        autoCompleteKapal.clearListSelection();
        KapalService service = RetrofitClient.getClient().create(KapalService.class);
        ArrayList<String> lp = new ArrayList<>();
        Call<Response> getKapal = service.getUserKapal(sessionManager.getAuthToken());

        getKapal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                String pelabuhanString = null;
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();

                        if (data.getDropdown().size() > 0) {
                            int id = 0;
                            int selectedId = -1;
                            for(Dropdown drops : data.getDropdown()) {
                                listKapal.add(drops);
                            }

                            DropdownAdapter adapter = new DropdownAdapter(CreateRewardActivity.this, listKapal);
                            autoCompleteKapal.setAdapter(adapter);
                        }else {
                            Toast.makeText(getApplicationContext(), "Please Create Kapal First", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Please Create Kapal First", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Dropdown Kapal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("ERROR [KapalFragment] ", t.getMessage());
                Toast.makeText(getApplicationContext(),  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTanggalListener() {
        Long tanggal = MaterialDatePicker.todayInUtcMilliseconds();
        if (!berlaku.getText().toString().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date formatDate = formatter.parse(berlaku.getText().toString());
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
            berlaku.setText(date);
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
                    selectedImage = FileProvider.getUriForFile(CreateRewardActivity.this,
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
                                fotoReward.setDrawingCacheEnabled(true);
                                fotoReward.buildDrawingCache();
                                fotoReward.setImageBitmap(bitmap);
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
                                fotoReward.setDrawingCacheEnabled(true);
                                fotoReward.buildDrawingCache();
                                fotoReward.setImageBitmap(bitmap);

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
}
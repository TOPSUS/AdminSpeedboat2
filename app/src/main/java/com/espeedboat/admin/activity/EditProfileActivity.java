package com.espeedboat.admin.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.User;
import com.espeedboat.admin.service.EditProfileService;
import com.espeedboat.admin.service.KapalService;
import com.espeedboat.admin.utils.SessionManager;
import com.espeedboat.admin.utils.Util;
import com.google.android.material.textfield.TextInputEditText;

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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditProfileActivity extends AppCompatActivity {
    private RelativeLayout editprofile;
    private TextView username, role, title;
    private Toolbar toolbar;
    private ImageView  profileToolbar, notifToolbar, back, profile;
    private SessionManager sessionManager;
    private AutoCompleteTextView autoCompleteJK;
    private TextInputEditText nama, alamat, nohp, email;
    private Uri selectedImage;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(EditProfileActivity.this);

        init();

        setToolbar();
        setJKValue(null);
        getProfile();

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCameraListener();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void init() {
        nama = findViewById(R.id.namaForm);
        alamat = findViewById(R.id.alamatForm);
        nohp = findViewById(R.id.hpForm);
        email = findViewById(R.id.emailForm);
        editprofile = findViewById(R.id.image_wrapper);
        profile = findViewById(R.id.image_edit_user);
        submit = findViewById(R.id.btn_submit);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);
        back = findViewById(R.id.toolbar_back);

        title = findViewById(R.id.toolbar_title);
        title.setText("Edit Profile");

        profileToolbar.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
    }

    private void setJKValue(String tipe) {
        String[] items = {"Laki-laki", "Perempuan"};
        autoCompleteJK = findViewById(R.id.autoCompleteJeniskelamin);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, items);

        if (tipe != null) {
            if (tipe.equals("Laki-laki")) {
                autoCompleteJK.setText(adapter.getItem(0).toString());
            } else if (tipe.equals("Perempuan")) {
                autoCompleteJK.setText(adapter.getItem(1).toString());
            }
        }

        autoCompleteJK.setAdapter(adapter);
    }

    private void saveProfile() {
        RequestBody r_nama, r_alamat, r_jk, r_hp, r_email;
        EditProfileService service = RetrofitClient.getClient().create(EditProfileService.class);

        if (selectedImage != null) {

            String filePath = Util.getRealPathFromURIPath(selectedImage, EditProfileActivity.this);
            File fileImage = new File(filePath);

            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), fileImage);
            MultipartBody.Part body = MultipartBody.Part.createFormData("foto", fileImage.getName(), mFile);
            r_nama = RequestBody.create(MultipartBody.FORM, nama.getText().toString());
            r_alamat = RequestBody.create(MultipartBody.FORM, alamat.getText().toString());
            r_jk = RequestBody.create(MultipartBody.FORM, autoCompleteJK.getText().toString());
            r_hp = RequestBody.create(MultipartBody.FORM, nohp.getText().toString());
            r_email = RequestBody.create(MultipartBody.FORM, email.getText().toString());

            Call<Response> updateUser = service.updateProfile(sessionManager.getAuthToken(), sessionManager.getUserId(),
                    r_nama, r_alamat, r_jk, r_hp, r_email, body);

            updateUser.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            Toast.makeText(getApplicationContext(),  response.body().getMessage(), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Log.d("Response not 200", response.message().toString());
                            Toast.makeText(getApplicationContext(),  "Error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("Response not success", response.message());
                        Toast.makeText(getApplicationContext(),  "Error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("Response not success", t.getMessage());
                    Toast.makeText(getApplicationContext(),  "Failed", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),  "Choose Profile Picture", Toast.LENGTH_LONG).show();
        }
    }

    private void getProfile() {
        EditProfileService service = RetrofitClient.getClient().create(EditProfileService.class);

        Call<Response> getUser = service.editProfile(sessionManager.getAuthToken(), sessionManager.getUserId());

        Log.d("user id", String.valueOf(sessionManager.getUserId()));

        getUser.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("user id", response.toString());
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        setProfileValue(data.getUser());
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void setProfileValue(User user) {
        nama.setText(user.getNama());
        alamat.setText(user.getAlamat());
        nohp.setText(user.getNohp());
        email.setText(user.getEmail());
        setJKValue(user.getJeniskelamin());

        Glide.with(this).load(user.getUrlFoto()).error(R.drawable.no_profile).into(profile);
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
                    selectedImage = FileProvider.getUriForFile(EditProfileActivity.this,
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
                                profile.setDrawingCacheEnabled(true);
                                profile.buildDrawingCache();
                                profile.setImageBitmap(bitmap);
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
                                profile.setDrawingCacheEnabled(true);
                                profile.buildDrawingCache();
                                profile.setImageBitmap(bitmap);

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
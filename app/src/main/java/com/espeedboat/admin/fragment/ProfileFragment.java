package com.espeedboat.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.EditProfileActivity;
import com.espeedboat.admin.activity.LoginActivity;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.model.User;
import com.espeedboat.admin.service.EditProfileService;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class
ProfileFragment extends Fragment {

    FinishActivity finishActivityCallback;
    ToolbarTitle toolbarTitleCallback;
    private View view;
    private ImageView back, profileToolbar;
    private SessionManager sessionManager;
    private TextView username, role, title;
    private RelativeLayout logout, review, editprofile;
    private CircleImageView profilePic;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarTitleCallback = (ToolbarTitle) context;
        finishActivityCallback = (FinishActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();

        toolbarTitleCallback.setToolbarTitle("Profile");

        setUser();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username.setText(sessionManager.getUserName());
        role.setText(sessionManager.getUserRole());

        logout.setOnClickListener(v -> {
            sessionManager.clearSession();

            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
        });

        editprofile.setOnClickListener(v -> {

            Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
            startActivity(intent);
        });

        review.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Review");
            Fragment fragment = new ReviewFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setUser();
    }

    private void init() {
        sessionManager = new SessionManager(view.getContext());
        username = view.findViewById(R.id.username);
        role = view.findViewById(R.id.role);
        logout = view.findViewById(R.id.logout);
        review = view.findViewById(R.id.menu_review);
        title = view.findViewById(R.id.toolbar_title);
        editprofile = view.findViewById(R.id.menu_editprofile);
        profilePic = view.findViewById(R.id.profile);
    }

    private void setUser() {
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
                        setUserValue(data.getUser());
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void setUserValue(User user) {
        username.setText(user.getNama());
        role.setText(user.getRole());

        //set Image
        Glide.with(this).load(user.getUrlFoto()).into(profilePic);
    }
}
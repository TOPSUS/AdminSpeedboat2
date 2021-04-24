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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.activity.LoginActivity;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.utils.Constants;
import com.espeedboat.admin.utils.SessionManager;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    FinishActivity finishActivityCallback;
    ToolbarTitle toolbarTitleCallback;
    private View view;
    private ImageView back, profileToolbar;
    private SessionManager sessionManager;
    private TextView username, role, title;
    private RelativeLayout logout, review;

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

        review.setOnClickListener(v -> {
            toolbarTitleCallback.setToolbarTitle("Review");
            Fragment fragment = new ReviewFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
            ft.commit();
        });
    }

    private void init() {
        sessionManager = new SessionManager(view.getContext());
        username = view.findViewById(R.id.username);
        role = view.findViewById(R.id.role);
        logout = view.findViewById(R.id.logout);
        review = view.findViewById(R.id.menu_review);
        title = view.findViewById(R.id.toolbar_title);
    }
}
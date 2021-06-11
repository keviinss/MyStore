package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private String TAG = SettingFragment.class.getSimpleName();

    private TextView txtUsername;
    private RelativeLayout layoutSignout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initView(view);

        txtUsername.setText(MyPreferences.getSharedPreferences()
                .getString(MyPreferences.USERNAME, "username"));

        layoutSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPreferences.getEditorPreferences().clear().commit();
                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void initView(View view) {
        txtUsername = view.findViewById(R.id.txt_username);
        layoutSignout = view.findViewById(R.id.layout_signout);
    }
}

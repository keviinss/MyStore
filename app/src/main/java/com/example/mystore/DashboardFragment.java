package com.example.mystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {
    private String TAG = DashboardFragment.class.getSimpleName();

    private EditText editName;
    private Button btnSubmit;
    private TextView txtName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initView(view);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtils.getReference(FirebaseUtils.MYNAME_PATH)
                        .setValue(editName.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUtils.getReference(FirebaseUtils.MYNAME_PATH).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        txtName.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void initView(View view) {
        editName = view.findViewById(R.id.edit_name);
        btnSubmit = view.findViewById(R.id.btn_submit);
        txtName = view.findViewById(R.id.txt_name);
    }
}

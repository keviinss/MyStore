package com.example.mystore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private String TAG = SignUpActivity.class.getSimpleName();

    private EditText editUsername;
    private EditText editPassword;
    private Button btnSignUp;
    private TextView txtSignIn;

    private String key;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnSignUp = findViewById(R.id.btn_signup);
        txtSignIn = findViewById(R.id.txt_signin);
    }

    private Boolean isValid(EditText editText, String data) {
        if (!TextUtils.isEmpty(data) && !data.equals("")) {
            return true;
        } else {
            editText.setError("This field cannot be empty.");
            return false;
        }
    }

    private void addAccount() {
        key = FirebaseUtils.getReference(FirebaseUtils.ACCOUNTS_PATH).push().getKey();
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if (isValid(editUsername, username) && isValid(editPassword, password)) {
            Account account = new Account(key, username, password);
            FirebaseUtils.getReference(FirebaseUtils.ACCOUNTS_PATH).child(key).setValue(account);
            Toast.makeText(SignUpActivity.this,
                    "Account has been added.", Toast.LENGTH_LONG);
            onBackPressed();
        }
    }
}

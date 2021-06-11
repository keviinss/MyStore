package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private String TAG = SignInActivity.class.getSimpleName();

    private EditText editUsername;
    private EditText editPassword;
    private Button btnSignIn;
    private TextView txtSignUp;

    private String key;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initView();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAccount();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnSignIn = findViewById(R.id.btn_signin);
        txtSignUp = findViewById(R.id.txt_signup);
    }

    private Boolean isValid(EditText editText, String data) {
        if (!TextUtils.isEmpty(data) && !data.equals("")) {
            return true;
        } else {
            editText.setError("This field cannot be empty.");
            return false;
        }
    }

    private void signInAccount() {
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        Query query = FirebaseUtils.getReference(FirebaseUtils.ACCOUNTS_PATH).orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Account account = snapshot.getValue(Account.class);
                        if (account.getUsername().equals(username) &&
                                account.getPassword().equals(password)) {
                            MyPreferences.getEditorPreferences()
                                    .putBoolean(MyPreferences.IS_LOGIN, true);
                            MyPreferences.getEditorPreferences()
                                    .putString(MyPreferences.USERNAME, account.getUsername());
                            MyPreferences.getEditorPreferences().commit();
                            Intent intent = new Intent(SignInActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getDetails() + " | " + databaseError.getMessage());
            }
        });
    }
}

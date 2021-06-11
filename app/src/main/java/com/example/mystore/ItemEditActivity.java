package com.example.mystore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

public class ItemEditActivity extends AppCompatActivity {
    private String TAG = ItemEditActivity.class.getSimpleName();

    private EditText editName;
    private EditText editDesc;
    private EditText editQty;
    private Button btnSubmit;

    private String key;
    private String name;
    private String desc;
    private int qty;

    private Item item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        initView();

        item = (Item) getIntent().getSerializableExtra("data");

        if (item != null) {
            editName.setText(item.getName());
            editDesc.setText(item.getDesc());
            editQty.setText(String.valueOf(item.getQty()));
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateItem(item);
                }
            });
        } else {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem();
                }
            });
        }
    }

    private void initView() {
        editName = findViewById(R.id.edit_name);
        editDesc = findViewById(R.id.edit_desc);
        editQty = findViewById(R.id.edit_qty);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private Boolean isValid(EditText editText, String data) {
        if (!TextUtils.isEmpty(data) && !data.equals("")) {
            return true;
        } else {
            editText.setError("This field cannot be empty.");
            return false;
        }
    }

    private void addItem() {
        key = FirebaseUtils.getReference(FirebaseUtils.ITEMS_PATH).push().getKey();
        name = editName.getText().toString().trim();
        desc = editDesc.getText().toString().trim();

        if (isValid(editName, name) && isValid(editDesc, desc)
                && isValid(editQty, editQty.getText().toString().trim())) {
            qty = Integer.parseInt(editQty.getText().toString().trim());

            Item item = new Item(key, name, desc, qty);

            FirebaseUtils.getReference(FirebaseUtils.ITEMS_PATH).child(key).setValue(item);

            Toast.makeText(ItemEditActivity.this,
                    "Item has been added.", Toast.LENGTH_LONG).show();

            onBackPressed();
        }
    }

    private void updateItem(Item item) {
        item.setName(editName.getText().toString().trim());
        item.setDesc(editDesc.getText().toString().trim());
        item.setQty(Integer.parseInt(editQty.getText().toString().trim()));

        FirebaseUtils.getReference(FirebaseUtils.ITEMS_PATH).child(item.getKey())
                .setValue(item)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ItemEditActivity.this,
                                "Item has been updated", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });
    }
}

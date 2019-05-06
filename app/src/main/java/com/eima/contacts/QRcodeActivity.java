package com.eima.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class QRcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        final Intent senderIntent = getIntent();

        TextView nameTextView = findViewById(R.id.name_view);
        nameTextView.setText(senderIntent.getStringExtra("name"));
    }
}

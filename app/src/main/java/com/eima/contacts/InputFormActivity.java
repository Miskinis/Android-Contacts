package com.eima.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class InputFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent senderIntent = getIntent();

        EditText nameInputView = findViewById(R.id.name_input);
        EditText emailInputView = findViewById(R.id.email_input);
        EditText phoneNumberInputView = findViewById(R.id.phone_number_input);

        nameInputView.setText(senderIntent.getStringExtra("name"));
        emailInputView.setText(senderIntent.getStringExtra("email"));
        phoneNumberInputView.setText(senderIntent.getStringExtra("phoneNumber"));

        FloatingActionButton save_contact_fab = findViewById(R.id.save_contact_fab);
        save_contact_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameInputView = findViewById(R.id.name_input);
                EditText emailInputView = findViewById(R.id.email_input);
                EditText phoneNumberInputView = findViewById(R.id.phone_number_input);

                Intent returnIntent = new Intent(getBaseContext(), MainActivity.class);
                returnIntent.putExtra("name", nameInputView.getText().toString());
                returnIntent.putExtra("email", emailInputView.getText().toString());
                returnIntent.putExtra("phoneNumber", phoneNumberInputView.getText().toString());
                returnIntent.putExtra("add", senderIntent.getBooleanExtra("add", true));

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

package com.eima.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File directory = new File(getBaseContext().getFilesDir().getAbsolutePath() + "/contacts.json");
        if(directory.exists())
        {
            contacts = Contact.ReadJsonFile(directory.getPath());
        }
        else
        {
            contacts = new ArrayList<Contact>(1);
        }

        //
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        adapter = new ContactsAdapter(contacts);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        //
        FloatingActionButton add_contact_fab = findViewById(R.id.add_contact_fab);
        add_contact_fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), InputFormActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Contact contact = new Contact(data.getStringExtra("name"), data.getStringExtra("email"), data.getStringExtra("phoneNumber"));
                if(data.getBooleanExtra("add", true)) {
                    adapter.AddItem(contact);
                }
                else
                {
                    adapter.ChangeItem(contact);
                }

                String directory = getBaseContext().getFilesDir().getAbsolutePath();
                Contact.WriteJsonFile(directory + "/contacts.json", contacts);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

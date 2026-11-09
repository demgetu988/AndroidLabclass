package com.example.database;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btsave, btview, btupdate, deletebutton;
    private EditText name, phonenumber, contactid;
    private ListView listView;
    private List<String> myarraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        btsave = findViewById(R.id.save);
        btview = findViewById(R.id.view);
        btupdate = findViewById(R.id.update);
        deletebutton = findViewById(R.id.deletebutton);
        name = findViewById(R.id.name);
        phonenumber = findViewById(R.id.phonenumber);
        contactid = findViewById(R.id.contactid);

        name.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        listView = findViewById(R.id.mobile_list);

        btsave.setOnClickListener(v -> {
            if (validate(name) && validate(phonenumber)) {
                db.addContact(new Contact(name.getText().toString(), phonenumber.getText().toString()));
                Toast.makeText(getApplicationContext(), "Insert successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please fill empty fields!", Toast.LENGTH_LONG).show();
            }
        });

        btview.setOnClickListener(v -> {
            List<Contact> contacts = db.getAllContacts();
            myarraylist = new ArrayList<>();
            for (Contact cn : contacts) {
                myarraylist.add("Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    myarraylist);
            listView.setAdapter(arrayAdapter);
        });

        btupdate.setOnClickListener(v -> {
            if (validate(name) && validate(phonenumber) && validate(contactid)) {
                db.updatedata(contactid.getText().toString(),
                        name.getText().toString(), phonenumber.getText().toString());
                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please fill empty fields!", Toast.LENGTH_LONG).show();
            }
        });

        deletebutton.setOnClickListener(v -> {
            if (validate(contactid)) {
                db.deletedata(contactid.getText().toString());
                Toast.makeText(getApplicationContext(), "Delete successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please fill ID field!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate(EditText text) {
        return text != null && !text.getText().toString().trim().isEmpty();
    }
}

package com.mobdeve.s15.group1.a20210825_practicingsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    EditText inputName;
    TextView txtName, txtDate;
    Spinner spinnerDate;
    Button btnSubmit;

    private MyDbHelper myDbHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ArrayList<ContactModel> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                myDbHelper = MyDbHelper.getInstance(MainActivity.this);
                contacts = myDbHelper.getAllContactsDefault();
                for(ContactModel c:contacts) {
                    Log.d("MainActivity", "here be contacts: "+c.toString());
                    Log.d("MainActivity", "get name: "+c.getName());
                }
            }
        });

        this.spinnerDate = findViewById(R.id.spinnerDate);
        String[] items = new String[]{"January", "February", "March"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerDate.setAdapter(adapter);

        this.btnSubmit = findViewById(R.id.btnSubmit);
        this.inputName = findViewById(R.id.inputName);
        this.txtName = findViewById(R.id.txtName);
        this.txtDate = findViewById(R.id.txtDate);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = spinnerDate.getSelectedItem().toString();
                String name = inputName.getText().toString();

                txtName.setText(name);
                txtDate.setText(date);

                ContactModel c = new ContactModel(name, date);
                MyDbHelper db = new MyDbHelper(MainActivity.this);
                db.insertContact(c);
            }
        });
    }
}
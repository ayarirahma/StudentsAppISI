package com.example.geekhamza.isi;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Student extends AppCompatActivity {
    FloatingActionButton fab;
    ListView listView;
    // Array of strings...
    String[] mobileArray = {"Demande d'attestation de presence ","Mise a jour emploi ","Mise a jour des note",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
       fab = findViewById(R.id.student_fab);
       listView=findViewById(R.id.student_list_view);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.single_item_list_view, mobileArray);
        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}

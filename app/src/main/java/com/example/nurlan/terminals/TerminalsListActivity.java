package com.example.nurlan.terminals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class TerminalsListActivity extends AppCompatActivity {
    TerminalsDatabase db;
    TerminalsAdapter  adapter;
    String LOG_TAG = "nb_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminals_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(LOG_TAG, "On Create in TerminalsListActivity");

          adapter = new TerminalsAdapter(TerminalsListActivity.this, getData(), TerminalsListActivity.this.getApplicationContext());
        ListView dima = (ListView) findViewById(R.id.listView);
        Log.d(LOG_TAG, "назначение адаптера");
        dima.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Список терминалов (" + String.valueOf(adapter.getCount()) + ")");

    }
    public ArrayList<Point> getData(){
        Log.d(LOG_TAG, "вход в ARRAYLIST in TerminalsListActivity");
        db = new TerminalsDatabase(TerminalsListActivity.this.getApplicationContext());

        final ArrayList<Point> stringItems = new ArrayList<Point>();

        ArrayList<Point> pr = (ArrayList<Point>) db.getAllPoints();   // содержит  все даты
        for (Point p : pr) {
            stringItems.add(p);
            Log.d(LOG_TAG, "добавляются в лист Point");
        }
        Log.d(LOG_TAG, "Список отрисовался в TerminalsListActivity");
        return stringItems;


    }

}

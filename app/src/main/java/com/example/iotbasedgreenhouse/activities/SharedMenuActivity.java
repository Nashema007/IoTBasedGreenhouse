package com.example.iotbasedgreenhouse.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.adapter.SharedMenuAdapter;
import com.example.iotbasedgreenhouse.models.SharedMenuModal;

import java.util.ArrayList;

public class SharedMenuActivity extends AppCompatActivity {




    String [] titles = {"Daily", "Weekly", "Monthly", "Yearly"};

    ArrayList<SharedMenuModal> sharedMenuModalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_menu);

ActionBar actionBar = this.getSupportActionBar();
        RecyclerView recyclerView = findViewById(R.id.sharedMenuRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        int count = 0;

        for(String title: titles){
            sharedMenuModalArrayList.add(new SharedMenuModal(title));
         count ++;
        }

        String temp = getIntent().getStringExtra("temp");
        String moist = getIntent().getStringExtra("moist");
        String hum = getIntent().getStringExtra("hum");
        String water = getIntent().getStringExtra("water");
        String extra = " ";


        if (temp == null && moist == null && hum == null){
            temp = " ";
            moist = " ";
            hum = " ";
            extra = water;

            if (actionBar != null) {
                actionBar.setTitle("Water Flow  Data");
            }
        }else if (water == null && moist == null && hum == null){
            extra = temp;
            moist = " ";
            hum = " ";
            water = " ";


            if (actionBar != null) {
                actionBar.setTitle("Temperature Data");
            }
        } else if (temp == null && water == null && hum == null){
            temp = " ";
            extra = moist ;
            hum = " ";
            water = " ";

            if (actionBar != null) {
                actionBar.setTitle("Soil Moisture Data");
            }
        } else if (temp == null && moist == null && water == null){
            temp = " ";
            moist = " ";
            water = " ";
            extra = hum;

            if (actionBar != null) {
                actionBar.setTitle("Humidity Data");
            }
        }

        SharedMenuAdapter sharedMenuAdapter = new SharedMenuAdapter(this, sharedMenuModalArrayList, extra);

        recyclerView.setAdapter(sharedMenuAdapter);
    }
}

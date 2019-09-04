package com.example.iotbasedgreenhouse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.adapter.LegendAdapter;
import com.example.iotbasedgreenhouse.models.LegendModal;
import com.example.iotbasedgreenhouse.utilities.AppConfig;
import com.example.iotbasedgreenhouse.utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WaterFlowActivity extends AppCompatActivity {
    private ArrayList<LegendModal> legendModals = new ArrayList<>();
    private LegendAdapter legendAdapter;
    private RecyclerView recyclerView;
    private TextView viewGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legend);
        ActionBar actionBar = this.getSupportActionBar();


        viewGraph = findViewById(R.id.viewGraph);
        recyclerView = findViewById(R.id.legendecyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));





        String daily = getIntent().getStringExtra("Daily");
        String weekly = getIntent().getStringExtra("Weekly");
        String monthly = getIntent().getStringExtra("Monthly");
        String yearly = getIntent().getStringExtra("Yearly");


        if (weekly == null && monthly == null && yearly == null && daily.equals("Daily")) {
            weekly = "";
            monthly = "";
            yearly = "";
            String period = "daily";

            assert actionBar != null;
            actionBar.setTitle("Daily Water flow readings");

            retrieveTempValues(period, AppConfig.GET_DAILY_VALUES);

        }
        else if (daily == null && monthly == null && yearly == null && weekly.equals("Weekly")) {
            daily = "";
            monthly = "";
            yearly = "";
            String period = "weekly";

            assert actionBar != null;
            actionBar.setTitle("Weekly Water flow readings");


            retrieveTempValues(period, AppConfig.GET_WEEKLY_VALUES);

        }
        else if (daily == null && weekly == null && yearly== null && monthly.equals("Monthly")) {
            daily = "";
            weekly = "";
            yearly = "";
            String period = "monthly";

            assert actionBar != null;
            actionBar.setTitle("Monthly Water flow readings");

            retrieveTempValues(period, AppConfig.GET_MONTHLY_VALUES);

        }
        else if (daily == null && weekly == null &&  monthly == null && yearly.equals("Yearly")){
            daily = "";
            weekly = "";
            monthly = "";
            String period = "yearly";
            assert actionBar != null;
            actionBar.setTitle("Yearly Water flow readings");

            retrieveTempValues(period, AppConfig.GET_YEARLY_VALUES);
        }


    }

    private void retrieveTempValues(String period, String url){

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                ArrayList<String> periodValues = new ArrayList<>();
                for (int i = 0; i< jsonArray.length();i++ ){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String water= object.getString("flow_rate");
                    if(water.equals(" ") || water.equals("null")){
                        water="0";
                    }
                    periodValues.add(water);

                }
                setTitle(period,periodValues);
                legendAdapter = new LegendAdapter(this, legendModals);
                recyclerView.setAdapter(legendAdapter);

                viewGraph.setOnClickListener((v)->{
                    Intent intent = new Intent(this, SharedGraphActivity.class);
                    intent.putStringArrayListExtra("waterValues",periodValues);
                    intent.putExtra("water", "water");
                    intent.putExtra("period",period);
                    startActivity(intent);

                });






            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            if (error instanceof TimeoutError) {
                Toast.makeText(this, "Attempt has timed out. Please try again.",
                        Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();

            }
            error.printStackTrace();


        });

        AppSingleton.getInstance(this).addToRequestQueue(stringRequest);




    }

    private void setTitle(String period,ArrayList<String> periodValues){
        ArrayList<String> values = new ArrayList<>();
        switch (period) {
            case "weekly": {
                String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                if(periodValues.size() <7){
                    for (int i = 0; i < (7 - periodValues.size());i++){
                        values.add("0");
                    }
                }
                periodValues.addAll(values);
                int count = 0;
                for (String day : days) {

                    legendModals.add(new LegendModal(day, periodValues.get(count)));
                    count++;
                }
                break;
            }
            case "daily": {
                String[] days = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00",
                        "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
                        "22:00", "23:00"};
                if(periodValues.size() <24){
                    for (int i = 0; i < (24- periodValues.size());i++){
                        values.add("0");
                    }
                }
                periodValues.addAll(values);
                int count = 0;
                for (String day : days) {

                    legendModals.add(new LegendModal(day, periodValues.get(count)));
                    count++;
                }
                break;
            }
            case "monthly": {
                String[] days = {"Week 1", "Week 2", "Week 3", "Week 4"};
                if(periodValues.size() <4){
                    for (int i = 0; i < (4 - periodValues.size());i++){
                        values.add("0");
                    }
                }
                periodValues.addAll(values);
                int count = 0;
                for (String day : days) {

                    legendModals.add(new LegendModal(day, periodValues.get(count)));
                    count++;
                }
                break;
            }
            case "yearly": {
                String[] days = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                        "October", "November", "December"};
                if(periodValues.size() <13){
                    for (int i = 0; i < (12 - periodValues.size());i++){
                        values.add("0");
                    }
                }
                periodValues.addAll(values);
                int count = 0;
                for (String day : days) {

                    legendModals.add(new LegendModal(day, periodValues.get(count)));
                    count++;
                }
                break;
            }
        }


    }

}



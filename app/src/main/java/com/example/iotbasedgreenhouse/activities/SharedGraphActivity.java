package com.example.iotbasedgreenhouse.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.utilities.DrawGraph;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;

public class SharedGraphActivity extends AppCompatActivity {

    private SciChartSurface surface;
    private SciChartBuilder sciChartBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_graph);

        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Graphical Representation");


        // Create a SciChartSurface
        surface = new SciChartSurface(this);

        // Get a layout declared in "activity_legend" by id
        LinearLayout chartLayout = findViewById(R.id.sharedGraph);

        // Add the SciChartSurface to the layout
        chartLayout.addView(surface);

        // Initialize the SciChartBuilder
        SciChartBuilder.init(this);

        // Obtain the SciChartBuilder instance
        sciChartBuilder = SciChartBuilder.instance();

        String temp = getIntent().getStringExtra("temp");
        String humidity = getIntent().getStringExtra("hum");
        String water_flow = getIntent().getStringExtra("water");
        String moisture = getIntent().getStringExtra("moist");

        if (temp == null && humidity == null && moisture == null && water_flow.equals("water")) {
            temp = "";
            humidity = "";
            moisture = "";
            ArrayList<String> waterValues = getIntent().getStringArrayListExtra("waterValues");
            ArrayList<Double> yValues = new ArrayList<>();
            assert waterValues != null;
            for (String waterValue : waterValues) {
                yValues.add(Double.parseDouble(waterValue));
            }
            selectPeriod(yValues);

        } else if (water_flow == null && temp == null && humidity == null && moisture.equals("moist")) {
            water_flow = "";
            temp = "";
            humidity = "";
            ArrayList<String> moistValues = getIntent().getStringArrayListExtra("moistValues");
            ArrayList<Double> yValues = new ArrayList<>();
            assert moistValues != null;
            for (String moistValue : moistValues) {
                yValues.add(Double.parseDouble(moistValue));
            }
            selectPeriod(yValues);


        } else if (moisture == null && water_flow == null && temp == null && humidity.equals("hum")) {
            moisture = "";
            water_flow = "";
            temp = "";
            ArrayList<String> humValues = getIntent().getStringArrayListExtra("humValues");
            ArrayList<Double> yValues = new ArrayList<>();
            assert humValues != null;


            for (String humValue : humValues) {
                yValues.add(Double.parseDouble(humValue));
            }

            selectPeriod(yValues);

        } else if (humidity == null && moisture == null && water_flow == null && temp.equals("temp")) {
            humidity = "";
            moisture = "";
            water_flow = "";
            ArrayList<String> tempValues = getIntent().getStringArrayListExtra("tempValues");
            ArrayList<Double> yValues = new ArrayList<>();
            assert tempValues != null;
            for (String tempValue : tempValues) {
                yValues.add(Double.parseDouble(tempValue));
            }

            selectPeriod(yValues);

        }


    }


    private void selectPeriod(ArrayList<Double> yValues) {

        String period = getIntent().getStringExtra("period");


        if (period != null) {
            switch (period) {
                case "daily":
                    ArrayList<Double>day = new ArrayList<>();
                    for (int i = 0; i < 25; i++){
                        day.add(yValues.get(i));
                    }
                    DrawGraph.columnGraph(surface, sciChartBuilder, "Hours in a day", "Average values per Hour", day);
                    break;
                case "weekly":
                    DrawGraph.columnGraph(surface, sciChartBuilder, "Days in a week", "Average values per Day", yValues);
                    break;
                case "monthly":
                    DrawGraph.columnGraph(surface, sciChartBuilder, "Weeks in a month", "Average values per Week", yValues);
                    break;
                case "yearly":
                    DrawGraph.columnGraph(surface, sciChartBuilder, "Months in a Year", "Average values per Month", yValues);
                    break;
            }

        }
    }
}

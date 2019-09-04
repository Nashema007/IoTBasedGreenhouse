package com.example.iotbasedgreenhouse.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.utilities.AppConfig;
import com.example.iotbasedgreenhouse.utilities.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.iotbasedgreenhouse.utilities.AppConfig.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    private TextView humidityValue;
    private TextView tempValue;
    private TextView waterFlowValue;
    private TextView moistureValue;

    private static void setupAnimation(LottieAnimationView lottieAnimationView, float animationSpeed) {

        lottieAnimationView.setSpeed(animationSpeed);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);


        humidityValue = findViewById(R.id.humidityValue);
        tempValue = findViewById(R.id.tempValue);
        waterFlowValue = findViewById(R.id.waterFlowValue);
        moistureValue = findViewById(R.id.moistureValue);

        LinearLayout linearLayoutTemperature = findViewById(R.id.Temperature);
        LinearLayout linearLayoutHumidity = findViewById(R.id.Humidity);
        LinearLayout linearLayoutMoisture = findViewById(R.id.Moisture);
        LinearLayout linearLayoutWaterFlow = findViewById(R.id.WaterFlow);

        LottieAnimationView tempAnimation = findViewById(R.id.tempAnimation);
        LottieAnimationView humidityAnimation = findViewById(R.id.humidityAnimation);
        LottieAnimationView moistureAnimation = findViewById(R.id.moistureAnimation);
        LottieAnimationView waterFlowAnimation = findViewById(R.id.waterFlowAnimation);

        PreferenceManager.setDefaultValues(this, R.xml.settings_pref, true);


        setupAnimation(tempAnimation, 3f);
        setupAnimation(humidityAnimation, 3f);
        setupAnimation(moistureAnimation, 3f);
        setupAnimation(waterFlowAnimation, 1f);

        String temp = "\u2103";//tempSymbol;
        String humiditySymbol = "%";
        String moistureSymbol = "%";
        String waterFlow = "L/min";

        tempValue.setText(String.format("%s %s", 22, temp));
        humidityValue.setText(String.format("%s %s", 23, humiditySymbol));
        moistureValue.setText(String.format("%s %s", 4, moistureSymbol));
        waterFlowValue.setText(String.format("%s %s",23, waterFlow));


        if(Integer.parseInt("22") <= 26){
            sendNotification(1, "Temperature level alert", "Temperature values have gone above threshold: Fan switched on");
        }
        else if(Integer.parseInt("44") <= 40){
            sendNotification(1, "Humidity level alert", "Humidity values have gone above threshold: Fan switched on");
        }
        else if (Integer.parseInt("4") <= 28){
            sendNotification(1, "Moisture level alert", "Moisture level below threshold: water pump switched on");
        }

//        Timer timer = new Timer();
//     // Set the schedule function
//       timer.scheduleAtFixedRate(new TimerTask() {
//      @Override
//            public void run() {
                retrieveMenuVales();
       //     }
      // }, 0, 20000);   // 20000 Millisecond  = 20 second


        linearLayoutTemperature.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedMenuActivity.class);
            intent.putExtra("temp", "temp");
            startActivity(intent);
        });

        linearLayoutHumidity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedMenuActivity.class);
            intent.putExtra("hum", "hum");
            startActivity(intent);
        });

        linearLayoutMoisture.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedMenuActivity.class);
            intent.putExtra("moist", "moist");
            startActivity(intent);
        });

        linearLayoutWaterFlow.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedMenuActivity.class);
            intent.putExtra("water", "water");
            startActivity(intent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveMenuVales() {
        // AppConfig.URL_GET_MAIN_MENU
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.GET_REAL_TIME , response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
/*
"temperature": 21.8,
"humidity": 44.5,
"moisture": 55,
"flow_rate": null,
"current_flow": 0
 */
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject index = jsonArray.getJSONObject(i);
                    String temperature = index.getString("temperature");
                    String humidity = index.getString("humidity");
                    String water_flow = index.getString("flow_rate");
                    String current_flow = index.getString("current_rate");
                    String moisture = index.getString("moisture");

                    String temp = "\u2103";//tempSymbol;
                    String humiditySymbol = "%";
                    String moistureSymbol = "%";
                    String waterFlow = "L/min";

                    tempValue.setText(String.format("%s %s", temperature, temp));
                    humidityValue.setText(String.format("%s %s", humidity, humiditySymbol));
                    moistureValue.setText(String.format("%s %s", moisture, moistureSymbol));
                    waterFlowValue.setText(String.format("%s %s", current_flow, waterFlow));

                    if(Integer.parseInt(temperature) <= 26){
                        sendNotification(1, "Temperature level alert", "Temperature values have gone above threshold: Fan switched on");
                    }
                    else if(Integer.parseInt(humidity) <= 40){
                        sendNotification(1, "Humidity level alert", "Humidity values have gone above threshold: Fan switched on");
                    }
                    else if (Integer.parseInt(moisture) <= 28){
                        sendNotification(1, "Moisture level alert", "Moisture level below threshold: water pump switched on");
                    }


                }


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

    public void sendNotification(int notificationId, String notificationTitle, String notificationTxt){

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setContentTitle(notificationTitle)
                .setContentText(notificationTxt)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

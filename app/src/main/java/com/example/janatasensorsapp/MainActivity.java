package com.example.janatasensorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView accelerometerTxtView, proximityTxtView, gyroTxtView, lightTextView;
    private SensorManager sensorManager;
    private CardView accelerometerCard, proximityCard, gyroCard, lightCard;
    private Button resetDbBtn;
    private JanataSensorsService mService;
    private Intent mServiceIntent;
    private static final String DB_NAME = "janata_sensors_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting all the views from the layout file
        accelerometerTxtView = findViewById(R.id.accelerometerTxtView);
        proximityTxtView = findViewById(R.id.proximityTxtView);
        gyroTxtView = findViewById(R.id.gyroTxtView);
        lightTextView = findViewById(R.id.lightTxtView);
        accelerometerCard = findViewById(R.id.accelerometerCard);
        proximityCard = findViewById(R.id.proximityCard);
        gyroCard = findViewById(R.id.gyroscopeCard);
        lightCard = findViewById(R.id.lightCard);
        resetDbBtn = findViewById(R.id.resetDbBtn);

        //Setting up the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Starting service for saving data
        startService(new Intent(MainActivity.this, JanataSensorsService.class));

        //Checking if the service is already running in the background
        mService = new JanataSensorsService();
        mServiceIntent = new Intent(this, mService.getClass());
        if (!isMyServiceRunning(mService.getClass())) {
            startService(mServiceIntent);
        }

        //On Click listeners for the Cards
        accelerometerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccelerometerDetails.class));
            }
        });

        proximityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProximityDetails.class));
            }
        });

        gyroCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GyroscopeDetails.class));
            }
        });

        lightCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LightDetails.class));
            }
        });

        resetDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = DatabaseHelper.getDB(getApplicationContext());
                databaseHelper.sensorsDao().deleteTable();
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerTxtView.setText("X : " + String.format("%.4f", sensorEvent.values[0]) + "\n" + "Y : " + String.format("%.4f", sensorEvent.values[1]) + "\n" + "Z : " + String.format("%.4f", sensorEvent.values[2]));
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroTxtView.setText("X : " + String.format("%.6f", sensorEvent.values[0]));
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
                proximityTxtView.setText(String.format("%.2f", sensorEvent.values[0]));
            }
            else {
                proximityTxtView.setText("Sensor Not Found");
            }
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
                lightTextView.setText(String.format("%.2f", sensorEvent.values[0]));
            }
            else {
                lightTextView.setText("Sensor Not Found");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    //Will be called when this activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main", "onResume: Called");
        //Registering the sensors
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Will be called when this activity is in background
    @Override
    protected void onPause() {
        Log.d("Main", "onPause: Called");
        sensorManager.unregisterListener((SensorEventListener) this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //Unregistering the Sensor Listener
        sensorManager.unregisterListener((SensorEventListener) this);

        //Sending data to the Boradcase Reciver to Restart the service.
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ServiceRestarter.class);
        this.sendBroadcast(broadcastIntent);
        Log.d("Main", "onDestroy: Called");
        super.onDestroy();

    }
}


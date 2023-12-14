package com.example.janatasensorsapp;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.janatasensorsapp.Models.SensorModel;

import java.time.LocalDateTime;

public class SensorData extends Activity implements SensorEventListener{
    private float accelerometerX, accelerometerY, accelerometerZ, gyroscope, proximity, light;
    //private Sensor sensorAccelerometer, sensorGyroscope, sensorProximity, sensorLight;
    private SensorManager sensorManager;
    private Context context;
    public SensorData(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public SensorModel getSensorData() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            String time = String.valueOf(localDateTime);
            SensorModel newSensorData = new SensorModel(time, accelerometerX, accelerometerY, accelerometerZ, gyroscope, proximity, light);
            Log.d("Data", "getSensorData: " + newSensorData.toString());
            return newSensorData;
        }
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
            accelerometerZ = event.values[2];
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroscope = event.values[0];
        }
        else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity = event.values[0];
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume: Called");
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: Called");
        sensorManager.unregisterListener(this);
    }

}

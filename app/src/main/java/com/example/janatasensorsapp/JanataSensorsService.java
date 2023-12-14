package com.example.janatasensorsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.janatasensorsapp.Models.SensorModel;

public class JanataSensorsService extends Service{
    private static int count = 0;
    private static SensorData sensorData;
    private SensorModel sensorModel;
    private Drawable drawable;
    private BitmapDrawable notificationIcon;
    private  Bitmap largeNotificationIcon;
    private NotificationManager notificationManager;
    private Notification notification;
    public static final String SHOW_SENSOR_DATA_CHANNEL = "notification";
    public static final int SHOW_SENSOR_DATA_CHANNEL_ID = 2;
    private JanataSensorsService mService;
    private Intent mServiceIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            //App crashes if this method is not called after closing it from task manager
            startForeground(1, new Notification());

    }

    //Starts the service in foreground
    public void startMyOwnForeground() {

        //Building the notification that will be used in the method "startForeground(2, notification);"
        String SERVICE_NOTIFICATION_CHANNEL_ID = "service_notification";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(SERVICE_NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLightColor(Color.BLUE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, SERVICE_NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.janata_sensor)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();


        //App crashes if this method is not called after closing it from task manager
        startForeground(2, notification);


        //Setting up the database for writing data
        sensorData = new SensorData(getApplicationContext());
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        //Setting up the resources for the notification that will view sensor data
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.janata_sensor, null);
        notificationIcon = (BitmapDrawable) drawable;
        largeNotificationIcon = notificationIcon.getBitmap();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Runnable method that will save data every 5 seconds.
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sensorModel = sensorData.getSensorData();
                Log.d("TAG", "run: + " + sensorModel.toString());
                databaseHelper.sensorsDao().createNew(sensorModel);
                showNotification(sensorModel);
                Toast.makeText(JanataSensorsService.this, "New Information Saved", Toast.LENGTH_SHORT).show();
                count++;
                handler.postDelayed(this,5000);
            }
        },20000);
    }

    public void showNotification(SensorModel sensorModel) {
        //Show notification every 1 Minutes
        if (count % 12 != 0) {
            Log.d("TAG", "showNotification If : Count Value " + count);
            return;
        }
        else {
            Log.d("TAG", "showNotification Else : Count Value " + count);
            String bigNotification = "Accelerometer : [" + sensorModel.getAccelerometerX() + ", " + sensorModel.getAccelerometerY() + ", " + sensorModel.getAccelerometerZ() + "]\n" + "Proximity : " + sensorModel.getProximity() +"\n" + "Gyroscope : " + sensorModel.getGyroscope() + "\n" + "Light : " + sensorModel.getLight();

            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new NotificationCompat.Builder(getApplicationContext(), SHOW_SENSOR_DATA_CHANNEL)
                        .setLargeIcon(largeNotificationIcon)
                        .setSmallIcon(R.drawable.janata_sensor)
                        .setContentText("New sensor information is available, expand to view them")
                        .setSubText("New Sensor Information")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(bigNotification))
                        .build();
                notificationManager.createNotificationChannel(new NotificationChannel(SHOW_SENSOR_DATA_CHANNEL, "Notificatin Channel", NotificationManager.IMPORTANCE_HIGH));
            }
            else {
                notification = new Notification.Builder(getApplicationContext())
                        .setLargeIcon(largeNotificationIcon)
                        .setSmallIcon(R.drawable.janata_sensor)
                        .setContentText("Accelerometer : [" + sensorModel.getAccelerometerX() + ", " + sensorModel.getAccelerometerY() + ", " + sensorModel.getAccelerometerZ() + "]\n" +
                                "Proximity : " + sensorModel.getProximity() +"\n" +
                                "Gyroscope : " + sensorModel.getGyroscope() + "\n" +
                                "Light : " + sensorModel.getLight())
                        .setSubText("New Sensor Information")
                        .build();
            }

            notificationManager.notify(SHOW_SENSOR_DATA_CHANNEL_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //Additional code will be places if needed
        Log.d("Janata Sensor Service", "onTaskRemoved: Called");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        //Additional code will be places if needed
        Log.d("Janata Sensor Service", "onDestroy: Called");
        super.onDestroy();
    }
}

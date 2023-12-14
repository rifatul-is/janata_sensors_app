package com.example.janatasensorsapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.janatasensorsapp.Interfaces.SensorsDao;
import com.example.janatasensorsapp.Models.SensorModel;

@Database(entities = SensorModel.class, exportSchema = false, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "janata_sensors_db";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract SensorsDao sensorsDao();
}

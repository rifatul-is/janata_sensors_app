package com.example.janatasensorsapp.Interfaces;

import androidx.room.Dao;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.janatasensorsapp.Models.SensorModel;

import java.util.List;

@Dao
public interface SensorsDao {
    @Query("select * from sensor_data")
    List<SensorModel> findAllSensorData();
    @Insert
    void createNew (SensorModel sensorModel);

    @Query("DELETE FROM sensor_data")
    void deleteTable();
}

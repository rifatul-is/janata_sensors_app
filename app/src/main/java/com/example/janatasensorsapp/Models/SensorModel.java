package com.example.janatasensorsapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "sensor_data")
public class SensorModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "time")
    private String localDateTime;
    @ColumnInfo(name = "accelerometer_x")
    private double accelerometerX;
    @ColumnInfo(name = "accelerometer_y")
    private double accelerometerY;
    @ColumnInfo(name = "accelerometer_z")
    private double accelerometerZ;
    @ColumnInfo(name = "gyroscope")
    private double gyroscope;
    @ColumnInfo(name = "proximity")
    private double proximity;
    @ColumnInfo(name = "light")
    private double light;

    public SensorModel(int id, String localDateTime, double accelerometerX, double accelerometerY, double accelerometerZ, double gyroscope, double proximity, double light) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.accelerometerX = accelerometerX;
        this.accelerometerY = accelerometerY;
        this.accelerometerZ = accelerometerZ;
        this.gyroscope = gyroscope;
        this.proximity = proximity;
        this.light = light;
    }
    @Ignore
    public SensorModel(String localDateTime, double accelerometerX, double accelerometerY, double accelerometerZ, double gyroscope, double proximity, double light) {
        this.localDateTime = localDateTime;
        this.accelerometerX = accelerometerX;
        this.accelerometerY = accelerometerY;
        this.accelerometerZ = accelerometerZ;
        this.gyroscope = gyroscope;
        this.proximity = proximity;
        this.light = light;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public double getAccelerometerX() {
        return accelerometerX;
    }

    public void setAccelerometerX(double accelerometerX) {
        this.accelerometerX = accelerometerX;
    }

    public double getAccelerometerY() {
        return accelerometerY;
    }

    public void setAccelerometerY(double accelerometerY) {
        this.accelerometerY = accelerometerY;
    }

    public double getAccelerometerZ() {
        return accelerometerZ;
    }

    public void setAccelerometerZ(double accelerometerZ) {
        this.accelerometerZ = accelerometerZ;
    }

    public double getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(double gyroscope) {
        this.gyroscope = gyroscope;
    }

    public double getProximity() {
        return proximity;
    }

    public void setProximity(double proximity) {
        this.proximity = proximity;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    @Override
    public String toString() {
        return "SensorModel{" +
                "id=" + id +
                ", localDateTime='" + localDateTime + '\'' +
                ", accelerometerX=" + accelerometerX +
                ", accelerometerY=" + accelerometerY +
                ", accelerometerZ=" + accelerometerZ +
                ", gyroscope=" + gyroscope +
                ", proximity=" + proximity +
                ", light=" + light +
                '}';
    }
}

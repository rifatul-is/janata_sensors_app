package com.example.janatasensorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.janatasensorsapp.Models.SensorModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class GyroscopeDetails extends AppCompatActivity {
    public final int DATA_COUNT_DISPLAY = 25;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_details);
        lineChart = findViewById(R.id.gyroscopeChart);
        int count = 0;

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        List<SensorModel> sensorData = databaseHelper.sensorsDao().findAllSensorData();
        ArrayList<Entry> chartData = new ArrayList<Entry>();
        ArrayList<Entry> finalChartData = new ArrayList<Entry>();

        for (SensorModel data: sensorData) {
            chartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getGyroscope()))));
            count++;
        }

        if (chartData.size() < DATA_COUNT_DISPLAY ) {
            for (SensorModel data: sensorData) {
                finalChartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getGyroscope()))));
                count++;
            }
        }
        else if (chartData.size() > DATA_COUNT_DISPLAY ) {
            for (int i = chartData.size() - DATA_COUNT_DISPLAY; i < chartData.size(); i++) {
                finalChartData.add(chartData.get(i));
            }
        }
        else {

        }

        Legend legend = lineChart.getLegend();
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();


        LineDataSet lineDataSet = new LineDataSet(finalChartData, "GyroScope Sensor");

        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSet);

        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.graphBuilder(legend, xAxis, yAxis, lineChart, lineDataSet,null, null);

        if (finalChartData.size() > 2) {
            lineChart.setData(lineData);
        }
        Log.d("Gyroscope Details", "onCreate: FinalChartData Size " + finalChartData.size());

    }
}
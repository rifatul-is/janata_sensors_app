package com.example.janatasensorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.janatasensorsapp.Models.SensorModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AccelerometerDetails extends AppCompatActivity {
    public final int DATA_COUNT_DISPLAY = 25; //The number of data points to be shown on the graph.
    private LineChart lineChart;
    private LineData lineData;
    private ArrayList<Entry> finalChartDataX, finalChartDataY, finalChartDataZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_details);

        lineChart = findViewById(R.id.accelerometerChart);
        int count = 0;

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        List<SensorModel> sensorData = databaseHelper.sensorsDao().findAllSensorData();
        ArrayList<Entry> chartDataX = new ArrayList<Entry>();
        ArrayList<Entry> chartDataY = new ArrayList<Entry>();
        ArrayList<Entry> chartDataZ = new ArrayList<Entry>();

        finalChartDataX = new ArrayList<Entry>();
        finalChartDataY = new ArrayList<Entry>();
        finalChartDataZ = new ArrayList<Entry>();

        //Getting all data from the database
        for (SensorModel data: sensorData) {
            chartDataX.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerX()))));
            chartDataY.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerY()))));
            chartDataZ.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerZ()))));

            count++;
        }

        //Checking retried data size fro the database
        //Inserting the last 25 Data from the retried data
        if (chartDataX.size() < DATA_COUNT_DISPLAY ) {
            for (SensorModel data: sensorData) {
                finalChartDataX.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerX()))));
                finalChartDataY.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerY()))));
                finalChartDataZ.add(new Entry(count, Float.parseFloat(String.valueOf(data.getAccelerometerZ()))));
                count++;
            }
        }
        else {
            for (int i = chartDataX.size() - DATA_COUNT_DISPLAY; i < chartDataX.size(); i++) {
                finalChartDataX.add(chartDataX.get(i));
                finalChartDataY.add(chartDataY.get(i));
                finalChartDataZ.add(chartDataZ.get(i));
            }
        }

        //Getting chart objects to use in graph builder
        Legend legend = lineChart.getLegend();
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();

        //Setting the data into the chart
        LineDataSet lineDataSetX = new LineDataSet(finalChartDataX, "X");
        LineDataSet lineDataSetY = new LineDataSet(finalChartDataY, "Y");
        LineDataSet lineDataSetZ = new LineDataSet(finalChartDataZ, "Z");

        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSetX);
        iLineDataSet.add(lineDataSetY);
        iLineDataSet.add(lineDataSetZ);

        lineData = new LineData(iLineDataSet);

        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.graphBuilder(legend, xAxis, yAxis, lineChart, lineDataSetX,lineDataSetY, lineDataSetZ);

        if (finalChartDataX.size() > 2 && finalChartDataY.size() > 2 && finalChartDataZ.size() > 2) {
            lineChart.setData(lineData);
        }
        Log.d("Accelerometer Details", "onCreate: FinalChartData Size " + finalChartDataX.size());
        Log.d("Accelerometer Details", "onCreate: FinalChartData Size " + finalChartDataY.size());
        Log.d("Accelerometer Details", "onCreate: FinalChartData Size " + finalChartDataZ.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (finalChartDataX.size() > 2 && finalChartDataY.size() > 2 && finalChartDataZ.size() > 2) {
            lineChart.setData(lineData);
        }
        Log.d("Accelerometer Details", "onResume: FinalChartData Size " + finalChartDataX.size());
        Log.d("Accelerometer Details", "onResume: FinalChartData Size " + finalChartDataY.size());
        Log.d("Accelerometer Details", "onResume: FinalChartData Size " + finalChartDataZ.size());
    }
}
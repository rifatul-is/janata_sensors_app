package com.example.janatasensorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.janatasensorsapp.DatabaseHelper;
import com.example.janatasensorsapp.Models.SensorModel;
import com.example.janatasensorsapp.R;
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

public class LightDetails extends AppCompatActivity {
    public final int DATA_COUNT_DISPLAY = 25; //The number of data points to be shown on the graph.
    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_details);

        lineChart = findViewById(R.id.lightChart);

        int count = 0;

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        List<SensorModel> sensorData = databaseHelper.sensorsDao().findAllSensorData();
        ArrayList<Entry> chartData = new ArrayList<Entry>();
        ArrayList<Entry> finalChartData = new ArrayList<Entry>();

        //Getting all data from the database
        for (SensorModel data: sensorData) {
            chartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getLight()))));
            count++;
        }

        //Checking retried data size fro the database
        //Inserting the last 25 Data from the retried data
        if (chartData.size() < DATA_COUNT_DISPLAY ) {
            //Log.i("I", "onCreate: Size less then 20");
            count = 0;
            for (SensorModel data: sensorData) {
                finalChartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getLight()))));
                count++;
                //Log.d("I", "onCreate: Light Data : " + data.getLight());
            }
        }
        else{
            //Log.i("I", "onCreate: Size more then 20");
            for (int i = chartData.size() - DATA_COUNT_DISPLAY; i < chartData.size(); i++) {
                finalChartData.add(chartData.get(i));
                //Log.d("I", "onCreate: Light Data : " + chartData.get(i));
            }
        }

        //Getting chart objects to use in graph builder
        Legend legend = lineChart.getLegend();
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();

        //Setting the data into the chart
        LineDataSet lineDataSet = new LineDataSet(finalChartData, "Light Sensor");

        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSet);

        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.graphBuilder(legend, xAxis, yAxis, lineChart, lineDataSet,null, null);

        if (finalChartData.size() > 2) {
            lineChart.setData(lineData);
        }
        Log.d("Light Details", "onCreate: FinalChartData Size " + finalChartData.size());
    }
}
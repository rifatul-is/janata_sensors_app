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

public class ProximityDetails extends AppCompatActivity {
    public final int DATA_COUNT_DISPLAY = 25; //The number of data points to be shown on the graph.
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_details);

        lineChart = findViewById(R.id.proximityChart);

        int count = 0;

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);
        List<SensorModel> sensorData = databaseHelper.sensorsDao().findAllSensorData();
        ArrayList<Entry> chartData = new ArrayList<Entry>();
        ArrayList<Entry> finalChartData = new ArrayList<Entry>();

        //Getting all data from the database
        for (SensorModel data: sensorData) {
            chartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getProximity()))));
            count++;
        }

        //Checking retried data size fro the database
        //Inserting the last 25 Data from the retried data
        if (chartData.size() < DATA_COUNT_DISPLAY ) {
            for (SensorModel data: sensorData) {
                finalChartData.add(new Entry(count, Float.parseFloat(String.valueOf(data.getProximity()))));
                Log.d("Proximity Details", "onCreate: ChartData Loop Size" + finalChartData.size());
                count++;
            }
        }
        else {
            for (int i = chartData.size() - DATA_COUNT_DISPLAY; i < chartData.size(); i++) {
                finalChartData.add(chartData.get(i));
                Log.d("Proximity Details", "onCreate: FinalChartData Loop Size" + finalChartData.size());
            }
        }

        //Getting chart objects to use in graph builder
        Legend legend = lineChart.getLegend();
        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();

        //Setting the data into the chart
        LineDataSet lineDataSet = new LineDataSet(finalChartData, "Proximity Sensor");
        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSet);

        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.graphBuilder(legend, xAxis, yAxis, lineChart, lineDataSet,null, null);

        if (finalChartData.size() > 2) {
            lineChart.setData(lineData);
        }
        Log.d("Proximity Details", "onCreate: FinalChartData Size " + finalChartData.size());

    }
}
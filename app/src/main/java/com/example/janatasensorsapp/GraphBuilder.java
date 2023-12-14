package com.example.janatasensorsapp;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class GraphBuilder {
    public void graphBuilder(Legend legend, XAxis xAxis, YAxis yAxis, LineChart lineChart, LineDataSet lineDataSet, LineDataSet lineDataSet2, LineDataSet lineDataSet3) {

        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(12);
        legend.setYEntrySpace(10);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        lineChart.setEnabled(false);
        //lineChart.setDrawGridBackground(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setDrawAxisLine(false);
        //xAxis.setDrawGridLines(false);

        // data has AxisDependency.LEFT
        //yAxis.setDrawLabels(true); // no axis labels
        //yAxis.setDrawAxisLine(false); // no axis line
        //yAxis.setDrawGridLines(false); // no grid lines
        yAxis.setDrawZeroLine(false); // draw a zero line
        lineChart.getAxisRight().setEnabled(false); // no right axis

        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.GRAY);
        lineDataSet.setCircleColor(Color.LTGRAY);
        //lineDataSet.setDrawCircles(false);
        //lineDataSet.setDrawValues(false);
        //lineDataSet.setCircleColor(Color.GRAY);

        if (lineDataSet2 != null ) {
            lineDataSet2.setLineWidth(2);
            lineDataSet2.setColor(Color.BLUE);
            lineDataSet2.setCircleColor(Color.BLUE);
            //lineDataSet2.setDrawCircles(false);
            //lineDataSet2.setDrawValues(false);
            //lineDataSet2.setCircleColor(Color.GRAY);
        }
        if (lineDataSet3 != null) {
            lineDataSet3.setLineWidth(2);
            lineDataSet3.setColor(Color.MAGENTA);
            lineDataSet3.setCircleColor(Color.MAGENTA);
            //lineDataSet3.setDrawCircles(false);
            //lineDataSet3.setDrawValues(false);
            //lineDataSet3.setCircleColor(Color.GRAY);
        }

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
    }
}

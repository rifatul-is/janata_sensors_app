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
    //Excess code that was repeated in all of the Detailed Activities
    public void graphBuilder(Legend legend, XAxis xAxis, YAxis yAxis, LineChart lineChart, LineDataSet lineDataSet, LineDataSet lineDataSet2, LineDataSet lineDataSet3) {

        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(12);
        legend.setYEntrySpace(10);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        lineChart.setEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxis.setDrawZeroLine(false); // draw a zero line
        lineChart.getAxisRight().setEnabled(false); // no right axis

        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.GRAY);
        lineDataSet.setCircleColor(Color.LTGRAY);

        if (lineDataSet2 != null ) {
            lineDataSet2.setLineWidth(2);
            lineDataSet2.setColor(Color.BLUE);
            lineDataSet2.setCircleColor(Color.BLUE);
        }
        if (lineDataSet3 != null) {
            lineDataSet3.setLineWidth(2);
            lineDataSet3.setColor(Color.MAGENTA);
            lineDataSet3.setCircleColor(Color.MAGENTA);
        }

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
    }
}

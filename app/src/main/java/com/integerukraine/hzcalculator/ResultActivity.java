package com.integerukraine.hzcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;

public class ResultActivity extends AppCompatActivity {

    LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initViews();
        initChart();


    }

    private void initChart() {
        String[] x = new String[]{"1", "2", "3", "4", "5"};
        float[] y = new float[]{10, 20, 30, 40, 50};
        LineSet dataset = new LineSet(x, y);
        dataset.setColor(Color.parseColor("#a34545"))
                .setFill(Color.parseColor("#a34545"))
                .setSmooth(true);
        lineChartView.addData(dataset);
        lineChartView.setStep(10);

        lineChartView.show();
    }

    private void initViews() {
        lineChartView = (LineChartView) findViewById(R.id.linechart);
    }

}

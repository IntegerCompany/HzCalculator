package com.integerukraine.hzcalculator;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.integerukraine.hzcalculator.calculations.Calculations;
import com.integerukraine.hzcalculator.calculations.ChartCalculator;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    Calculations calculations;
    ChartCalculator chartCalculator = new ChartCalculator();
    DecimalFormat decimalFormat = new DecimalFormat("#.###");

    LineChartView lineChartView;
    TextView totalErp, erpInWatts, tvFreeSpacePathLoss, tvTotalReceive, tvFresnelRadiusObstical, tvObstacleClearanceRequired, tv1stFresnelRadius, tvEarthHeightMidpoint,
            tvWavelengthAlpha, tvTheoreticalBeamwidth, tvLinkBudget, tvSystemOperatingMargin, tvLos, tvControlled, tvUncontrolled, tvControlled2, tvUncontrolled2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        calculations = (Calculations) getIntent().getSerializableExtra("Calculations");
        initViews();
        initChart();
        fillViewsWithData();


    }

    private void fillViewsWithData() {
        DecimalFormat tempDecFormat = new DecimalFormat("#.#");
        totalErp.setText("Total ERP (dB): " + tempDecFormat.format(calculations.getTotalErp_dB()));
        tempDecFormat.applyPattern("#.##");
        erpInWatts.setText("ERP In Watts: " + tempDecFormat.format(calculations.getErpInWatts()));
        tempDecFormat.applyPattern("#.###");
        tvFreeSpacePathLoss.setText("Free Space Path Loss: " + tempDecFormat.format(calculations.getFreeSpacePathLoss()));
        tempDecFormat.applyPattern("#.##");
        tvTotalReceive.setText("Total Receive (dBm): " + tempDecFormat.format(calculations.getTotalReceive_dBm()));
        tempDecFormat.applyPattern("#.##");
        tvFresnelRadiusObstical.setText("Fresnel Radius at Obstical (M): " + tempDecFormat.format(calculations.getFresnelRadiusAtObstical_M()));
        tvObstacleClearanceRequired.setText("Obstacle Clearance Required (M): " + tempDecFormat.format(calculations.getObstacleClearanceRequired_M()));
        tv1stFresnelRadius.setText("1st Fresnel Radius (M): " + tempDecFormat.format(calculations.getFresnelRadius1st_M()));
        tvEarthHeightMidpoint.setText("Earth Height -Mid Point (M): " + tempDecFormat.format(calculations.getEarthHeightMidpoint_M()));
        tempDecFormat.applyPattern("#.###");
        tvWavelengthAlpha.setText("Wavelength λ (m): " + tempDecFormat.format(calculations.getWavelengthAlpha_m()));
        tempDecFormat.applyPattern("#.##");
        tvTheoreticalBeamwidth.setText("Theoretical 3dB Beamwidtht: " + tempDecFormat.format(calculations.getTheoretical3dBBeamwidth()));
        tempDecFormat.applyPattern("#.###");
        tvLinkBudget.setText("Link Budget (dB): " + tempDecFormat.format(calculations.getLinkBudget_dB()));
        tempDecFormat.applyPattern("#.##");
        tvSystemOperatingMargin.setText("System Operating margin (SAD): " + tempDecFormat.format(calculations.getSystemOperatingMargin_SAD()) + "%");
        tempDecFormat.applyPattern("#.##");
        tvLos.setText("LoS (in KM): " + tempDecFormat.format(calculations.getLoS_KM()));
        tvControlled.setText("Controlled Environment (m): " + tempDecFormat.format(calculations.getReflectionsControlledEnvironment_m()));
        tvUncontrolled.setText("Uncontrolled Environment (m): " + tempDecFormat.format(calculations.getReflectionsUncontrolledEnvironment_m()));
        tvControlled2.setText("Controlled Environment (m): " + tempDecFormat.format(calculations.getControlledEnvironment_m()));
        tvUncontrolled2.setText("Uncontrolled Environment (m): " + tempDecFormat.format(calculations.getUncontrolledEnvironment_m()));
    }

    private void initChart() {
        LineSet mainChartDataset = new LineSet();
        LineSet freeSpaceDataset = new LineSet();
        for (int range = 10; range < 80; range++) {
            mainChartDataset.addPoint(((range % 10) == 0) ? range + "" : "", (float) chartCalculator.calculateCurvedEarth(calculations.getFrequency_MHz(), calculations.getAntenna1(), calculations.getAntenna2(), range, calculations.getPolarization(), calculations.getGroundType(), ChartCalculator.dBm));
            freeSpaceDataset.addPoint(((range % 10) == 0) ? range + "" : "", (float) chartCalculator.calculateCurvedEarth(calculations.getFrequency_MHz(), calculations.getAntenna1(), calculations.getAntenna2(), range, calculations.getPolarization(), ChartCalculator.FREE_SPACE, ChartCalculator.dBm));
        }
        freeSpaceDataset.setSmooth(true)
                .setThickness(4f);
        mainChartDataset.setSmooth(true)
                .setThickness(4f);
        freeSpaceDataset.setColor(Color.parseColor("#990000"));
        mainChartDataset.setColor(Color.parseColor("#000099"));
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#666666"));
        lineChartView.setGrid(ChartView.GridType.HORIZONTAL, paint);
        lineChartView.addData(mainChartDataset);
        lineChartView.addData(freeSpaceDataset);
        lineChartView.setStep(10);
        lineChartView.setAxisBorderValues(-200, -100, 10);
        lineChartView.setShadow(0.5f, 5, 5, Color.parseColor("#999999"));


        lineChartView.show();
    }

    private void initViews() {
        lineChartView = (LineChartView) findViewById(R.id.linechart);

        totalErp = (TextView) findViewById(R.id.tv_total_erp);
        erpInWatts = (TextView) findViewById(R.id.tv_erp_in_watts);
        tvFreeSpacePathLoss = (TextView) findViewById(R.id.tv_free_space_path_loss);
        tvTotalReceive = (TextView) findViewById(R.id.tv_total_receive);
        tvFresnelRadiusObstical = (TextView) findViewById(R.id.tv_fresnel_radius_obstical);
        tvObstacleClearanceRequired = (TextView) findViewById(R.id.tv_obstacle_clearance_required);
        tv1stFresnelRadius = (TextView) findViewById(R.id.tv_fresnel_radius1);
        tvEarthHeightMidpoint = (TextView) findViewById(R.id.tv_earth_height_midpoint);
        tvWavelengthAlpha = (TextView) findViewById(R.id.tv_wavelength);
        tvTheoreticalBeamwidth = (TextView) findViewById(R.id.tv_theoretical_beamwidth);
        tvLinkBudget = (TextView) findViewById(R.id.tv_link_budget);
        tvSystemOperatingMargin = (TextView) findViewById(R.id.tv_system_operating_margin);
        tvLos = (TextView) findViewById(R.id.tv_los);
        tvControlled = (TextView) findViewById(R.id.tv_controlled);
        tvUncontrolled = (TextView) findViewById(R.id.tv_uncontrolled);
        tvControlled2 = (TextView) findViewById(R.id.tv_controlled2);
        tvUncontrolled2 = (TextView) findViewById(R.id.tv_uncontrolled2);

    }


}

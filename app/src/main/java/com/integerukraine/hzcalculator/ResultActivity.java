package com.integerukraine.hzcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.integerukraine.hzcalculator.calculations.Calculations;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    Calculations calculations;
    DecimalFormat decimalFormat = new DecimalFormat("#.###");

    LineChartView lineChartView;
    TextView totalErp, erpInWatts, tvTotalReceive, tvFresnelRadiusObstical, tvObstacleClearanceRequired, tv1stFresnelRadius, tvEarthHeightMidpoint,
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
        totalErp.setText("Total ERP (dB): " + decimalFormat.format(calculations.getTotalErp_dB()));
        erpInWatts.setText("ERP In Watts: " + decimalFormat.format(calculations.getErpInWatts()));
        tvTotalReceive.setText("Total Receive (dBm): " + decimalFormat.format(calculations.getTotalReceive_dBm()));
        tvFresnelRadiusObstical.setText("Total Receive (dBm): " + decimalFormat.format(calculations.getFresnelRadiusAtObstical_M()));
        tvObstacleClearanceRequired.setText("Obstacle Clearance Required (M): " + decimalFormat.format(calculations.getObstacleClearanceRequired_M()));
        tv1stFresnelRadius.setText("1st Fresnel Radius (M): " + decimalFormat.format(calculations.getFresnelRadius1st_M()));
        tvEarthHeightMidpoint.setText("Earth Height -Mid Point (M): " + decimalFormat.format(calculations.getEarthHeightMidpoint_M()));
        tvWavelengthAlpha.setText("Wavelength λ (m): " + decimalFormat.format(calculations.getWavelengthAlpha_m()));
        tvTheoreticalBeamwidth.setText("Theoretical 3dB Beamwidtht: " + decimalFormat.format(calculations.getTheoretical3dBBeamwidth()));
        tvLinkBudget.setText("Link Budget (dB): " + decimalFormat.format(calculations.getLinkBudget_dB()));
        tvSystemOperatingMargin.setText("System Operating margin (SAD): " + decimalFormat.format(calculations.getSystemOperatingMargin_SAD()));
        tvLos.setText("LoS (in KM): " + decimalFormat.format(calculations.getLoS_KM()));
        tvControlled.setText("Controlled Environment (m): " + decimalFormat.format(calculations.getControlledEnvironment_m()));
        tvUncontrolled.setText("Uncontrolled Environment (m): " + decimalFormat.format(calculations.getReflectionsUncontrolledEnvironment_m()));
        tvControlled2.setText("Controlled Environment (m): " + decimalFormat.format(calculations.getControlledEnvironment_m()));
        tvUncontrolled2.setText("Uncontrolled Environment (m): " + decimalFormat.format(calculations.getUncontrolledEnvironment_m()));
    }

    private void initChart() {
        String[] x = new String[]{"1", "2", "3", "4", "5"};
        float[] y = new float[]{10, 20, 30, 40, 50};
        String[] x2 = new String[]{"1", "2", "3", "4", "5"};
        float[] y2 = new float[]{5, 8, 20, 34, 25};
        LineSet dataset = new LineSet(x, y);
        dataset.setColor(Color.parseColor("#990000"))
                .setSmooth(true)
                .getThickness();
        lineChartView.addData(dataset);
        LineSet dataset2 = new LineSet(x2, y2);
        dataset.setColor(Color.parseColor("#000099"))
                .setSmooth(true);
        lineChartView.addData(dataset);
        lineChartView.addData(dataset2);
        lineChartView.setStep(10);
        lineChartView.setShadow(0.5f, 5, 5, Color.parseColor("#999999"));


        lineChartView.show();
    }

    private void initViews() {
        lineChartView = (LineChartView) findViewById(R.id.linechart);

        totalErp = (TextView) findViewById(R.id.tv_total_erp);
        erpInWatts = (TextView) findViewById(R.id.tv_erp_in_watts);
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

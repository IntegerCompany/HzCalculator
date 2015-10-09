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

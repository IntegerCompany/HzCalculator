package com.integerukraine.hzcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.integerukraine.hzcalculator.calculations.Calculations;

public class MainActivity extends AppCompatActivity {

    Calculations calculations = new Calculations();

    //Tx Gain
    EditText etTxOutput, etTxAntennaGain, etTxCableLosses;
    TextView tvTotalErp, tvErpWatts;

    //RX Gain
    EditText etRxSensivity, etRxAnntennaGain, etRxCableLosses;
    TextView tvTotalReceive;

    //Free Space Path Loss
    EditText etFrequency, etDistance;
    TextView tvFreeSpacePathLoss;

    //Fresnel at specific point
    EditText etDistanceObstical, etTotalLinkDistance;
    TextView tvFresnelRadiusObstical, tvObstacleClearanceRequired;

    // 1st Fresnel Radius (at midpoint)
    EditText etLinkDistance;
    TextView tv1stFresnelRadius, tvEarthHeightMidpoint;

    // Line Of Site
    EditText etAntenna1Height, etAntenna2Height;
    TextView tvLos;

    // dBm- Watts
    EditText etPowerDbm;
    TextView tvPowerWatts, tvPowerMWatts;

    // Watts- dBm
    EditText etPowerWatts;
    TextView tvPowerDbm;

    // mWatts- dBm
    EditText etPowerMWatts;
    TextView tvPowerDbm2;

    // mWatts- dBm
    EditText etDiameter, etGain;
    TextView tvTheoreticalBeamwidth;

    // Power Density at Range ® metres
    EditText etPowerDensityRangeMetres;
    TextView tvPowerDensity;

    // Distance Miles
    EditText etDistanceMiles;
    TextView tvDistanceKilometres, tvDistanceNaughticalMiles;

    // Distance Kilometres
    EditText etDistanceKilometres;
    TextView tvDistanceMiles, tvDistanceNaughticalMiles2;

    // Power Density at Range ® metres
    EditText etDistanceNaughticalMiles;
    TextView tvDistanceMiles2, tvDistanceKilometres2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initUiListeners();
    }

    private void initUiListeners() {
        //Tx Gain
        TextWatcher txGainListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etTxOutput.getText().length() > 0 & etTxAntennaGain.getText().length() > 0 & etTxCableLosses.getText().length() > 0) {
                    calculations.calculateTxGain(
                            Double.parseDouble(etTxOutput.getText().toString()),
                            Double.parseDouble(etTxAntennaGain.getText().toString()),
                            Double.parseDouble(etTxCableLosses.getText().toString()));
                    tvTotalErp.setText("Total ERP (dB): " + calculations.getTotalErp_dB());
                    tvErpWatts.setText("ERP In Watts: " + calculations.getErpInWatts());
                } else {
                    tvTotalErp.setText("");
                    tvErpWatts.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etTxOutput.addTextChangedListener(txGainListener);
        etTxAntennaGain.addTextChangedListener(txGainListener);
        etTxCableLosses.addTextChangedListener(txGainListener);


        //RX Gain
        TextWatcher rxGainListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etRxSensivity.getText().length() > 0 & etRxAnntennaGain.getText().length() > 0 & etRxCableLosses.getText().length() > 0) {
                    calculations.calculateRxGain(
                            Double.parseDouble(etRxSensivity.getText().toString()),
                            Double.parseDouble(etRxAnntennaGain.getText().toString()),
                            Double.parseDouble(etRxCableLosses.getText().toString()));
                    tvTotalReceive.setText("Total Receive (dBm): " + calculations.getTotalReceive_dBm());
                } else {
                    tvTotalReceive.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etRxSensivity.addTextChangedListener(rxGainListener);
        etRxAnntennaGain.addTextChangedListener(rxGainListener);
        etRxCableLosses.addTextChangedListener(rxGainListener);

        //Free Space Path Loss
        TextWatcher freeSpacePathLossListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etFrequency.getText().length() > 0 & etDistance.getText().length() > 0) {
                    calculations.calculateFreeSpacePathLoss(
                            Double.parseDouble(etFrequency.getText().toString()),
                            Double.parseDouble(etDistance.getText().toString()));
                    tvFreeSpacePathLoss.setText("Free Space Path Loss: " + calculations.getFreeSpacePathLoss());
                } else {
                    tvFreeSpacePathLoss.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etFrequency.addTextChangedListener(freeSpacePathLossListener);
        etDistance.addTextChangedListener(freeSpacePathLossListener);

        //Fresnel at specific point
        TextWatcher fresnelSpecificPointListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etDistanceObstical.getText().length() > 0 & etTotalLinkDistance.getText().length() > 0) {
                    calculations.calculateFreeSpacePathLoss(
                            Double.parseDouble(etFrequency.getText().toString()),
                            Double.parseDouble(etDistance.getText().toString()));
                    tvFreeSpacePathLoss.setText("Free Space Path Loss: " + calculations.getFreeSpacePathLoss());
                } else {
                    tvFreeSpacePathLoss.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        EditText etDistanceObstical, etTotalLinkDistance;
        TextView tvFresnelRadiusObstical, tvObstacleClearanceRequired;

        // 1st Fresnel Radius (at midpoint)
        EditText etLinkDistance;
        TextView tv1stFresnelRadius, tvEarthHeightMidpoint;

        // Line Of Site
        EditText etAntenna1Height, etAntenna2Height;
        TextView tvLos;

        // dBm- Watts
        EditText etPowerDbm;
        TextView tvPowerWatts, tvPowerMWatts;

        // Watts- dBm
        EditText etPowerWatts;
        TextView tvPowerDbm;

        // mWatts- dBm
        EditText etPowerMWatts;
        TextView tvPowerDbm2;

        // mWatts- dBm
        EditText etDiameter, etGain;
        TextView tvTheoreticalBeamwidth;

        // Power Density at Range ® metres
        EditText etPowerDensityRangeMetres;
        TextView tvPowerDensity;

        // Distance Miles
        EditText etDistanceMiles;
        TextView tvDistanceKilometres, tvDistanceNaughticalMiles;

        // Distance Kilometres
        EditText etDistanceKilometres;
        TextView tvDistanceMiles, tvDistanceNaughticalMiles2;

        // Power Density at Range ® metres
        EditText etDistanceNaughticalMiles;
        TextView tvDistanceMiles2, tvDistanceKilometres2;
    }

    private void initViews() {
        //TX Gain
        etTxOutput = (EditText) findViewById(R.id.et_tx_output);
        etTxAntennaGain = (EditText) findViewById(R.id.et_tx_antenna_gain);
        etTxCableLosses = (EditText) findViewById(R.id.et_tx_cable_losses);
        tvTotalErp = (TextView) findViewById(R.id.tv_total_erp);
        tvErpWatts = (TextView) findViewById(R.id.tv_erp_watts);

        //RX Gain
        etRxSensivity = (EditText) findViewById(R.id.etRxSensivity);
        etRxAnntennaGain = (EditText) findViewById(R.id.etRxAnntennaGain);
        etRxCableLosses = (EditText) findViewById(R.id.etRxCableLosses);
        tvTotalReceive = (TextView) findViewById(R.id.tv_total_receive);

        //Free Space Path Loss
        etFrequency = (EditText) findViewById(R.id.et_frequency);
        etDistance = (EditText) findViewById(R.id.et_distance);
        tvFreeSpacePathLoss = (TextView) findViewById(R.id.tv_free_space_path_loss);

        //Fresnel at specific point
        etDistanceObstical = (EditText) findViewById(R.id.et_distance_obstical);
        etTotalLinkDistance = (EditText) findViewById(R.id.et_total_link_distance);
        tvFresnelRadiusObstical = (TextView) findViewById(R.id.tv_fresnel_radius_obstical);
        tvObstacleClearanceRequired = (TextView) findViewById(R.id.tv_obstacle_clearance_required);

        // 1st Fresnel Radius (at midpoint)
        etLinkDistance = (EditText) findViewById(R.id.et_link_distance);
        tv1stFresnelRadius = (TextView) findViewById(R.id.tv_1st_fresnel_radius);
        tvEarthHeightMidpoint = (TextView) findViewById(R.id.tv_earth_height_midpoint);

        // Line Of Site
        etAntenna1Height = (EditText) findViewById(R.id.et_antenna1_height);
        etAntenna2Height = (EditText) findViewById(R.id.et_antenna2_height);
        tvLos = (TextView) findViewById(R.id.tv_los);

        // dBm- Watts
        etPowerDbm = (EditText) findViewById(R.id.et_power_dbm);
        tvPowerWatts = (TextView) findViewById(R.id.tv_power_watts);
        tvPowerMWatts = (TextView) findViewById(R.id.tv_power_mwatts);

        // Watts- dBm
        etPowerWatts = (EditText) findViewById(R.id.et_power_watts);
        tvPowerDbm = (TextView) findViewById(R.id.tv_power_dbm);

        // mWatts- dBm
        etPowerMWatts = (EditText) findViewById(R.id.et_power_mwatts);
        tvPowerDbm2 = (TextView) findViewById(R.id.tv_power_dbm2);

        // mWatts- dBm
        etDiameter = (EditText) findViewById(R.id.et_diameter);
        etGain = (EditText) findViewById(R.id.et_gain);
        tvTheoreticalBeamwidth = (TextView) findViewById(R.id.tv_theoretical_beamwidth);

        // Power Density at Range ® metres
        etPowerDensityRangeMetres = (EditText) findViewById(R.id.et_power_density_range_metres);
        tvPowerDensity = (TextView) findViewById(R.id.tv_power_density);

        // Distance Miles
        etDistanceMiles = (EditText) findViewById(R.id.et_distance_miles);
        tvDistanceKilometres = (TextView) findViewById(R.id.tv_distance_kilometres);
        tvDistanceNaughticalMiles = (TextView) findViewById(R.id.tv_distance_naughtical_miles);

        // Distance Kilometres
        etDistanceKilometres = (EditText) findViewById(R.id.et_distance_kilometres);
        tvDistanceMiles = (TextView) findViewById(R.id.tv_distance_miles);
        tvDistanceNaughticalMiles2 = (TextView) findViewById(R.id.tv_distance_naughtical_miles2);

        // Power Density at Range ® metres
        etDistanceNaughticalMiles = (EditText) findViewById(R.id.et_distance_naughtical_miles);
        tvDistanceMiles2 = (TextView) findViewById(R.id.tv_distance_miles2);
        tvDistanceKilometres2 = (TextView) findViewById(R.id.tv_distance_kilometres2);
    }


}

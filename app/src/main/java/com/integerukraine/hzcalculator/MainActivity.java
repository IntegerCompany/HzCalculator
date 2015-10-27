package com.integerukraine.hzcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.integerukraine.hzcalculator.calculations.Calculations;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Calculations calculations = new Calculations();
    DecimalFormat decimalFormat = new DecimalFormat("#.###");
    //    FloatingActionButton fab;
    Button submitBtn;
    ArrayList<EditText> requiredEditTexts = new ArrayList<>();


    // Init data
    EditText etFade, etRain;
    Spinner polarizationSpinner, groundTypeSpinner;
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
    EditText etDistanceObstical;
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

    // Parabolic Antenna Gain
    EditText etDiameter;
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
        try {
        initViews();
            initSpinners();
        initUiListeners();
        initSubmitButton();
        initRequiredEditTexts();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSpinners() {
        ArrayList<String> polarization = new ArrayList<String>();
        polarization.add("Vertical");
        polarization.add("Horizontal");

        ArrayAdapter<String> polarizationAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, polarization);

        polarizationAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        ArrayList<String> groundType = new ArrayList<String>();
        groundType.add("Dry");
        groundType.add("Average");
        groundType.add("Sea");
        groundType.add("Water");
        groundType.add("Wet");
        groundType.add("Free Space");

        ArrayAdapter<String> groundTypeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, groundType);

        groundTypeAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        polarizationSpinner.setAdapter(polarizationAdapter);
        groundTypeSpinner.setAdapter(groundTypeAdapter);
    }

    private void initSubmitButton() {

        submitBtn = (Button) findViewById(R.id.btn_submit);
        submitBtn.setEnabled(false);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculations.setGroundType(groundTypeSpinner.getSelectedItemPosition() + 1);
                calculations.setPolarization(polarizationSpinner.getSelectedItemPosition() + 1);
                calculations.calculate_1stFresnelRadiusAtMidpoint(Double.parseDouble(etDistance.getText().toString()), Double.parseDouble(etFrequency.getText().toString()));
                calculations.calculate_LinkBudget_dB();
                calculations.calculate_SafeWorkingDistance(Double.parseDouble(etFrequency.getText().toString()));
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("Calculations", calculations);
                startActivity(intent);
            }
        });
    }

    private boolean isAllRequiredEditTextsFilled() {

        for (EditText editText : requiredEditTexts) {

            if (editText.getText().length() == 0) return false;
        }
        return true;
    }

    private void initRequiredEditTexts() {
        requiredEditTexts.add(etFade);
        requiredEditTexts.add(etRain);
        requiredEditTexts.add(etTxOutput);
        requiredEditTexts.add(etTxAntennaGain);
        requiredEditTexts.add(etTxCableLosses);
        requiredEditTexts.add(etRxSensivity);
        requiredEditTexts.add(etRxAnntennaGain);
        requiredEditTexts.add(etRxCableLosses);
        requiredEditTexts.add(etFrequency);
        requiredEditTexts.add(etDistance);
        requiredEditTexts.add(etDistanceObstical);
        requiredEditTexts.add(etAntenna1Height);
        requiredEditTexts.add(etAntenna2Height);
//        requiredEditTexts.add(etPowerDbm);
//        requiredEditTexts.add(etPowerWatts);
//        requiredEditTexts.add(etPowerMWatts);
        requiredEditTexts.add(etDiameter);
        requiredEditTexts.add(etPowerDensityRangeMetres);
//        requiredEditTexts.add(etDistanceMiles);
//        requiredEditTexts.add(etDistanceKilometres);
//        requiredEditTexts.add(etDistanceNaughticalMiles);
    }

//    private void initFloatActionButton() {
//        fab = (FloatingActionButton) findViewById(R.id.float_action_button);
//        fab.hide();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//                intent.putExtra("Calculations", calculations);
//                startActivity(intent);
//            }
//        });
//    }

    private void initUiListeners() throws Exception {
        //init data
        TextWatcher initDataListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etFade.getText().length() > 0 & etRain.getText().length() > 0) {
                    calculations.init(
                            Double.parseDouble(etFade.getText().toString()),
                            Double.parseDouble(etRain.getText().toString()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etFade.addTextChangedListener(initDataListener);
        etRain.addTextChangedListener(initDataListener);
        //Tx Gain
        TextWatcher txGainListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
 try{
                    if (etTxOutput.getText().length() > 0 & etTxAntennaGain.getText().length() > 0 & etTxCableLosses.getText().length() > 0) {
                        calculations.calculate_TxGain(
                                Double.parseDouble(etTxOutput.getText().toString()),
                                Double.parseDouble(etTxAntennaGain.getText().toString()),
                                Double.parseDouble(etTxCableLosses.getText().toString()));
                        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                        tvTotalErp.setText("Total ERP (dB): " + decimalFormat.format(calculations.getTotalErp_dB()));
                        decimalFormat = new DecimalFormat("#.##");
                        tvErpWatts.setText("ERP In Watts: " + decimalFormat.format(calculations.getErpInWatts()));
                        submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                    } else {
                        tvTotalErp.setText("");
                        tvErpWatts.setText("");
                    }
 }catch(NumberFormatException e){

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
                try{
                if (etRxSensivity.getText().length() > 0 & etRxAnntennaGain.getText().length() > 0 & etRxCableLosses.getText().length() > 0) {
                    calculations.calculate_RxGain(
                            Double.parseDouble(etRxSensivity.getText().toString()),
                            Double.parseDouble(etRxAnntennaGain.getText().toString()),
                            Double.parseDouble(etRxCableLosses.getText().toString()));
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    tvTotalReceive.setText("Total Receive (dBm): " + decimalFormat.format(calculations.getTotalReceive_dBm()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvTotalReceive.setText("");
                }
                }catch(NumberFormatException e){

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
                try{
                if (etFrequency.getText().length() > 0 & etDistance.getText().length() > 0) {
                    calculations.calculate_FreeSpacePathLoss(
                            Double.parseDouble(etFrequency.getText().toString()),
                            Double.parseDouble(etDistance.getText().toString()));
                    tvFreeSpacePathLoss.setText("Free Space Path Loss: " + decimalFormat.format(calculations.getFreeSpacePathLoss()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvFreeSpacePathLoss.setText("");
                }
                }catch(NumberFormatException e){

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
                try{
                if (etDistanceObstical.getText().length() > 0 & etDistance.getText().length() > 0 & etFrequency.getText().length() > 0) {
                    calculations.calculate_FresnelAtSpecificPoint(
                            Double.parseDouble(etDistanceObstical.getText().toString()),
                            Double.parseDouble(etDistance.getText().toString()),
                            Double.parseDouble(etFrequency.getText().toString()));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    tvFresnelRadiusObstical.setText("Fresnel Radius at Obstical (M): " + decimalFormat.format(calculations.getFresnelRadiusAtObstical_M()));
                    tvObstacleClearanceRequired.setText("Fresnel Radius at Obstical (M): " + decimalFormat.format(calculations.getObstacleClearanceRequired_M()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvFresnelRadiusObstical.setText("");
                    tvObstacleClearanceRequired.setText("");
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etDistanceObstical.addTextChangedListener(fresnelSpecificPointListener);
        etDistance.addTextChangedListener(fresnelSpecificPointListener);
        etFrequency.addTextChangedListener(fresnelSpecificPointListener);

//        // 1st Fresnel Radius (at midpoint)
//        TextWatcher fresnelMidpointPointListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (etLinkDistance.getText().length() > 0 & etFrequency.getText().length() > 0) {
//                    calculations.calculate_1stFresnelRadiusAtMidpoint(
//                            Double.parseDouble(etDistance.getText().toString()),
//                            Double.parseDouble(etFrequency.getText().toString()));
//                    tv1stFresnelRadius.setText("1st Fresnel Radius (M): " + decimalFormat.format(calculations.getFresnelRadius1st_M()));
//                    tvEarthHeightMidpoint.setText("Earth Height -Mid Point (M): 88.88" + decimalFormat.format(calculations.getEarthHeightMidpoint_M()));
//                    if (isAllRequiredEditTextsFilled()) {
//                        fab.show();
//                    } else {
//                        fab.hide();
//                    }
//                } else {
//                    tv1stFresnelRadius.setText("");
//                    tvEarthHeightMidpoint.setText("");
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        etLinkDistance.addTextChangedListener(fresnelMidpointPointListener);
//        etFrequency.addTextChangedListener(fresnelMidpointPointListener);

        // Line Of Site
        TextWatcher lineOfSiteListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etAntenna1Height.getText().length() > 0 & etAntenna2Height.getText().length() > 0) {
                    calculations.calculate_LineOfSite(
                            Double.parseDouble(etAntenna1Height.getText().toString()),
                            Double.parseDouble(etAntenna2Height.getText().toString()));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    tvLos.setText("LoS (in KM): " + decimalFormat.format(calculations.getLoS_KM()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvLos.setText("");
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etAntenna1Height.addTextChangedListener(lineOfSiteListener);
        etAntenna2Height.addTextChangedListener(lineOfSiteListener);

        // dBm- Watts
        TextWatcher dbmWattsListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etPowerDbm.getText().length() > 0) {
                    calculations.calculate_dBmWatts(
                            Double.parseDouble(etPowerDbm.getText().toString()));
                    tvPowerWatts.setText("Power in Watts: " + decimalFormat.format(calculations.getPower_W()));
                    tvPowerMWatts.setText("Power in mW: " + decimalFormat.format(calculations.getPower_mW()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvPowerWatts.setText("");
                    tvPowerMWatts.setText("");
                }

                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etPowerDbm.addTextChangedListener(dbmWattsListener);

        // Watts- dBm
        TextWatcher wattsDbmListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etPowerWatts.getText().length() > 0) {
                    calculations.calculate_WattsdBm(
                            Double.parseDouble(etPowerWatts.getText().toString()));
                    tvPowerDbm.setText("Power in dBm: " + decimalFormat.format(calculations.getPower_dBm()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvPowerDbm.setText("");
                }
                }catch(NumberFormatException e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etPowerWatts.addTextChangedListener(wattsDbmListener);

        // mWatts- dBm
        TextWatcher mwattsDbmListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPowerMWatts.getText().length() > 0) {
                    calculations.calculate_mWattsdBm(
                            Double.parseDouble(etPowerMWatts.getText().toString()));
                    tvPowerDbm2.setText("Power in dBm: " + decimalFormat.format(calculations.getPower_dBm()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvPowerDbm2.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etPowerMWatts.addTextChangedListener(mwattsDbmListener);

        // Parabolic Antenna Gain
        TextWatcher parabolicAntennaGainListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etDiameter.getText().length() > 0 & etFrequency.getText().length() > 0) {
                    calculations.calculate_WavelengthAlpha(Double.parseDouble(etFrequency.getText().toString()));
                    calculations.calculate_ParabolicAntennaGain(
                            Double.parseDouble(etDiameter.getText().toString()));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    tvTheoreticalBeamwidth.setText("Theoretical 3dB Beamwidth: " + decimalFormat.format(calculations.getTheoretical3dBBeamwidth()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvTheoreticalBeamwidth.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etDiameter.addTextChangedListener(parabolicAntennaGainListener);


        // Power Density at Range ® metres
        TextWatcher powerDensityListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etPowerDensityRangeMetres.getText().length() > 0) {
                    calculations.calculate_PowerDensity(
                            Double.parseDouble(etPowerDensityRangeMetres.getText().toString()));
                    DecimalFormat decimalFormat = new DecimalFormat("#.####");
                    tvPowerDensity.setText("Power Density (mW/cm²): " + decimalFormat.format(calculations.getPowerDensity_mWcm()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvPowerDensity.setText("");
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etPowerDensityRangeMetres.addTextChangedListener(powerDensityListener);

        // Distance Miles
        TextWatcher distanceMilesListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etDistanceMiles.getText().length() > 0) {
                    calculations.calculate_DistanceMiles(
                            Double.parseDouble(etDistanceMiles.getText().toString()));
                    tvDistanceKilometres.setText("Distance Kilometres: " + decimalFormat.format(calculations.getDistanceKilometres()));
                    tvDistanceNaughticalMiles.setText("Distance Naughtical Miles (NM): " + decimalFormat.format(calculations.getDistanceNaughticalMiles_NM()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvDistanceKilometres.setText("");
                    tvDistanceNaughticalMiles.setText("");
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etDistanceMiles.addTextChangedListener(distanceMilesListener);

        // Distance Kilometres
        TextWatcher distanceKilometresListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etDistanceKilometres.getText().length() > 0) {
                    calculations.calculate_DistanceKilometres(
                            Double.parseDouble(etDistanceKilometres.getText().toString()));
                    tvDistanceMiles.setText("Distance Miles: " + decimalFormat.format(calculations.getDistanceMiles()));
                    tvDistanceNaughticalMiles2.setText("Distance Naughtical Miles (NM): " + decimalFormat.format(calculations.getDistanceNaughticalMiles_NM()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvDistanceMiles.setText("");
                    tvDistanceNaughticalMiles2.setText("");
                }
                }catch(NumberFormatException e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etDistanceKilometres.addTextChangedListener(distanceKilometresListener);

        // Distance Naughtical Miles (NM)
        TextWatcher distanceNaughticalMilesListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                if (etDistanceNaughticalMiles.getText().length() > 0) {
                    calculations.calculate_DistanceNaughticalMiles_NM(
                            Double.parseDouble(etDistanceNaughticalMiles.getText().toString()));
                    tvDistanceMiles2.setText("Distance Miles: " + decimalFormat.format(calculations.getDistanceMiles()));
                    tvDistanceKilometres2.setText("Distance Kilometres: " + decimalFormat.format(calculations.getDistanceKilometres()));
                    submitBtn.setEnabled(isAllRequiredEditTextsFilled());
                } else {
                    tvDistanceMiles2.setText("");
                    tvDistanceKilometres2.setText("");
                }
                }catch(NumberFormatException e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etDistanceNaughticalMiles.addTextChangedListener(distanceNaughticalMilesListener);
    }

    private void initViews() {
        //Init data
        etFade = (EditText) findViewById(R.id.et_fade);
        etRain = (EditText) findViewById(R.id.et_rain);
        polarizationSpinner = (Spinner) findViewById(R.id.polarization_spinner);
        groundTypeSpinner = (Spinner) findViewById(R.id.ground_type_spinner);
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
        tvFresnelRadiusObstical = (TextView) findViewById(R.id.tv_fresnel_radius_obstical);
        tvObstacleClearanceRequired = (TextView) findViewById(R.id.tv_obstacle_clearance_required);

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

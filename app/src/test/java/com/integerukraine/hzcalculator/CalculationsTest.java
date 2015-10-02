package com.integerukraine.hzcalculator;

import com.integerukraine.hzcalculator.calculations.Calculations;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class CalculationsTest {

    private Calculations calculator = new Calculations();


    @Before
    public void resetCalculator() {
        calculator.resetCalculator();
    }

    @Test
    public void test_TxGainCalculations() {
        double totalErpDelta = 0.1d;
        double erpInWattsDelta = 0.01d;

        calculator.calculate_TxGain(40d, 13d, 2.8d);
        Assert.assertEquals(50.2d, calculator.getTotalErp_dB(), totalErpDelta);
        Assert.assertEquals(104.71d, calculator.getErpInWatts(), erpInWattsDelta);
    }

    @Test
    public void test_RxGainCalculations() {
        double totalReceiveDelta = 0.1d;

        calculator.calculate_RxGain(86d, 27d, 2.3d);
        Assert.assertEquals(110.7d, calculator.getTotalReceive_dBm(), totalReceiveDelta);
        calculator.calculate_RxGain(-86d, 27d, 2.3d);
        Assert.assertEquals(110.7d, calculator.getTotalReceive_dBm(), totalReceiveDelta);
    }

    @Test
    public void test_FreeSpacePathLossCalculations() {
        double freeSpacePathLossDelta = 0.001d;

        calculator.calculate_FreeSpacePathLoss(3600d, 80d);
        Assert.assertEquals(141.655d, calculator.getFreeSpacePathLoss(), freeSpacePathLossDelta);
    }

    @Test
    public void test_FresnelAtSpecificPointCalculations() {
        double FresnelRadiusAtObsticalDelta = 0.01d;
        double obstacleClearanceRequiredDelta = 0.01d;

        calculator.calculate_FresnelAtSpecificPoint(0.005d, 80, 3600);
        Assert.assertEquals(0.65d, calculator.getFresnelRadiusAtObstical_M(), FresnelRadiusAtObsticalDelta);
        Assert.assertEquals(0.39d, calculator.getObstacleClearanceRequired_M(), obstacleClearanceRequiredDelta);
    }

    @Test
    public void test_1stFresnelRadiusAtMidpointCalculations() {
        double fresnelRadiusAtObsticalDelta = 0.01d;
        double obstacleClearanceRequiredDelta = 0.01d;

        calculator.calculate_1stFresnelRadiusAtMidpoint(80, 3600);
        Assert.assertEquals(28.86d, calculator.getFresnelRadius1st_M(), fresnelRadiusAtObsticalDelta);
        Assert.assertEquals(94.07d, calculator.getEarthHeightMidpoint_M(), obstacleClearanceRequiredDelta);
    }

    @Test
    public void test_LineOfSiteCalculations() {
        double LoSDelta = 0.01d;

        calculator.calculate_LineOfSite(250, 40);
        Assert.assertEquals(91.14d, calculator.getLoS_KM(), LoSDelta);
    }

    @Test
    public void test_dBmWattsCalculations() {
        double power_WDelta = 0.01d;
        double power_mWDelta = 1d;

        calculator.calculate_dBmWatts(60);
        Assert.assertEquals(1000.00d, calculator.getPower_W(), power_WDelta);
        Assert.assertEquals(1000000d, calculator.getPower_mW(), power_mWDelta);
    }

    @Test
    public void test_DistanceMilesCalculations() {
        double distanceKilometresDelta = 0.01d;
        double distanceNaughticalMilesDelta = 0.01d;

        calculator.calculate_DistanceMiles(100);
        Assert.assertEquals(160.93d, calculator.getDistanceKilometres(), distanceKilometresDelta);
        Assert.assertEquals(86.90d, calculator.getDistanceNaughticalMiles_NM(), distanceNaughticalMilesDelta);
    }

    @Test
    public void test_LinkBudgetCalculations() {
        double linkBudgetDelta = 0.001d;
        double systemOperatingMarginDelta = 0.01d;

        calculator.init(10, 1);
        calculator.calculate_TxGain(40, 13, 2.8);
        calculator.calculate_RxGain(86, 27, 2.3);
        calculator.calculate_FreeSpacePathLoss(3600, 80);

        calculator.calculate_LinkBudget_dB();
        Assert.assertEquals(8.245d, calculator.getLinkBudget_dB(), linkBudgetDelta);
        Assert.assertEquals(16.42d, calculator.getSystemOperatingMargin_SAD(), systemOperatingMarginDelta);
    }

    @Test
    public void test_PowerDensityCalculations() {
        double powerDensityDelta = 0.0001d;

        calculator.calculate_TxGain(40, 13, 2.8);
        calculator.calculate_PowerDensity(4.000d);
        Assert.assertEquals(0.0521d, calculator.getPowerDensity_mWcm(), powerDensityDelta);
    }

    @Test
    public void test_WavelengthAlphaCalculations() {
        double WavelengthAlphaDelta = 0.001d;
        calculator.calculate_WavelengthAlpha(3600);
        Assert.assertEquals(0.083d, calculator.getWavelengthAlpha_m(), WavelengthAlphaDelta);
    }

    @Test
    public void test_WattsdBmCalculations() {
        double powerDelta = 0.001d;
        calculator.calculate_WattsdBm(100000d);
        Assert.assertEquals(80.000d, calculator.getPower_dBm(), powerDelta);
    }

    @Test
    public void test_DistanceKilometresCalculations() {
        double distanceMilesDelta = 0.01d;
        double distanceNaughticalMilesDelta = 0.01d;
        calculator.calculate_DistanceKilometres(100.00d);
        Assert.assertEquals(62.14d, calculator.getDistanceMiles(), distanceMilesDelta);
        Assert.assertEquals(54.00d, calculator.getDistanceNaughticalMiles_NM(), distanceNaughticalMilesDelta);
    }

    @Test
    public void test_ParabolicAntennaGainCalculations() {
        double theoretical3dBBeamwidthDelta = 0.01d;

        calculator.calculate_WavelengthAlpha(3600);
        calculator.calculate_ParabolicAntennaGain(3);
        Assert.assertEquals(1.94d, calculator.getTheoretical3dBBeamwidth(), theoretical3dBBeamwidthDelta);
    }

    @Test
    public void test_mWattsdBmCalculations() {
        double power_dBmDelta = 0.001d;

        calculator.calculate_mWattsdBm(200d);
        Assert.assertEquals(23.010d, calculator.getPower_dBm(), power_dBmDelta);
    }

    @Test
    public void test_DistanceNaughticalMiles_NMCalculations() {
        double distanceMilesDelta = 0.01d;
        double distanceKilometresDelta = 0.01d;

        calculator.calculate_DistanceNaughticalMiles_NM(100);
        Assert.assertEquals(115.08d, calculator.getDistanceMiles(), distanceMilesDelta);
        Assert.assertEquals(185.20d, calculator.getDistanceKilometres(), distanceKilometresDelta);
    }

    @Test
    public void test_SafeWorkingDistanceCalculations() {
        double controlledEnvironmentDelta = 0.01d;
        double uncontrolledEnvironmentDelta = 0.01d;
        double reflectionsControlledEnvironmentDelta = 0.01d;
        double reflectionsUncontrolledEnvironmentDelta = 0.01d;

        calculator.calculate_TxGain(40d, 13d, 2.8d);
        calculator.calculate_SafeWorkingDistance(3600d);
        Assert.assertEquals(1.15d, calculator.getReflectionsControlledEnvironment_m(), reflectionsControlledEnvironmentDelta);
        Assert.assertEquals(2.58d, calculator.getReflectionsUncontrolledEnvironment_m(), reflectionsUncontrolledEnvironmentDelta);
        Assert.assertEquals(0.41d, calculator.getControlledEnvironment_m(), controlledEnvironmentDelta);
        Assert.assertEquals(0.91d, calculator.getUncontrolledEnvironment_m(), uncontrolledEnvironmentDelta);
    }

    @Test
    public void test_resetCalculator() {
        calculator.resetCalculator();
        Assert.assertEquals(0.0d, calculator.getTotalErp_dB());
        Assert.assertEquals(0.0d, calculator.getErpInWatts());
        Assert.assertEquals(0.0d, calculator.getTotalReceive_dBm());
        Assert.assertEquals(0.0d, calculator.getFreeSpacePathLoss());
        Assert.assertEquals(0.0d, calculator.getFresnelRadiusAtObstical_M());
        Assert.assertEquals(0.0d, calculator.getObstacleClearanceRequired_M());
        Assert.assertEquals(0.0d, calculator.getFresnelRadius1st_M());
        Assert.assertEquals(0.0d, calculator.getEarthHeightMidpoint_M());
        Assert.assertEquals(0.0d, calculator.getLoS_KM());
        Assert.assertEquals(0.0d, calculator.getPower_W());
        Assert.assertEquals(0.0d, calculator.getPower_mW());
        Assert.assertEquals(0.0d, calculator.getDistanceKilometres());
        Assert.assertEquals(0.0d, calculator.getDistanceNaughticalMiles_NM());
        Assert.assertEquals(0.0d, calculator.getFadeAndEnvironmentalMargin_dB());
        Assert.assertEquals(0.0d, calculator.getRainAttenuation_dB());
        Assert.assertEquals(0.0d, calculator.getLinkBudget_dB());
        Assert.assertEquals(0.0d, calculator.getSystemOperatingMargin_SAD());
        Assert.assertEquals(0.0d, calculator.getPowerDensity_mWcm());
        Assert.assertEquals(0.0d, calculator.getWavelengthAlpha_m());
        Assert.assertEquals(0.0d, calculator.getPower_dBm());
        Assert.assertEquals(0.0d, calculator.getDistanceMiles());
        Assert.assertEquals(0.0d, calculator.getTheoretical3dBBeamwidth());
        Assert.assertEquals(0.0d, calculator.getControlledEnvironment_m());
        Assert.assertEquals(0.0d, calculator.getUncontrolledEnvironment_m());
        Assert.assertEquals(0.0d, calculator.getReflectionsControlledEnvironment_m());
        Assert.assertEquals(0.0d, calculator.getReflectionsUncontrolledEnvironment_m());

    }

    @Test
    public void test_initCalculator() {
        double fadeAndEnvironmentalMarginDelta = 1d;
        double rainAttenuationDelta = 1d;

        calculator.init(10, 1);
        Assert.assertEquals(10d, calculator.getFadeAndEnvironmentalMargin_dB(), fadeAndEnvironmentalMarginDelta);
        Assert.assertEquals(1d, calculator.getRainAttenuation_dB(), rainAttenuationDelta);
    }
}
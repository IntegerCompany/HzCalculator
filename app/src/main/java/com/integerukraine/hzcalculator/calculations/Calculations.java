package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public class Calculations {

    private static final double SPEED_OF_LIGHT = 299792458d;

    private double totalErp_dB;
    private double erpInWatts;
    private double totalReceive_dBm;
    private double freeSpacePathLoss;
    private double fresnelRadiusAtObstical_M;
    private double obstacleClearanceRequired_M;
    private double fresnelRadius1st_M;
    private double earthHeightMidpoint_M;
    private double LoS_KM;
    private double power_W;
    private double power_mW;
    private double distanceKilometres;
    private double distanceNaughticalMiles_NM;
    private double fadeAndEnvironmentalMargin_dB;
    private double rainAttenuation_dB;
    private double linkBudget_dB;
    private double systemOperatingMargin_SAD;
    private double powerDensity_mWcm;
    private double wavelengthAlpha_m;
    private double power_dBm;
    private double distanceMiles;
    private double theoretical3dBBeamwidth;
    private double controlledEnvironment_m;
    private double uncontrolledEnvironment_m;
    private double reflectionsControlledEnvironment_m;
    private double reflectionsUncontrolledEnvironment_m;



    public void calculate_TxGain(double txOutput_dBm, double txAntennaGain_dBi, double cableLosses_dB) {
        totalErp_dB = txOutput_dBm + txAntennaGain_dBi - cableLosses_dB;
        erpInWatts = Math.pow(10d, (totalErp_dB - 30d) / 10d);
    }

    public void calculate_RxGain(double rxSensitivity_dBm, double rxAntennaGain_dBi, double cableLosses_dB) {
        rxSensitivity_dBm = Math.abs(rxSensitivity_dBm);
        totalReceive_dBm = rxSensitivity_dBm + rxAntennaGain_dBi - cableLosses_dB;
    }

    public void calculate_FreeSpacePathLoss(double frequency_MHz, double distance_KM) {
        freeSpacePathLoss = 36.6d + 20d * Math.log10(frequency_MHz * distance_KM / 1.6093d);
    }

    public void calculate_FresnelAtSpecificPoint(double distanceToObstical_KM, double distance_KM, double frequency_MHz) {
        fresnelRadiusAtObstical_M = Math.sqrt(((SPEED_OF_LIGHT / (frequency_MHz * 1000000d)) * (distanceToObstical_KM * 1000d) * ((distance_KM - distanceToObstical_KM) * 1000d)) / (distance_KM * 1000d));
        obstacleClearanceRequired_M = fresnelRadiusAtObstical_M * 0.6d;
    }

    public void calculate_1stFresnelRadiusAtMidpoint(double distance_KM, double frequency_MHz) {
        double linkDistance_KM = distance_KM / 2d;
        double Freq_GHz = frequency_MHz / 1000d;

        fresnelRadius1st_M = 8.657d * Math.sqrt(linkDistance_KM / Freq_GHz);
        earthHeightMidpoint_M = (((distance_KM * 1000d) * (distance_KM * 1000d)) / 68033.4613333d) / 1000d;
    }

    public void calculate_LineOfSite(double antenna1Height_M, double antenna2Height_M) {
        LoS_KM = (3.57d * Math.sqrt(1.33d * antenna1Height_M)) + (3.57 * Math.sqrt(1.33 * antenna2Height_M));
    }

    public void calculate_dBmWatts(double power_dBm) {
        power_W = Math.pow(10d, (power_dBm - 30d) / 10d);
        power_mW = power_W * 1000d;
    }

    public void calculate_DistanceMiles(double distanceMiles) {
        distanceKilometres = distanceMiles * 1.6093d;
        distanceNaughticalMiles_NM = distanceKilometres / 1.852d;
    }

    public void calculate_LinkBudget_dB() {
        if (totalErp_dB == 0 | totalReceive_dBm == 0 | freeSpacePathLoss == 0)
            throw new IllegalArgumentException("Some of calculations is 0. Maybe you forget to calculate Tx Gain/Rx Gain/Free Space Path Loss");

        linkBudget_dB = (totalErp_dB + totalReceive_dBm) - (freeSpacePathLoss + fadeAndEnvironmentalMargin_dB + rainAttenuation_dB);
        systemOperatingMargin_SAD = (linkBudget_dB / totalErp_dB) * 100d;
    }

    /**
     * Required TX gain calculation before     *
     */
    public void calculate_PowerDensity(double powerDensityRange_M) {
        if (erpInWatts == 0)
            throw new IllegalArgumentException("ERP in watts = 0. Maybe you forget to calculate Tx Gain?");

        powerDensity_mWcm = (erpInWatts * 1000d) / ((Math.PI * 4d) * ((powerDensityRange_M * 100d) * (powerDensityRange_M * 100d)));
    }

    public void calculate_WavelengthAlpha(double frequency_MHz) {
        wavelengthAlpha_m = SPEED_OF_LIGHT / (frequency_MHz * 1000000d);
    }

    public void calculate_WattsdBm(double power_Watts) {
        power_dBm = 10d * Math.log10(power_Watts) + 30d;
    }

    public void calculate_DistanceKilometres(double distanceKilometres) {
        distanceMiles = distanceKilometres / 1.6093;
        distanceNaughticalMiles_NM = distanceKilometres / 1.852;
    }

    /**
     * Required wavelengthAlpha calculation
     */
    public void calculate_ParabolicAntennaGain(double diameter_m) {
        if (wavelengthAlpha_m == 0)
            throw new IllegalArgumentException("wavelengthAlpha_m = 0. Maybe you forget to calculate wavelength alpha?");

        theoretical3dBBeamwidth = (70d * wavelengthAlpha_m) / diameter_m;
    }

    public void calculate_mWattsdBm(double power_mWatts) {
        power_dBm = (10 * Math.log10(power_mWatts / 1000) + 30);
    }

    public void calculate_DistanceNaughticalMiles_NM(double distanceNaughticalMiles_NM) {
        distanceMiles = distanceNaughticalMiles_NM * 1.15078d;
        distanceKilometres = distanceNaughticalMiles_NM * 1.852d;
    }

    public void calculate_SafeWorkingDistance(double frequency_MHz) {
        if (erpInWatts == 0)
            throw new IllegalArgumentException("ERP in watts = 0. Maybe you forget to calculate Tx Gain?");
        //Big and ugly formulas to...
        //calculate info for controlledEnvironment_m with reflections
        reflectionsControlledEnvironment_m = frequency_MHz <= 30 ? (frequency_MHz <= 3 ? (Math.pow(48 * erpInWatts, 0.5) / 614) : ((Math.pow(48 * erpInWatts, 0.5) / (1842 / frequency_MHz)))) : ((Math.pow(48d * erpInWatts, 0.5d)) / 61.4d);
        //calculate info for uncontrolledEnvironment_m with reflections
        reflectionsUncontrolledEnvironment_m = frequency_MHz <= 30 ? (frequency_MHz <= 3 ? (Math.pow(48 * erpInWatts, 0.5) / 614) : (Math.pow(48 * erpInWatts, 0.5) / (842 / frequency_MHz))) : (Math.pow(48 * erpInWatts, 0.5) / 27.5);
        //calculate info for controlledEnvironment_m without reflections
        controlledEnvironment_m = frequency_MHz <= 1500 ? (frequency_MHz <= 3 ? (Math.pow(30 * erpInWatts, 0.5) / 614) : (frequency_MHz <= 30 ? ((Math.pow(30 * erpInWatts, 0.5)) / (1842 / frequency_MHz)) : (frequency_MHz <= 300 ? (Math.pow(30 * erpInWatts, 0.5) / 61.4) : (Math.sqrt((erpInWatts * 1000 / frequency_MHz / 300) / (Math.PI * 4)) / 100)))) : (Math.sqrt(((erpInWatts * 1000) / 5) / (Math.PI * 4)) / 100);
        //calculate info for uncontrolledEnvironment_m without reflections
        uncontrolledEnvironment_m = frequency_MHz <= 1500 ? (frequency_MHz <= 3 ? (Math.pow(30 * erpInWatts, 0.5) / 614) : (frequency_MHz <= 30 ? ((Math.pow(30 * erpInWatts, 0.5)) / (824 / frequency_MHz)) : (frequency_MHz <= 300 ? (Math.pow(30 * erpInWatts, 0.5) / 27.5) : (Math.sqrt(((erpInWatts * 1000) / (frequency_MHz / 1500)) / (Math.PI * 4)) / 100)))) : (Math.sqrt((erpInWatts * 1000) / (Math.PI * 4)) / 100);
    }

    //=========================================================================================================
    // Utility methods
    public void init(double fadeAndEnvironmentalMargin_dB, double rainAttenuation_dB) {
        this.fadeAndEnvironmentalMargin_dB = fadeAndEnvironmentalMargin_dB;
        this.rainAttenuation_dB = rainAttenuation_dB;
    }

    public void resetCalculator() {
        totalErp_dB = 0;
        erpInWatts = 0;
        totalReceive_dBm = 0;
        freeSpacePathLoss = 0;
        fresnelRadiusAtObstical_M = 0;
        obstacleClearanceRequired_M = 0;
        fresnelRadius1st_M = 0;
        earthHeightMidpoint_M = 0;
        LoS_KM = 0;
        power_W = 0;
        power_mW = 0;
        distanceKilometres = 0;
        distanceNaughticalMiles_NM = 0;
        fadeAndEnvironmentalMargin_dB = 0;
        rainAttenuation_dB = 0;
        linkBudget_dB = 0;
        systemOperatingMargin_SAD = 0;
        powerDensity_mWcm = 0;
        wavelengthAlpha_m = 0;
        power_dBm = 0;
        distanceMiles = 0;
        theoretical3dBBeamwidth = 0;
        controlledEnvironment_m = 0;
        uncontrolledEnvironment_m = 0;
        reflectionsControlledEnvironment_m = 0;
        reflectionsUncontrolledEnvironment_m = 0;

    }

    //=========================================================================================================
    // Getters
    public double getTotalErp_dB() {
        return totalErp_dB;
    }

    public double getErpInWatts() {
        return erpInWatts;
    }

    public double getTotalReceive_dBm() {
        return totalReceive_dBm;
    }

    public double getFreeSpacePathLoss() {
        return freeSpacePathLoss;
    }

    public double getFresnelRadiusAtObstical_M() {
        return fresnelRadiusAtObstical_M;
    }

    public double getObstacleClearanceRequired_M() {
        return obstacleClearanceRequired_M;
    }

    public double getFresnelRadius1st_M() {
        return fresnelRadius1st_M;
    }

    public double getEarthHeightMidpoint_M() {
        return earthHeightMidpoint_M;
    }

    public double getLoS_KM() {
        return LoS_KM;
    }

    public double getPower_W() {
        return power_W;
    }

    public double getPower_mW() {
        return power_mW;
    }

    public double getDistanceKilometres() {
        return distanceKilometres;
    }

    public double getDistanceNaughticalMiles_NM() {
        return distanceNaughticalMiles_NM;
    }

    public double getLinkBudget_dB() {
        return linkBudget_dB;
    }

    public double getSystemOperatingMargin_SAD() {
        return systemOperatingMargin_SAD;
    }

    public double getPowerDensity_mWcm() {
        return powerDensity_mWcm;
    }

    public double getFadeAndEnvironmentalMargin_dB() {
        return fadeAndEnvironmentalMargin_dB;
    }

    public double getRainAttenuation_dB() {
        return rainAttenuation_dB;
    }

    public double getWavelengthAlpha_m() {
        return wavelengthAlpha_m;
    }

    public double getPower_dBm() {
        return power_dBm;
    }

    public double getDistanceMiles() {
        return distanceMiles;
    }

    public double getTheoretical3dBBeamwidth() {
        return theoretical3dBBeamwidth;
    }

    public double getControlledEnvironment_m() {
        return controlledEnvironment_m;
    }

    public double getUncontrolledEnvironment_m() {
        return uncontrolledEnvironment_m;
    }

    public double getReflectionsControlledEnvironment_m() {
        return reflectionsControlledEnvironment_m;
    }

    public double getReflectionsUncontrolledEnvironment_m() {
        return reflectionsUncontrolledEnvironment_m;
    }
}

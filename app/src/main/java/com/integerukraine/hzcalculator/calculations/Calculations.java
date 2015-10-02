package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public class Calculations {

    private double totalErp_dB;
    private double erpInWatts;
    private double totalReceive_dBm;
    private double freeSpacePathLoss;
    private double fresnelRadiusAtObstical_M;
    private double obstacleClearanceRequired_M;


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
        fresnelRadiusAtObstical_M = Math.sqrt(((299792458d / (frequency_MHz * 1000000d)) * (distanceToObstical_KM * 1000d) * ((distance_KM - distanceToObstical_KM) * 1000d)) / (distance_KM * 1000d));
        obstacleClearanceRequired_M = fresnelRadiusAtObstical_M * 0.6d;
    }


    //=========================================================================================================
    // Utility methods
    public void resetCalculator() {
        totalErp_dB = 0;
        erpInWatts = 0;
        totalReceive_dBm = 0;
        freeSpacePathLoss = 0;
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
}

package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public class Calculations {

    private double totalErp_dB;
    private double erpInWatts;
    private double totalReceive_dBm;
    private double freeSpacePathLoss;




    public void calculateTxGain(double txOutput_dBm, double txAntennaGain_dBi, double cableLosses_dB) {
        totalErp_dB = txOutput_dBm + txAntennaGain_dBi - cableLosses_dB;
        erpInWatts = Math.pow(10d, (totalErp_dB - 30d) / 10d);
    }

    public void calculateRxGain(double rxSensitivity_dBm, double rxAntennaGain_dBi, double cableLosses_dB) {
        rxSensitivity_dBm = Math.abs(rxSensitivity_dBm);
        totalReceive_dBm = rxSensitivity_dBm + rxAntennaGain_dBi - cableLosses_dB;
    }

    public void calculateFreeSpacePathLoss(double frequency_MHz, double distance_KM) {
        freeSpacePathLoss = 36.6d + 20d * Math.log10(frequency_MHz * distance_KM / 1.6093d);
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
}

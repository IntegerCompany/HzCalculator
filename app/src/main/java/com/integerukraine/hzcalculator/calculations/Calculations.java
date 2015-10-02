package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public class Calculations {

    private double totalErp_dB;
    private double erpInWatts;


    public void resetCalculator() {
        totalErp_dB = 0;
        erpInWatts = 0;
    }

    public void calculateTxGain(double txOutput_dBm, double txAntennaGain_dBi, double cableLosses_dB) {
        totalErp_dB = txOutput_dBm + txAntennaGain_dBi - cableLosses_dB;
        erpInWatts = Math.pow(10, (totalErp_dB - 30) / 10);
    }


    //=========================================================================================================
    // Getters
    public double getTotalErp_dB() {
        return totalErp_dB;
    }

    public double getErpInWatts() {
        return erpInWatts;
    }
}

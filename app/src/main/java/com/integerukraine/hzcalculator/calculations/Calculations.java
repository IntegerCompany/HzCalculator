package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public class Calculations {

    private double totalErp_dB;
    private double erpInWatts;
    private double totalReceive_dBm;




    public void calculateTxGain(double txOutput_dBm, double txAntennaGain_dBi, double cableLosses_dB) {
        totalErp_dB = txOutput_dBm + txAntennaGain_dBi - cableLosses_dB;
        erpInWatts = Math.pow(10, (totalErp_dB - 30) / 10);
    }

    public void calculateRxGain(double rxSensitivity_dBm, double rxAntennaGain_dBi, double cableLosses_dB) {
        rxSensitivity_dBm = Math.abs(rxSensitivity_dBm);
        totalReceive_dBm = rxSensitivity_dBm + rxAntennaGain_dBi - cableLosses_dB;
    }


    //=========================================================================================================
    // Utility methods
    public void resetCalculator() {
        totalErp_dB = 0;
        erpInWatts = 0;
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
}

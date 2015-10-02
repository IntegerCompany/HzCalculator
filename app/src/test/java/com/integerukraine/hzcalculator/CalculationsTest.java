package com.integerukraine.hzcalculator;

import com.integerukraine.hzcalculator.calculations.Calculations;

import junit.framework.Assert;

import org.junit.Test;

public class CalculationsTest {

    private Calculations calculator = new Calculations();


    @Test
    public void testTxGainCalculations() {
        double totalErpDelta = 0.1d;
        double erpInWattsDelta = 0.01d;

        calculator.calculateTxGain(40d, 13d, 2.8d);
        Assert.assertEquals(50.2d, calculator.getTotalErp_dB(), totalErpDelta);
        Assert.assertEquals(104.71d, calculator.getErpInWatts(), erpInWattsDelta);
    }

    @Test
    public void testRxGainCalculations() {
        double totalReceiveDelta = 0.1d;

        calculator.calculateRxGain(86d, 27d, 2.3d);
        Assert.assertEquals(110.7d, calculator.getTotalReceive_dBm(), totalReceiveDelta);
        calculator.calculateRxGain(-86d, 27d, 2.3d);
        Assert.assertEquals(110.7d, calculator.getTotalReceive_dBm(), totalReceiveDelta);
    }

    @Test
    public void testFreeSpacePathLossCalculations() {
        double freeSpacePathLossDelta = 0.001d;

        calculator.calculateFreeSpacePathLoss(3600d, 80d);
        Assert.assertEquals(141.655d, calculator.getFreeSpacePathLoss(), freeSpacePathLossDelta);
    }

}
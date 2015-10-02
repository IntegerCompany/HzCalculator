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

}
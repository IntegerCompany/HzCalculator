package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/2/2015.
 */
public enum GroundType {
    AVERAGE(15, 0.005),
    DRY(5.5, 0.001),
    SEA(81, 5),
    WATER(81, 0.01),
    WET(27.5, 0.002),
    FREE_SPACE(1, 0);

    private final double dielectric;
    private final double conductivity;

    GroundType(final double dielectric, final double conductivity) {
        this.dielectric = dielectric;
        this.conductivity = conductivity;
    }

    public double getDielectric() {
        return dielectric;
    }

    public double getConductivity() {
        return conductivity;
    }
}

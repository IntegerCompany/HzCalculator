package com.integerukraine.hzcalculator.calculations;

/**
 * Created by Andriy on 10/20/2015.
 */
public class ChartCalculator {
    public static final int VERTICAL = 1, HORIZONTAL = 2;
    public static final int dBm = 1, REFLECTION = 2;
    public static final int DRY = 1, AVERAGE = 2, SEA = 3, WATER = 4, WET = 5, FREE_SPACE = 6;
    private final double pi = 3.14159265358979;

    public double calculateCurvedEarth(double fMHz, double h1, double h2, double km, int currentPolarisation, int groundType, int currentOutput) {
        //------
        double hh1;
        double hh2;
        double dd2;
        double earthRadius;
        double theta;
        double oneReal;
        double oneImag;
        double reflectionReal;
        double reflectionImag;
        double er;
        double xx;
        double denom;
        double lineOfSight;
        double dReflected;
        double deltaD;
        double lambda;
        double phiLOS;
        double phiRefl;
        double pathDirectReal;
        double pathDirectImag;
        double pathReflReal;
        double pathReflImag;
        double pathReal;
        double pathImag;
        double curvedEarth;
        //--------

        //'4/3 actual value
        earthRadius = 6378140 * 4 / 3;
        // 'Modify h's
        hh1 = (h1 + earthRadius) * Math.cos(km * 1000 * h1 / (h1 + h2) / earthRadius) - earthRadius;
        hh2 = (h2 + earthRadius) * Math.cos(km * 1000 * h2 / (h1 + h2) / earthRadius) - earthRadius;

        // 'Modify distance and get into metres
        dd2 = ((h1 + earthRadius) * Math.sin(km * 1000 * h1 / (h1 + h2) / earthRadius) + (h2 + earthRadius) * Math.sin(km * 1000 * h2 / (h1 + h2) / earthRadius));
        // 'Angle of relection with Earth
        theta = Math.atan((hh1 + hh2) / dd2);
        er = findDielectric(groundType);
        xx = findRelectionImag(groundType, fMHz);
        if (currentPolarisation == VERTICAL) {
            oneReal = er * Math.sin(theta);
            oneImag = xx * Math.sin(theta);
        } else {
            oneReal = Math.sin(theta);
            oneImag = 0;
        }

        double twoReal = ComplexSqrtReal(er - Math.pow(Math.cos(theta), 2), -xx);
        double twoImag = ComplexSqrtImag(er - Math.pow(Math.cos(theta), 2), -xx);
        // '(one-two)/(one+two)
        denom = (Math.pow(oneImag, 2) + Math.pow(oneReal, 2) + 2 * oneImag * twoImag + Math.pow(twoImag, 2) + 2 * oneReal * twoReal + Math.pow(twoReal, 2));
        reflectionReal = (Math.pow(oneReal, 2) + Math.pow(oneImag, 2) - Math.pow(twoImag, 2) - Math.pow(twoReal, 2)) / denom;
        reflectionImag = (-2 * oneReal * twoImag - 2 * oneImag * twoReal) / denom;
        if (currentOutput == dBm) {
            // 'Line of sight distance
            lineOfSight = Math.sqrt(Math.pow(dd2, 2) + Math.pow((hh1 - hh2), 2));
            // 'Reflection distance
            dReflected = Math.sqrt(Math.pow(dd2, 2) + Math.pow((hh1 + hh2), 2));
            deltaD = lineOfSight - dReflected;
            // 'Wavelength in m
            lambda = 300 / fMHz;
            phiLOS = 2 * pi * lineOfSight / lambda;
            phiRefl = 2 * pi * dReflected / lambda;
            pathDirectReal = lambda * Math.cos(phiLOS) / (4 * pi * lineOfSight);
            pathDirectImag = lambda * Math.sin(phiLOS) / (4 * pi * lineOfSight);
            pathReflReal = lambda * (reflectionReal * Math.cos(phiRefl) - reflectionImag * Math.sin(phiRefl)) / (4 * pi * dReflected);
            pathReflImag = lambda * (reflectionReal * Math.sin(phiRefl) + reflectionImag * Math.cos(phiRefl)) / (4 * pi * dReflected);
            curvedEarth = 10 * Math.log10(Math.pow((pathDirectReal + pathReflReal), 2) + Math.pow((pathDirectImag + pathReflImag), 2)) / Math.log10(10);
        } else {
            curvedEarth = reflectionReal;
        }

        return curvedEarth;
    }

    public double findDielectric(int groundType) {
        switch (groundType) {
            case DRY:
                return 5.5;
            case WET:
                return 27.5;
            case SEA:
                return 81;
            case WATER:
                return 81;
            case AVERAGE:
                return 15;
            case FREE_SPACE:
                return 1;
            default:
                return -1;
        }


    }

    //'Finds imaginary part of relection coefficient
    public double findRelectionImag(int groundType, double fMHz) {
        double temp;
        switch (groundType) {
            case AVERAGE:
                temp = 0.005d;
                break;
            case DRY:
                temp = 0.001d;
                break;
            case SEA:
                temp = 5d;
                break;
            case WATER:
                temp = 0.01d;
                break;
            case WET:
                temp = 0.002d;
                break;
            case FREE_SPACE:
                temp = 0;
                break;
            default:
                temp = -1;
        }
        temp = temp * 18000 / fMHz;
        return temp;

    }

    public double ComplexSqrtReal(double x, double y) {
        double temp1 = Math.pow(x, 2) + Math.pow(y, 2);
        double temp3 = Math.sqrt(Math.sqrt(temp1));
        double temp2 = Math.cos(Math.atan2(y, x) / 2);
        double temp = temp3 * temp2;

        return temp;
    }

    public double ComplexSqrtImag(double x, double y) {
        double temp = Math.sqrt(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) * Math.sin(Math.atan2(y, x) / 2);
        return temp;
    }

}

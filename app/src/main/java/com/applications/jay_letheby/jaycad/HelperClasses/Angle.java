package com.applications.jay_letheby.jaycad.HelperClasses;

import java.math.BigDecimal;

/**
 * Created by jay_the_superwarrior on 22/01/2017.
 * Class to represent Angle and store information such as degrees, minutes and seconds
 */

public class Angle {

    private int degrees = 0;
    private int minutes = 0;
    private int seconds = 0;
    private double decimalSeconds = 0.0;
    private double decimalAngle = 0.0;

    public Angle () {

    }

    public int getDegrees() {
        return degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public double getDecimalSeconds() {
        return decimalSeconds;
    }

    public double getDecimalAngle() {
        return decimalAngle;

    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
        convertDegMinSecToDecimal();
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        convertDegMinSecToDecimal();
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        convertDegMinSecToDecimal();
    }

    public void setDecimalSeconds(double decimalSeconds) {
        this.decimalSeconds = decimalSeconds;
    }

    public void setDecimalAngle(double decimalAngle) {
        this.decimalAngle = decimalAngle;
        convertDecimalToDegMinSec();
    }

    public void setAllValues(String deg, String min, String sec) {

        // We should have checked for number format errors prior
        // to calling this method but will place in try block for safety
        try {
            if (deg.length() > 0)
                setDegrees(Integer.parseInt(deg));
            else
                setDegrees(0);

            if (min.length() > 0)
                setMinutes(Integer.parseInt(min));
            else
                setMinutes(0);

            if(sec.length() > 0) {
                setSeconds(Integer.parseInt(sec));
                //setDecimalSeconds(Double.parseDouble(sec));
            } else
                setSeconds(0);

            convertDegMinSecToDecimal();
        }
        catch (NumberFormatException e) {
            // TODO : error message here
            return;
        }
    }

    public String addAngle(Angle otherAngle) {
        // Add this objects angle data to the one passed in as a parameter

        this.decimalAngle = this.decimalAngle + otherAngle.getDecimalAngle();
        convertDecimalToDegMinSec();

        return this.degrees + "° " + Math.abs(this.minutes) + "' " + Math.abs(this.seconds) + "\"";
    }

    public String subtractAngle(Angle otherAngle) {
        // Subtract this objects angle data to the one passed in as a parameter

        this.decimalAngle = this.decimalAngle - otherAngle.getDecimalAngle();
        convertDecimalToDegMinSec();

        return this.degrees + "° " + Math.abs(this.minutes) + "' " + Math.abs(this.seconds) + "\"";
    }

    private void convertDegMinSecToDecimal (){
        // Converts Deg Min Second to decimal degrees

        decimalAngle = this.degrees + ((double)this.minutes/60) + ((double)this.seconds/3600);

    }

    private void convertDecimalToDegMinSec() {

            // Get the whole degrees value from the decimal value
            this.degrees = (int)this.decimalAngle;


            // Get the whole minutes value from the decimal value
            double calcMinutesValue = ((this.decimalAngle - (double)this.degrees) * 60 );
            this.minutes = (int)calcMinutesValue;

            // Calculate seconds
            double theSeconds = ((calcMinutesValue - (double)minutes) * 60);
            BigDecimal roundSeconds = new BigDecimal(theSeconds).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.decimalSeconds = roundSeconds.doubleValue();
            // round to nearest whole number
            this.seconds = (int) Math.round(decimalSeconds);
    }


}

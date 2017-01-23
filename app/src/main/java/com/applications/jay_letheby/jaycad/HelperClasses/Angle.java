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

    public Angle (int deg, int min, int sec) {

        this.degrees = deg;
        this.minutes = min;
        this.seconds = sec;

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
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
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

            if(sec.length() > 0)
               setSeconds(Integer.parseInt(sec));
            else
                setSeconds(0);
        }
        catch (NumberFormatException e) {
            return;
        }
    }

    public String addAngle(Angle otherAngle) {
        // Add this objects angle data to the one passed in as a parameter
        int degreesResult = 0;
        int minutesResult = 0;
        int secondsResult = 0;

        secondsResult = seconds + otherAngle.getSeconds();
        if (secondsResult > 60) {

            minutesResult = secondsResult / 60;
            secondsResult = secondsResult % 60;

        }

        minutesResult = minutesResult + minutes + otherAngle.getMinutes();

        if (minutesResult > 60) {

            degreesResult = minutesResult / 60;
            minutesResult = minutesResult % 60;

        }

        degreesResult = degreesResult + degrees + otherAngle.getDegrees();

        return degreesResult + "° " + minutesResult + "' " + secondsResult + "\"";
    }

    public String subtractAngle(Angle otherAngle) {
        // Subtract this objects angle data to the one passed in as a parameter
        int degreesResult = 0;
        int minutesResult = 0;
        int secondsResult = 0;


        if (seconds - otherAngle.getSeconds() < 0) {
            secondsResult = (seconds + 60) - otherAngle.getSeconds();
            minutesResult = -1;
        }
        else
            secondsResult = seconds - otherAngle.getSeconds();


        if ((minutes - otherAngle.getMinutes() + minutesResult) < 0) {
            minutesResult = (minutes + 60) - otherAngle.getMinutes() + minutesResult;
            degreesResult = -1;
        }
        else
            minutesResult =  minutes - otherAngle.getMinutes() + minutesResult;

        degreesResult =  degrees - otherAngle.getDegrees() + degreesResult;

        return degreesResult + "° " + minutesResult + "' " + secondsResult + "\"";
    }

    public void convertDegMinSecToDecimal (){
        // Converts Deg Min Second to decimal degrees

        decimalAngle = this.degrees + ((double)this.minutes/60) + ((double)this.seconds/3600);

    }

    public void convertDecimalToDegMinSec() {

            // Get the whole degrees value from the decimal value
            this.degrees = (int)this.decimalAngle;


            // Get the whole minutes value from the decimal value
            double calcMinutesValue = ((this.decimalAngle - (double)this.degrees) * 60 );
            int minutes = (int)calcMinutesValue;

            // Calculate seconds
            double seconds = ((calcMinutesValue - (double)minutes)) * 60;
            BigDecimal roundSeconds = new BigDecimal(seconds).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.decimalSeconds = roundSeconds.doubleValue();

    }


}

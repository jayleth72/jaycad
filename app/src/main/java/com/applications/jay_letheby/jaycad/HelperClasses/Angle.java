package com.applications.jay_letheby.jaycad.HelperClasses;

/**
 * Created by jay_the_superwarrior on 22/01/2017.
 * Class to represent Angle and store information such as degrees, minutes and seconds
 */

public class Angle {

    private int degrees = 0;
    private int minutes = 0;
    private int seconds = 0;

    public Angle (int deg, int min, int sec) {

        degrees = deg;
        minutes = min;
        seconds = sec;
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


}

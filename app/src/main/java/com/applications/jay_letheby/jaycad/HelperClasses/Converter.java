package com.applications.jay_letheby.jaycad.HelperClasses;

import com.applications.jay_letheby.jaycad.HelperClasses.DecimalUtils;
/**
 * Created by jay_the_superwarrior on 25/01/2017.
 * This class is used to convert both area and length measurements
 */

public class Converter {

    public enum LengthConversionOperation {
        FEET_TO_METRES, METRES_TO_FEET, LINKS_TO_METRES, METRES_TO_LINKS
    }

    public enum AreaConversionOperation {
        HECTARES_TO_ACRES, ACRES_TO_HECTARES
    }

    public  Converter () {}

    public String lengthConverter (LengthConversionOperation operation, Double measurementToConvert) {
        // Length conversions
        Double convertedMeasurement = 0.0;
        String formattedResult = "";

        switch (operation) {
            case FEET_TO_METRES:
                convertedMeasurement = convertToDecimalFeet(measurementToConvert);
                convertedMeasurement = convertedMeasurement * 0.3048;

                break;
            case METRES_TO_FEET:
                convertedMeasurement = measurementToConvert * 3.28084;

                break;
            case LINKS_TO_METRES:
                convertedMeasurement = measurementToConvert * 0.201168;

                break;
            case METRES_TO_LINKS:
                convertedMeasurement = measurementToConvert * 4.9709595959;

                break;

        }
        // return result to 3 decimal places
        convertedMeasurement = DecimalUtils.round(convertedMeasurement, 3);
        formattedResult = convertedMeasurement.toString();
        return formattedResult;
    }

    public Double areaConverter (AreaConversionOperation operation, Double measurementToConvert){
        // Area conversions
        Double convertedMeasurement = 0.0;

        switch (operation) {
            case HECTARES_TO_ACRES:
                break;
            case ACRES_TO_HECTARES:
                break;
        }
        return convertedMeasurement;
    }

    public Double convertDecimalFeetToFeet (Double measurement) {
        // Convert decimal feet to feet.  So 5.9166
        // will be converted to 5.11 or 5ft and 11 inches
        double fractionalPart = measurement % 1;
        double integralPart = measurement - fractionalPart;

        return integralPart + (fractionalPart / 12);
    }

    private double convertToDecimalFeet(Double measurement){
        // If entering a measurement of 5 ft and 7inches
        // a user will enter 5.7.  This will need tp be converted
        // to decimal feet.  There are 12 inches to a foot.
        // Also account for users entering up to 16ths of an inch
        // So 5.711 is interpreted as 5ft 7" 11/16 on an inch
        double fractionalPart = measurement % 1;
        double integralPart = measurement - fractionalPart;

        return integralPart + ( fractionalPart * (1/12) );
    }

}

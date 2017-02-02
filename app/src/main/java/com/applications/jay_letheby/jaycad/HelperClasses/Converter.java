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
                convertedMeasurement = measurementToConvert * 0.3048;
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
        //convertedMeasurement = DecimalUtils.round(convertedMeasurement, 3);
        formattedResult = convertedMeasurement.toString();
        return formattedResult;
    }


    public String areaConverter (AreaConversionOperation operation, Double measurementToConvert){
        // Area conversions
        Double convertedMeasurement = 0.0;

        switch (operation) {
            case HECTARES_TO_ACRES:
                convertedMeasurement = measurementToConvert * 2.4710538146717;
                break;
            case ACRES_TO_HECTARES:
                convertedMeasurement = measurementToConvert * 0.404686;
                break;
        }
        return convertedMeasurement.toString();
    }

}

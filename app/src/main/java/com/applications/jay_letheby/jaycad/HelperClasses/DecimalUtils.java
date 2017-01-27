package com.applications.jay_letheby.jaycad.HelperClasses;

import java.math.BigDecimal;

/**
 * Created by jay_the_superwarrior on 27/01/2017.
 * This class rounds a  decimal number
 */

public class DecimalUtils {

    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}

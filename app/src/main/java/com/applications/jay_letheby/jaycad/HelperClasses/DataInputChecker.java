package com.applications.jay_letheby.jaycad.HelperClasses;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.applications.jay_letheby.jaycad.R;

/**
 * Created by jay_the_superwarrior on 22/01/2017.
 * A class to check containing methods to check user input data
 * This class checks for no input data
 * This class checks that data entered is numerical (either double or integer)
 * This class contains alert dialogs for errors in user input
 */

public class DataInputChecker {

    String [] inputFieldsToCheckArray;

    public DataInputChecker(){

    }

    public boolean emptyField(String inputField) {
        // check for no user input
        if (inputField.length() == 0)
            return true;
        else
            return false;
    }

    public  boolean allInputFieldsEmpty(String [] inputFields) {
        // check if a series of fields are all empty

         boolean flag = true;

         for (String inputField : inputFields) {

             if (inputField.length() > 0) {
                 // field found with data so not all fields are empty
                 flag =  false;
             }
         }

        // No data found in input Fields so return true
         return flag;
    }

    public boolean isNotNumericData(String [] inputFields, String numericType) {
        // check that input data is either double or integer types

        boolean flag = false;
        int inputFieldCheck;
        double inputFieldCheckDouble;

        for (String inputField : inputFields) {

            if (numericType.equals("Integer")) {
                try {
                    // only check ifd field not empty, assume empty input fields to be zero
                    if (inputField.length() > 0)
                        inputFieldCheck = Integer.parseInt(inputField);
                }
                catch (NumberFormatException e) {
                    flag = true;
                }
            } else if (numericType.equals("Double")) {
                try {
                    if (inputField.length() > 0)
                        inputFieldCheckDouble = Double.parseDouble(inputField);
                }
                catch (NumberFormatException e) {
                    flag = true;
                }
            }

        }

        return flag;
    }


}

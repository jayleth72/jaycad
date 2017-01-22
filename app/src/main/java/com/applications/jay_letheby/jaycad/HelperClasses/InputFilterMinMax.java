package com.applications.jay_letheby.jaycad.HelperClasses;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;
/**
 * Created by jay_the_superwarrior on 22/01/2017.
 * This is used to set a min and max value on EditText Fields
 * For instance setting a value of 0-59 on minutes and seconds fields
 */

public class InputFilterMinMax implements InputFilter {

    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;

        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}

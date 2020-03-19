package com.breiter.mynutrition.ui.tool;


import java.util.Locale;

 public class DoubleToString {

     /*
     nDigits represents decimal precision
     As per food regulation:
     kcal & kJ: no decimal places
     salt: 2 decimal places
     all the other nutrients: 1 decimal place
     */

    public static String convert(double value, int nDigits) {
        return String.format("%." + nDigits + "f", value, Locale.getDefault());
    }
}

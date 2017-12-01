package com.beta.tacademy.hellomoneycustomer.common.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by kirkee on 2017. 10. 14..
 */

public class CustomAxisValueFormatter implements IAxisValueFormatter {

    /**
     * decimalformat for formatting
     */
    protected DecimalFormat mFormat;

    public CustomAxisValueFormatter() {
        mFormat = new DecimalFormat("0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // avoid memory allocations here (for performance)
        return mFormat.format(value);
    }

}

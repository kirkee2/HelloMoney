package com.beta.tacademy.hellomoneycustomer.common.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by kirkee on 2017. 10. 14..
 */

public class CustomValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public CustomValueFormatter() {
        mFormat = new DecimalFormat("0.00"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return mFormat.format(value); // e.g. append a dollar-sign
    }
}

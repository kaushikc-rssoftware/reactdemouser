package com.example.merchantdemo.util;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static String getCurrentDateFormatted() {
        // Get the current date
        Date currentDate = new Date();

        // Define the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        // Format the current date
        return sdf.format(currentDate);
    }

    public static CalendarConstraints buildCalendarConstraints(/*Long startDate*/) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        // Set the start date to the specified startDate
//        constraintsBuilder.setStart(startDate);

        // Set the end date to today
        constraintsBuilder.setEnd(System.currentTimeMillis());

        // Specify the selection criteria
        constraintsBuilder.setValidator(DateValidatorPointBackward.now());

        return constraintsBuilder.build();
    }
}

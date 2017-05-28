package com.example.gabor.mybudget.View.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.gabor.mybudget.Model.Constants.Constants;
import com.example.gabor.mybudget.R;

import java.util.Calendar;

/**
 * Created by Gabor on 2017. 05. 28..
 */

public class YearMonthPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;
    private int selectedYear = 0;
    private int selectedMonth = 0;


    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        if (selectedMonth > 0) {
            monthPicker.setValue(selectedMonth);
        } else {
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
        }

        yearPicker.setMinValue(1971);
        yearPicker.setMaxValue(2099);
        if (selectedYear > 0) {
            yearPicker.setValue(selectedYear);
        } else {
            yearPicker.setValue(cal.get(Calendar.YEAR));
        }

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        YearMonthPickerDialog.this.getDialog().cancel();
                    }
                }).setNeutralButton(getString(R.string.current_time), null);
//                .setNeutralButton("Current time", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Calendar c = Calendar.getInstance();
//                        yearPicker.setValue(c.get(Calendar.YEAR));
//                        monthPicker.setValue(c.get(Calendar.MONTH) + 1);
//                    }
//                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);

                neutralButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        yearPicker.setValue(c.get(Calendar.YEAR));
                        monthPicker.setValue(c.get(Calendar.MONTH) + 1);
                    }
                });
            }
        });
        return alertDialog;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }
}
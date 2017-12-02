package com.example.miek.miekstours.Classes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MMART on 11/27/2017.
 */
public class DateTextPicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;

    public DateTextPicker(Context context, EditText editText){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.editText.setOnFocusChangeListener(this);
        this.editText.setInputType(InputType.TYPE_NULL);
        this.context = context;
        myCalendar = Calendar.getInstance();
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(String string) {
        editText.setText(string);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)     {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        editText.setText(sdformat.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        new DatePickerDialog(context, this, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new DatePickerDialog(context, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
}


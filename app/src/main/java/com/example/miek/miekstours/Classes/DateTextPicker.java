package com.example.miek.miekstours.Classes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MMART on 11/27/2017.
 */
public class DateTextPicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context context;
    private Date currentDate;
    private SimpleDateFormat sdFormat;

    public DateTextPicker(Context context, EditText editText){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.editText.setOnFocusChangeListener(this);
        this.editText.setInputType(InputType.TYPE_NULL);
        this.context = context;
        this.sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        myCalendar = Calendar.getInstance();
    }

    public String getText() {
        return editText.getText().toString();
    }

    public EditText getEditText() {
        return editText;
    }

    public void setText(String string) {
        editText.setText(string);
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return sdFormat;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)     {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = sdFormat.format(myCalendar.getTime());
        editText.setText(dateString);
        try {
            this.currentDate = sdFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getCurrentDate() {
        return currentDate;
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


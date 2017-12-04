package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by MMART on 11/27/2017.
 */
public class LocationPicker implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText editText;
    private Context context;
    private int PLACE_PICKER_REQUEST = 1;
    ProgressDialog dialog;

    public LocationPicker(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.editText.setOnFocusChangeListener(this);
        this.editText.setInputType(InputType.TYPE_NULL);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        locationText.activityResult(requestCode, resultCode, data);
//    }

    public EditText getEditText() {
        return editText;
    }

    public Place activityResult(int requestCode, int resultCode, Intent data) {
        dialog.dismiss();
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.context);
                editText.setText(place.getName());
                return place;
            }
        }
        return null;
    }

    public void startPicker() {
        dialog = ProgressDialog.show(this.context, "",
                "Loading Location Picker, please wait...", true);
        new Thread() {
            public void run() {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        Activity activity = (Activity) context;
                        activity.startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(String string) {
        editText.setText(string);
    }

    @Override
    public void onClick(View v) {
        startPicker();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            startPicker();
        }
    }
}

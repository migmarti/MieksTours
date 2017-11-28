package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    public LocationPicker(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;

        this.editText.setOnClickListener(this);
        this.editText.setOnFocusChangeListener(this);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        locationText.activityResult(requestCode, resultCode, data);
//    }
    public void activityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.context);
                editText.setText(place.getName());
            }
        }
    }

    public void startPicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Activity activity = (Activity) this.context;
            activity.startActivityForResult(builder.build(this.context), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return editText.getText().toString();
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

package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.miek.miekstours.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by MMART on 11/27/2017.
 */
public class Utils {

    public static void showAlert(String title, String message, Activity act) {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(act);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();
    }

    public static void executeActivity(Context context, Activity activityToFinish, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
        if (activityToFinish != null) {
            activityToFinish.finish();
        }
    }

    public static Boolean validateEmailPassword(Context context, EditText emailText, EditText passwordText) {

        boolean cancel = false;
        View focusView = null;

        if (emailText != null) {
            emailText.setError(null);
            String email = emailText.getText().toString();

            if (TextUtils.isEmpty(email)) {
                emailText.setError(context.getString(R.string.error_field_required));
                focusView = emailText;
                cancel = true;
            } else if (!isEmailValid(email)) {
                emailText.setError(context.getString(R.string.error_invalid_email));
                focusView = emailText;
                cancel = true;
            }
        }

        if (passwordText != null) {
            passwordText.setError(null);
            String password = passwordText.getText().toString();

            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                passwordText.setError(context.getString(R.string.error_invalid_password));
                focusView = passwordText;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static Boolean validateInformation(Context context, EditText firstNameText, EditText lastNameText, EditText dobText,
                                              EditText locationText, EditText descriptionText) {
        ArrayList<EditText> editTexts = new ArrayList<EditText>();
        editTexts.add(firstNameText);
        editTexts.add(lastNameText);
        editTexts.add(dobText);
        editTexts.add(locationText);
        editTexts.add(descriptionText);

        Boolean cancel = false;
        View focusView = null;

        for (EditText editText : editTexts) {
            editText.setError(null);
            if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError(context.getString(R.string.error_field_required));
                focusView = editText;
                cancel = true;
                break;
            }
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public static Boolean validateLocation(Context context, LocationPicker picker) {
        EditText pickerText = picker.getEditText();
        pickerText.setError(null);

        Boolean cancel = false;
        View focusView = null;

        return false;
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = new Date();
        try {
            parsedDate = sdFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static Date today() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayString =  sdFormat.format(new Date());
        Date todayDate = parseDate(todayString);
        return todayDate;
    }

    public static Boolean validateDates(Context context, DateTextPicker start, DateTextPicker end) {
        Date startDate = start.getCurrentDate();
        Date endDate = end.getCurrentDate();
        EditText startText = start.getEditText();
        EditText endText = end.getEditText();
        startText.setError(null);
        endText.setError(null);
        Date todayDate = today();

        Boolean cancel = false;

        if (TextUtils.isEmpty(startText.getText().toString())) {
            startText.setError(context.getString(R.string.error_field_required));
            cancel = true;
        }
        else if (TextUtils.isEmpty(endText.getText().toString())) {
            endText.setError(context.getString(R.string.error_field_required));
            cancel = true;
        }
        else if (startDate.after(endDate)) {
            startText.setError("Start date cannot be after end date.");
            cancel = true;
        }
        else if (startDate.before(todayDate)) {
            startText.setError("Start date cannot be earlier than today.");
            cancel = true;
        }
        else if (endDate.before(todayDate)) {
            endText.setError("End date cannot be earlier than today.");
            cancel = true;
        }

        if (cancel) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private static boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    public static void checkHost(final Activity activity, final String id, final String num, final String startDate, final String endDate,
                                 final RequestQueue queue, final DatabaseHandler db) {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tecnami.com/miekstours/api/check_host.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Objects.equals(num, "0")) {
                            Utils.showAlert("Alert", "You are no longer hosting.", activity);
                            db.updateHost(id, 0);
                        }
                        else if (Objects.equals(num, "1")) {
                            Utils.showAlert("Success", "You are now hosting!", activity);
                            db.updateHost(id, 1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.toString(), activity);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("UserId", id);
                params.put("startDate", startDate);
                params.put("endDate", startDate);
                params.put("HostingStatus", num);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

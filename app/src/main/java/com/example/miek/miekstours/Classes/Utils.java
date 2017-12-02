package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.miek.miekstours.R;

import java.util.ArrayList;

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

        emailText.setError(null);
        String email = emailText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            emailText.setError(context.getString(R.string.error_field_required));
            focusView = emailText;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            emailText.setError(context.getString(R.string.error_invalid_email));
            focusView = emailText;
            cancel = true;
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

    private static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private static boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}

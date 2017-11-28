package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MMART on 11/27/2017.
 */
public class Utils {
    public static UserAccount getCurrentUser(DatabaseHandler db) {
        UserAccount user = new UserAccount();
        try {
            user = db.getCurrentUser();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

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
}

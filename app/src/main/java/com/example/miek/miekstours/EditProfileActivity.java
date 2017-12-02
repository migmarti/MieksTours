package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.DateTextPicker;
import com.example.miek.miekstours.Classes.LocationPicker;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;
import com.google.android.gms.location.places.Place;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    UserAccount currentUser;
    EditText emailText, passwordText, confirmPasswordText, firstNameText, lastNameText, descriptionText;
    DateTextPicker dobText;
    LocationPicker locationText;
    Button buttonAccept, buttonInterests, buttonBecomeHost;
    String email, password, confirmPassword, firstName, lastName, description, location, dob;
    private String EDITPROFILE_URL = "http://tecnami.com/miekstours/api/edit_profile.php";
    private String CHECKUSER_URL = "http://tecnami.com/miekstours/api/check_user.php";
    private DatabaseHandler db;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        queue = Volley.newRequestQueue(this);

        emailText = (EditText) findViewById(R.id.emailText);
        emailText.setEnabled(false);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        dobText = new DateTextPicker(this, (EditText) findViewById(R.id.dobText));
        locationText = new LocationPicker(this, (EditText) findViewById(R.id.locationText));
        descriptionText = (EditText) findViewById(R.id.descriptionText);

        emailText.setText(currentUser.getEmail());
        firstNameText.setText(currentUser.getFirstName());
        lastNameText.setText(currentUser.getLastName());
        dobText.setText(currentUser.getDob());
        locationText.setText(currentUser.getLocation());
        descriptionText.setText(currentUser.getDescription());

        buttonAccept = (Button) findViewById(R.id.buttonAccept);
        buttonInterests = (Button) findViewById(R.id.buttonInterests);
        buttonBecomeHost = (Button) findViewById(R.id.buttonBecomeHost);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                confirmPassword = confirmPasswordText.getText().toString();
                firstName = firstNameText.getText().toString();
                lastName = lastNameText.getText().toString();
                dob = dobText.getText().toString();
                location = locationText.getText().toString();
                description = descriptionText.getText().toString();
                updateProfile();
                //finish();
            }
        });
        buttonInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.executeActivity(getApplicationContext(), null, InterestsActivity.class);
            }
        });
        buttonBecomeHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.executeActivity(getApplicationContext(), null, BecomeHostActivity.class);
            }
        });
    }

    private void updateProfile() {
        Boolean check;
        Boolean updatePassword = false;

        if (password != null && password.length() > 0) {
            check = Utils.validateEmailPassword(EditProfileActivity.this, emailText, passwordText);
            updatePassword = true;
        }
        else {
            check = Utils.validateEmailPassword(EditProfileActivity.this, emailText, null);
        }

        if (check && Utils.validateInformation(EditProfileActivity.this, firstNameText, lastNameText,
                (EditText) findViewById(R.id.dobText), (EditText) findViewById(R.id.locationText),
                descriptionText)) {
            if (updatePassword) {
                if (Objects.equals(password, confirmPassword)) {
                    editProfile(currentUser.getId(), password);
                    //db.updateUser(currentUser.getId(), firstName, lastName, dob, password, email, location,
                            //description, currentUser.getHostingStatus());
                }
                else {
                    Utils.showAlert("Invalid Input", "Your passwords do not match.", EditProfileActivity.this);
                }
            }
            else {
                editProfile(currentUser.getId(), currentUser.getPassword());
                //db.updateUser(currentUser.getId(), firstName, lastName, dob, currentUser.getPassword(), email, location,
                        //description, currentUser.getHostingStatus());
            }
            //Utils.executeActivity(getApplicationContext(), EditProfileActivity.this, HubActivity.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Place place = locationText.activityResult(requestCode, resultCode, data);
    }

    private void editProfile(final String userId, final String password) {
        System.out.println("DEBUG: Entered edit profile");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDITPROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.showAlert("Success", "Your account has been updated.", EditProfileActivity.this);
                        db.updateUser(currentUser.getId(), firstName, lastName, dob, password, email, location,
                                description, currentUser.getHostingStatus());
                        //finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("MIEKIFY DEBUG Edit Profile: " + error.getMessage());
                        Utils.showAlert("Technical Error - Edit Profile", error.getMessage(), EditProfileActivity.this);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(db.KEY_USERID, userId);
                params.put(db.KEY_EMAIL, email);
                params.put(db.KEY_PASSWORD, password);
                params.put(db.KEY_FIRSTNAME, firstName);
                params.put(db.KEY_LASTNAME, lastName);
                params.put(db.KEY_DOB, dob);
                params.put(db.KEY_LOCATION, location);
                params.put(db.KEY_DESCRIPTION, description);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }
}


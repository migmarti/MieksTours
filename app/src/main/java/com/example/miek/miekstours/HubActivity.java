package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.DateTextPicker;
import com.example.miek.miekstours.Classes.LocationPicker;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;
import com.google.android.gms.location.places.Place;

public class HubActivity extends AppCompatActivity {

    TextView welcomeText;
    LocationPicker locationText;
    DateTextPicker endDateText, startDateText;
    Button editProfileButton, signOutButton, findHostsButton;
    UserAccount currentUser;
    Place place;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        welcomeText = (TextView) findViewById(R.id.textWelcome);
        welcomeText.setText("Welcome " + currentUser.getFirstName());
        locationText = new LocationPicker(this, (EditText) findViewById(R.id.locationText));
        endDateText = new DateTextPicker(this, (EditText) findViewById(R.id.endDateText));
        startDateText = new DateTextPicker(this, (EditText) findViewById(R.id.startDateText));

        findHostsButton = (Button) findViewById(R.id.buttonFindHosts);
        editProfileButton = (Button) findViewById(R.id.buttonEditProfile);
        signOutButton = (Button) findViewById(R.id.buttonSignOut);
        findHostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.executeActivity(getApplicationContext(), null, AvailableHostsActivity.class);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.executeActivity(getApplicationContext(), null, EditProfileActivity.class);
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        place = locationText.activityResult(requestCode, resultCode, data);
    }

    public void signOut() {
        db.deleteAllUsers();
        Utils.executeActivity(getApplicationContext(), HubActivity.this, LoginActivity.class);
    }

    public void onBackPressed() {
        finish();
    }
}

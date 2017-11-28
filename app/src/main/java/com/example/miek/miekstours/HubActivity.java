package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;

public class HubActivity extends AppCompatActivity {

    TextView welcomeText;
    Button editProfileButton, signOutButton;
    UserAccount currentUser;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = Utils.getCurrentUser(db);
        welcomeText = (TextView) findViewById(R.id.textWelcome);
        welcomeText.setText("Welcome " + currentUser.getFirstName());
        editProfileButton = (Button) findViewById(R.id.buttonEditProfile);
        signOutButton = (Button) findViewById(R.id.buttonSignOut);
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

    public void signOut() {
        db.deleteAllUsers();
        Utils.executeActivity(getApplicationContext(), HubActivity.this, LoginActivity.class);
    }

    public void onBackPressed() {
        finish();
    }
}

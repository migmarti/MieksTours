package com.example.miek.miekstours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.UserAccount;

public class AvailableHostsActivity extends AppCompatActivity {

    Button buttonAccept;
    DatabaseHandler db;
    UserAccount currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hosts);
    }

    public void onBackPressed() {
        finish();
    }
}

package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.UserAccount;

public class BecomeHostActivity extends AppCompatActivity {

    Button buttonAccept;
    DatabaseHandler db;
    UserAccount currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_host);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();

        TextView textLocation = (TextView) findViewById(R.id.textViewLocation);
        textLocation.setText(currentUser.getLocation());

        buttonAccept = (Button) findViewById(R.id.buttonAcceptHost);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}

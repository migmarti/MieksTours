package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.DateTextPicker;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;

public class BecomeHostActivity extends AppCompatActivity {

    Button buttonAccept;
    DatabaseHandler db;
    UserAccount currentUser;
    DateTextPicker startDate, endDate;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_host);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();

        TextView textLocation = (TextView) findViewById(R.id.textViewLocation);
        textLocation.setText(currentUser.getLocation());

        startDate = new DateTextPicker(this, (EditText) findViewById(R.id.startDateText));
        endDate = new DateTextPicker(this, (EditText) findViewById(R.id.endDateText));

        buttonAccept = (Button) findViewById(R.id.buttonAcceptHost);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.validateDates(getBaseContext(), startDate, endDate)) {
                    Utils.checkHost(BecomeHostActivity.this, currentUser.getId(), "1",
                            startDate.getText(), endDate.getText(), queue);
                    //finish();
                }
            }
        });
    }




    public void onBackPressed() {
        finish();
    }
}

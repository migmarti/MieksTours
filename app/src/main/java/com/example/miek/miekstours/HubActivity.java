package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.DateTextPicker;
import com.example.miek.miekstours.Classes.LocationPicker;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class HubActivity extends AppCompatActivity {

    TextView infoText;
    LocationPicker locationText;
    DateTextPicker endDateText, startDateText;
    Button editProfileButton, signOutButton, findHostsButton;
    UserAccount currentUser;
    ArrayList<UserAccount> hosts;
    Place place;
    DatabaseHandler db;
    String GETHOSTS_URL = "";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        queue = Volley.newRequestQueue(this);

        infoText = (TextView) findViewById(R.id.textViewInfo);
        infoText.setText("Logged in as: " + currentUser.getEmail());
        //welcomeText.setText("Welcome " + currentUser.getFirstName());
        locationText = new LocationPicker(this, (EditText) findViewById(R.id.locationText));
        endDateText = new DateTextPicker(this, (EditText) findViewById(R.id.endDateText));
        startDateText = new DateTextPicker(this, (EditText) findViewById(R.id.startDateText));

        findHostsButton = (Button) findViewById(R.id.buttonFindHosts);
        editProfileButton = (Button) findViewById(R.id.buttonEditProfile);
        signOutButton = (Button) findViewById(R.id.buttonSignOut);
        findHostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.validateDates(HubActivity.this, startDateText, endDateText)) {
                    getHosts();
                }
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

    private void getHosts() {

        final JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, GETHOSTS_URL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hosts = new ArrayList<UserAccount>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        hosts.add(new UserAccount(response.getString(i), db));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), AvailableHostsActivity.class);
                intent.putExtra("Parcel", hosts);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showAlert("Technical Error", error.toString(), HubActivity.this);
            }
        });
        queue = Volley.newRequestQueue(this);
        queue.add(arrayRequest);
    }

    public void signOut() {
        db.deleteAllUsers();
        Utils.executeActivity(getApplicationContext(), HubActivity.this, LoginActivity.class);
    }

    public void onBackPressed() {
        finish();
    }
}

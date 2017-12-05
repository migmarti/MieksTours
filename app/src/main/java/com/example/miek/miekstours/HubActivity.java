package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.miek.miekstours.Classes.Requests;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HubActivity extends AppCompatActivity {

    TextView infoText, welcomeText, hostText;
    LocationPicker locationText;
    DateTextPicker endDateText, startDateText;
    Button editProfileButton, signOutButton, findHostsButton, buttonViewRequests;
    UserAccount currentUser;
    Boolean isHost = false;
    ArrayList<UserAccount> hosts = new ArrayList<>();
    ArrayList<Requests> requests = new ArrayList<>();
    Double lat, lng;
    DatabaseHandler db;
    String GETHOSTS_URL = "http://tecnami.com/miekstours/api/get_hosts.php";
    String REQUESTSHOST_URL = "http://tecnami.com/miekstours/api/get_requests_host.php";
    String REQUESTSTRAVELER_URL = "http://tecnami.com/miekstours/api/get_requests_traveler.php";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        if (currentUser.getHostingStatus() == 1) {
            isHost = true;
        }
        queue = Volley.newRequestQueue(this);

        infoText = (TextView) findViewById(R.id.textViewInfo);
        welcomeText = (TextView) findViewById(R.id.textWelcome);
        hostText = (TextView) findViewById(R.id.textViewHost);
        infoText.setText("Logged in as: " + currentUser.getEmail());
        welcomeText.setText("Welcome " + currentUser.getFirstName() + "!");
        locationText = new LocationPicker(this, (EditText) findViewById(R.id.locationText));
        endDateText = new DateTextPicker(this, (EditText) findViewById(R.id.endDateText));
        startDateText = new DateTextPicker(this, (EditText) findViewById(R.id.startDateText));

        findHostsButton = (Button) findViewById(R.id.buttonFindHosts);
        if (isHost) {
            hostText.setText("You are currently a Host");
            findHostsButton.setEnabled(false);
        }
        else {
            hostText.setText("You are currently a Traveler");
            findHostsButton.setEnabled(true);
        }
        editProfileButton = (Button) findViewById(R.id.buttonEditProfile);
        signOutButton = (Button) findViewById(R.id.buttonSignOut);
        buttonViewRequests = (Button) findViewById(R.id.buttonViewRequests);
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
                Utils.executeActivity(getApplicationContext(), HubActivity.this, EditProfileActivity.class);
            }
        });
        buttonViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHost) {
                    getRequests("HostId", REQUESTSHOST_URL);
                }
                else {
                    getRequests("TravelerId", REQUESTSTRAVELER_URL);
                }
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
        Place place = locationText.activityResult(requestCode, resultCode, data);
        if (place != null) {
            LatLng geo = place.getLatLng();
            lat = geo.latitude;
            lng = geo.longitude;
        }
    }

    private void getHosts() {
        hosts.clear();
        final String startDate = startDateText.getText();
        final String endDate = endDateText.getText();
        final String latString = lat + "";
        final String lngString = lng + "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETHOSTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    String sub = jsonArray.getString(i);
                                    UserAccount host = new UserAccount(sub, db);
                                    hosts.add(host);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (hosts.size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), AvailableHostsActivity.class);
                            intent.putExtra("Parcel", hosts);
                            intent.putExtra("StartDate", startDate);
                            intent.putExtra("EndDate", endDate);
                            startActivity(intent);
                        }
                        else {
                            Utils.showAlert("Sorry", "No results found.", HubActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("MIEK AVAILABLE HOSTS ERROR: " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                params.put("Latitud", latString);
                params.put("Longitud", lngString);
                return params;
            }
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void getRequests(final String key, final String url) {
        requests.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //{"data":[{"RequestId":"9","HostId":"h4xsoreecu","TravelerId":"da9pxforlv","StartDate":"2017-12-05","EndDate":"2017-12-31","Comment":"Hello Rodo Im Miek","StatusId":"0"}]}
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    String sub = jsonArray.getString(i);
                                    Requests request = new Requests(sub);
                                    if (currentUser.getHostingStatus() == 1 ) {
                                        if (!Objects.equals(request.getStatus(), "1")) {
                                            requests.add(request);
                                        }
                                    }
                                    else {
                                        requests.add(request);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (requests.size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), RequestsActivity.class);
                            intent.putExtra("Requests", requests);
                            intent.putExtra("User", currentUser);
                            startActivity(intent);
                        }
                        else {
                            Utils.showAlert("Alert", "You currently have no active requests.", HubActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), HubActivity.this);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(key, currentUser.getId());
                return params;
            }
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    public void signOut() {
        db.deleteAllUsers();
        Utils.executeActivity(getApplicationContext(), HubActivity.this, LoginActivity.class);
    }

    public void onBackPressed() {
        finish();
    }
}

package com.example.miek.miekstours;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HostProfileActivity extends AppCompatActivity {

    Button buttonRequest;
    TextView textViewTitle, textViewHostingAt, textViewFromDate, textViewToDate, textViewLikes, textViewDescription, textViewEmail;
    UserAccount host, currentUser;
    String title, startDate, endDate;
    RequestQueue queue;
    String USERINTERESTS_URL = "http://tecnami.com/miekstours/api/interest.php";
    String SENDREQUEST_URL = "http://tecnami.com/miekstours/api/request.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_profile);

        queue = Volley.newRequestQueue(this);
        currentUser = new DatabaseHandler(getApplicationContext()).getCurrentUser();

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewHostingAt = (TextView) findViewById(R.id.textViewHostingAt);
        textViewFromDate = (TextView) findViewById(R.id.textViewFromDate);
        textViewToDate = (TextView) findViewById(R.id.textViewToDate);
        textViewLikes = (TextView) findViewById(R.id.textViewLikes);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);

        host = this.getIntent().getParcelableExtra("Chosen Host");
        title = this.getIntent().getStringExtra("Title");
        startDate = this.getIntent().getStringExtra("StartDate");
        endDate = this.getIntent().getStringExtra("EndDate");

        textViewTitle.setText(title);
        textViewEmail.setText(host.getEmail());
        textViewHostingAt.setText("Hosting at: " + host.getLocation());
        textViewFromDate.setText("From: " + host.getStartDate());
        textViewToDate.setText("To: " + host.getEndDate());
        textViewDescription.setText(host.getDescription());
        getUserInterests();

        buttonRequest = (Button) findViewById(R.id.buttonRequest);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupText();
            }
        });
    }

    public void getUserInterests() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USERINTERESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //{"data":[{"FirstName":"Sam","Name":"Gastronomy"},{"FirstName":"Sam","Name":"Dating"},{"FirstName":"Sam","Name":"Culture"},{"FirstName":"Sam","Name":"Business"}]}
                        String userInterests = "";
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray data = json.getJSONArray("data");
                            JSONObject firstObject = data.getJSONObject(0);
                            userInterests = firstObject.getString("Name");
                            for (int i = 1; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                System.out.println(object.getString("Name"));
                                userInterests += ", " + object.getString("Name");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textViewLikes.setText("Likes: " + userInterests);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("MIEK USERACCOUNT GET INTERESTS ERROR: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", host.getId());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void sendRequest(final String comment) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SENDREQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("MIEK RESPONSE: " + response);
                        Utils.showAlert("Success", "Your request has been sent.", HostProfileActivity.this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("MIEK USERACCOUNT SEND REQUEST ERROR: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("HostId", host.getId());
                params.put("TravelerId", currentUser.getId());
                params.put("StartDate", startDate);
                params.put("EndDate", endDate);
                params.put("Comment", comment);
                params.put("StatusId", "0");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void popupText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send request to " + host.getFirstName());

        final EditText input = new EditText(this);
        input.setSingleLine(false);
        input.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setLines(5);
        input.setMaxLines(10);
        input.setVerticalScrollBarEnabled(true);
        input.setMovementMethod(ScrollingMovementMethod.getInstance());
        input.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String request = input.getText().toString();
                sendRequest(request);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void onBackPressed() {
        finish();
    }
}

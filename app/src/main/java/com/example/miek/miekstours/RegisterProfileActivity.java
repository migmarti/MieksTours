package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.example.miek.miekstours.Classes.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterProfileActivity extends AppCompatActivity {
    private String REGISTER_URL = "http://tecnami.com/miekstours/api/register.php";
    private RequestQueue queue;
    private TextView tv;
    private String id, email, password, firstName, lastName, dob, location, description;
    private EditText firstNameText, lastNameText, dobText, locationText, descriptionText;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);
        db = new DatabaseHandler(getApplicationContext());
        queue = Volley.newRequestQueue(this);

        tv = (TextView) findViewById(R.id.profileTitle);
        firstNameText = (EditText) findViewById(R.id.textFirstName);
        lastNameText = (EditText) findViewById(R.id.textLastName);
        dobText = (EditText) findViewById(R.id.textDOB);
        locationText = (EditText) findViewById(R.id.textLocation);
        descriptionText = (EditText) findViewById(R.id.textDescription);

        Bundle extras = getIntent().getExtras();
        email = extras.getString(db.KEY_EMAIL);
        password = extras.getString(db.KEY_PASSWORD);
        tv.setText("Create Profile\n" + email);

        Button finishButton = (Button) findViewById(R.id.buttonFinish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyFields(view);
            }
        });
    }

    private void verifyFields(final View view) {
        firstName = firstNameText.getText().toString();
        lastName = lastNameText.getText().toString();
        dob = dobText.getText().toString();
        location = locationText.getText().toString();
        description = descriptionText.getText().toString();
        id = getRandomString(10);

        registerAccount(view, id, email, password, firstName, lastName, dob, location, description);
    }

    private void registerAccount(final View view, final String id, final String email, final String password,
                                 final String firstName, final String lastName, final String dob,
                                 final String location, final String description) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("created")) {
                            Snackbar.make(view, "Success! The account has been created", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            db.addUser(new UserAccount(id, email, password, firstName, lastName, dob, location, description, 0, 0));
                            Intent intent = new Intent(getApplicationContext(), HubActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Snackbar.make(view, response, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error: " + error.getMessage(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(db.KEY_USERID, id);
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

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}

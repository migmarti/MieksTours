package com.example.miek.miekstours;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterProfileActivity extends AppCompatActivity {
    private String REGISTER_URL = "http://tecnami.com/miekstours/api/register.php";
    private RequestQueue queue;
    private TextView tv;
    private String email, password, firstName, lastName, dob;
    private EditText firstNameText, lastNameText, dobText;
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

        Bundle extras = getIntent().getExtras();
        email = extras.getString(db.KEY_EMAIL);
        password = extras.getString(db.KEY_PASSWORD);
        tv.setText("Create Profile\n" + email);


    }

    private void verifyFields() {

    }

    private void registerAccount(final View view, final String email, final String password,
                                 final String firstName, final String lastName, final String dob) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(view, response, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        if (response.contains("created")) {
                            Snackbar.make(view, "Success! The account has been created", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            SystemClock.sleep(1000);
                            finish();
                        }
                        else {
                            Snackbar.make(view, "An account with that email already exists.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
                params.put(db.KEY_USERID, getRandomString(10));
                params.put(db.KEY_EMAIL, email);
                params.put(db.KEY_PASSWORD, password);
                params.put(db.KEY_FIRSTNAME, firstName);
                params.put(db.KEY_LASTNAME, lastName);
                params.put(db.KEY_DOB, dob);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void onBackPressed() {
        finish();
    }

    private static final String ALLOWED_CHARACTERS ="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}

package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miek.miekstours.Classes.UserAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private String getUrl = "";
    private String postUrl = "";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        queue = Volley.newRequestQueue(this);
        emailText = (EditText) findViewById(R.id.textEmail);
        passwordText = (EditText) findViewById(R.id.textPassword);
        confirmPasswordText = (EditText) findViewById(R.id.textConfirmPassword);

        Button okButton = (Button) findViewById(R.id.buttonOk);
        Button backButton = (Button) findViewById(R.id.buttonBack);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void register() {
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        if (password == confirmPasswordText.getText().toString()) {
            System.out.println("confirm");
            final JsonArrayRequest checkExists = new JsonArrayRequest(Request.Method.GET, getUrl,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Boolean accountExists = false;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.getString(i));
                            if (email == jsonObject.getString("email")) {
                                Toast.makeText(getBaseContext(), "An account with that email already exists.", Toast.LENGTH_LONG).show();
                                accountExists = true;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!accountExists) {
                        UserAccount acc = new UserAccount();
                        acc.setEmail(email);
                        acc.setPassword(password);
                        JsonObjectRequest jor = uploadJSON(acc.toJSON(), postUrl);
                        queue.add(jor);
                        Toast.makeText(getBaseContext(), "The account has been created.", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(checkExists);
        }
    }

    public void onBackPressed() {
        finish();
    }

    public JsonObjectRequest uploadJSON(final JSONObject jsonBody, String url) {
        final JsonObjectRequest jsonPostRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Successfully uploaded object.");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error uploading: " + error.toString());
                    }
                });
        return jsonPostRequest;
    }
}

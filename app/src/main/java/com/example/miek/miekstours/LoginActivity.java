package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private RequestQueue queue;
    public static final String LOGIN_URL = "http://tecnami.com/miekstours/api/login.php";
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHandler(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAccount(view);
            }
        });
    }

    private void attemptLogin(View view) {

        emailText.setError(null);
        passwordText.setError(null);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            passwordText.setError(getString(R.string.error_invalid_password));
//            focusView = passwordText;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(email)) {
//            emailText.setError(getString(R.string.error_field_required));
//            focusView = emailText;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            emailText.setError(getString(R.string.error_invalid_email));
//            focusView = emailText;
//            cancel = true;
//        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Snackbar.make(view, "Attempting Login...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            validateCredentials(view, email, password);
        }
    }

    private void registerAccount(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

//    private void validateCredentials(final View view, final String email, final String password) {
//        final JsonArrayRequest validate = new JsonArrayRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Boolean success = false;
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response.getString(i));
//                        if (Objects.equals(email, jsonObject.getString("email")) && Objects.equals(password, jsonObject.getString("password"))) {
//                            Snackbar.make(view, "Success!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                            success = true;
//                            break;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (!success) {
//                    Snackbar.make(view, "Incorrect username and/or password", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//            }
//        });
//        queue.add(validate);
//    }

    private void validateCredentials(final View view, final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().contains("success")){
                            Snackbar.make(view, "Success!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }else{
                            Snackbar.make(view, "Incorrect username and/or password", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
                params.put(db.KEY_EMAIL, email);
                params.put(db.KEY_PASSWORD, password);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}


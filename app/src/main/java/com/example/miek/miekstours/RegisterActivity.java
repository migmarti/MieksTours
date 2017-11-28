package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
import com.example.miek.miekstours.Classes.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private String CHECKUSER_URL = "http://tecnami.com/miekstours/api/check_user.php";
    private RequestQueue queue;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHandler(getApplicationContext());
        queue = Volley.newRequestQueue(this);
        emailText = (EditText) findViewById(R.id.textEmail);
        passwordText = (EditText) findViewById(R.id.textPassword);
        confirmPasswordText = (EditText) findViewById(R.id.textConfirmPassword);

        Button okButton = (Button) findViewById(R.id.buttonOk);
        Button backButton = (Button) findViewById(R.id.buttonBack);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register(final View view) {
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        final String confirmPassword = confirmPasswordText.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordText.setError(getString(R.string.error_invalid_password));
            focusView = passwordText;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailText.setError(getString(R.string.error_field_required));
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError(getString(R.string.error_invalid_email));
            focusView = emailText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (!Objects.equals(confirmPassword, password)) {
                Utils.showAlert("Invalid Input", "Your passwords do not match.", RegisterActivity.this);
            }
            else {
                Snackbar.make(view, "Checking database...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                checkExists(view, email, password);
            }
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    private void checkExists(final View view, final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECKUSER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Intent intent = new Intent(getApplicationContext(), RegisterProfileActivity.class);
                            intent.putExtra(db.KEY_EMAIL, email);
                            intent.putExtra(db.KEY_PASSWORD, password);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Utils.showAlert("Invalid Email", "An account with that email already exists.", RegisterActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), RegisterActivity.this);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(db.KEY_EMAIL, email);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }



}

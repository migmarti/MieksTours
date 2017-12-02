package com.example.miek.miekstours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                if (Utils.validateEmailPassword(RegisterActivity.this, emailText, passwordText)) {
                    if (Objects.equals(passwordText.getText().toString(), confirmPasswordText.getText().toString())) {
                        checkExists(emailText.getText().toString(), passwordText.getText().toString());
                    }
                    else {
                        Utils.showAlert("Invalid Input", "Your passwords do not match.", RegisterActivity.this);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkExists(final String email, final String password) {
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
                        Utils.showAlert("Invalid Email", "An account with that email already exists.", RegisterActivity.this);
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

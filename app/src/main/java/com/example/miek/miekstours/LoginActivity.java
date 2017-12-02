package com.example.miek.miekstours;

import android.os.Bundle;
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
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.Utils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private RequestQueue queue;
    public static final String LOGIN_URL = "http://tecnami.com/miekstours/api/login.php";
    DatabaseHandler db;
    UserAccount currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        if (currentUser.getId() != null) {
            Utils.executeActivity(getApplicationContext(), LoginActivity.this, HubActivity.class);
        }
        queue = Volley.newRequestQueue(this);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.validateEmailPassword(LoginActivity.this, emailText, passwordText)) {
                    validateCredentials(emailText.getText().toString(), passwordText.getText().toString());
                }
            }
        });
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.executeActivity(getApplicationContext(), null, RegisterActivity.class);
            }
        });
    }

    private void validateCredentials(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().contains("UserId")){
                            UserAccount user = new UserAccount(response, db);
                            db.addUser(user);
                            Utils.executeActivity(getApplicationContext(), LoginActivity.this, HubActivity.class);
                        }else{
                            Utils.showAlert("Invalid Credentials", "You have entered an incorrect username and/or password.", LoginActivity.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), LoginActivity.this);
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


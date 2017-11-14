package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private String REGISTER_URL = "http://tecnami.com/miekstours/api/register.php";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_PASSWORD="Password";
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

//    public void register(final View view) {
//        final String email = emailText.getText().toString();
//        final String password = passwordText.getText().toString();
//        if (Objects.equals(password, confirmPasswordText.getText().toString())) {
//            final JsonArrayRequest checkExists = new JsonArrayRequest(Request.Method.GET, getUrl,
//                    null, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray response) {
//                    Boolean accountExists = false;
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.getString(i));
//                            if (Objects.equals(email, jsonObject.getString("email"))) {
//                                Snackbar.make(view, "An account with that email already exists", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                                accountExists = true;
//                                break;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (!accountExists) {
//                        UserAccount acc = new UserAccount();
//                        acc.setEmail(email);
//                        acc.setPassword(password);
//                        JsonObjectRequest jor = uploadJSON(acc.toJSON(), postUrl);
//                        queue.add(jor);
//                        Snackbar.make(view, "Account created", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                }
//            });
//            queue.add(checkExists);
//        }
//    }

    private void register(final View view) {
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(view, response, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }

//    public JsonObjectRequest uploadJSON(final JSONObject jsonBody, String url) {
//        final JsonObjectRequest jsonPostRequest = new JsonObjectRequest
//                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("Successfully uploaded object.");
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("Error uploading: " + error.toString());
//                    }
//                });
//        return jsonPostRequest;
//    }
}

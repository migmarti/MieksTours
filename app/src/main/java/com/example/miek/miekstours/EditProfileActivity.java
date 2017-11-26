package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

public class EditProfileActivity extends AppCompatActivity {
    DatabaseHandler db;
    UserAccount currentUser;
    String EDITPROFILE_URL = "http://tecnami.com/miekstours/api/edit_profile.php";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        queue = Volley.newRequestQueue(this);

    }

    private void editProfile(final View view, final String firstName, final String lastName,
                             final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDITPROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(view, "Account successfully updated.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
                return params;
            }
        };
        queue.add(stringRequest);
    }


    public void onBackPressed() {
        finish();
    }
}

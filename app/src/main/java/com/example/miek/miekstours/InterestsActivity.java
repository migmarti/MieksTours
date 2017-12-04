package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestsActivity extends AppCompatActivity {

    GridView grid;
    Button buttonAccept;
    DatabaseHandler db;
    UserAccount currentUser;
    HashMap<String, String> interests;
    String GETINTERESTS_URL = "http://tecnami.com/miekstours/api/get_interests.php";
    String INSERTINTERESTS_URL = "http://tecnami.com/miekstours/api/insert_interests.php";
    String USERINTERESTS_URL = "http://tecnami.com/miekstours/api/interest.php";
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();
        queue = Volley.newRequestQueue(this);
        grid = (GridView) findViewById(R.id.gridInterests);

        buttonAccept = (Button) findViewById(R.id.buttonAcceptInterests);
        buttonAccept.setEnabled(false);
        getInterests();
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringInterests = appendInterests();
                insertInterests(stringInterests);
                System.out.println(stringInterests);
            }
        });
    }

    private void getInterests() {
        StringRequest stringRequest = new StringRequest(Method.GET, GETINTERESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            interests = new HashMap<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject sub = jsonArray.getJSONObject(i);
                                    interests.put(sub.getString("Name"), sub.getString("InterestId"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            List<String> list = new ArrayList<>(interests.keySet());
                            Collections.sort(list);
                            grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
                            grid.setAdapter(new ArrayAdapter<>(InterestsActivity.this, android.R.layout.simple_list_item_multiple_choice,
                                    list.toArray(new String[list.size()])));
                            buttonAccept.setEnabled(true);
                            checkInterests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), InterestsActivity.this);
                    }
                }){
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void insertInterests(final String stringInterests) {
        StringRequest stringRequest = new StringRequest(Method.POST, INSERTINTERESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Utils.showAlert("Success", "Your interests have been updated", InterestsActivity.this);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), InterestsActivity.this);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Interests", stringInterests);
                return params;
            }
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void checkInterests() {
        StringRequest stringRequest = new StringRequest(Method.POST, USERINTERESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //{"data":[{"FirstName":"Sam","Name":"Gastronomy"},{"FirstName":"Sam","Name":"Dating"},{"FirstName":"Sam","Name":"Culture"},{"FirstName":"Sam","Name":"Business"}]}
                        System.out.println("MIEK RESPONSE: " + response);
                        Utils.showAlert("Response", response, InterestsActivity.this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.showAlert("Technical Error", error.getMessage(), InterestsActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                System.out.println("MIEK CURRENT USER ID: " + currentUser.getId());
                params.put("UserId", currentUser.getId());
                return params;
            }
        };
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private ArrayList<String> getCheckedItems(GridView grid) {
        ArrayList<String> checkedItems = new ArrayList<String>();
        SparseBooleanArray checked = grid.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            if(checked.valueAt(i)) {
                String item = (String) grid.getItemAtPosition(checked.keyAt(i));
                System.out.println("MIEK DEBUG - CHECKED ITEM: " + item);
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }

    private String appendInterests() {
        String chain = currentUser.getId() + " ";
        for (String item : getCheckedItems(grid)) {
            chain += interests.get(item) + " ";
        }
        chain = chain.substring(0, chain.length() - 1);
        return chain;
    }

    public void onBackPressed() {
        finish();
    }
}

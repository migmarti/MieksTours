package com.example.miek.miekstours.Classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.miek.miekstours.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by MMART on 12/3/2017.
 */
public class RequestsAdapter extends ArrayAdapter<Requests> {

    Context context;
    UserAccount user, user2;
    RequestQueue queue;
    String USERINFO_URL = "http://tecnami.com/miekstours/api/get_user_info.php";
    String APPROVEREQ_URL = "http://tecnami.com/miekstours/api/update_request.php";
    DatabaseHandler db;

    public RequestsAdapter(Context context, UserAccount user, RequestQueue queue) {
        super(context, R.layout.request_row, R.id.textViewInfo);
        this.context = context;
        this.user = user;
        this.queue = queue;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View objectView = super.getView(position, convertView, parent);
        final TextView textInfo = (TextView) objectView.findViewById(R.id.textViewInfo);
        final TextView textInfo2 = (TextView) objectView.findViewById(R.id.textViewInfo2);
        final TextView textBody = (TextView) objectView.findViewById(R.id.textViewBody);
        final TextView textStatus = (TextView) objectView.findViewById(R.id.textViewStatus);

        db = new DatabaseHandler(context);

        final Requests request = this.getItem(position);
        textBody.setText(request.getComment());

        Button buttonApprove = (Button) objectView.findViewById(R.id.buttonApprove);
        if (user.getHostingStatus() == 0) {
            System.out.println("MIEK HOST ID: " + request.getHostId());
            getHostInfo(request.getHostId(), textInfo, textInfo2);
            buttonApprove.setVisibility(View.INVISIBLE);
            String status = request.getStatus();
            if (Objects.equals(status, "0")) {
                textStatus.setText("Status: Pending...");
                textStatus.setTextColor(Color.YELLOW);
            }
            else {
                textStatus.setText("Status: Approved!");
                textStatus.setTextColor(Color.GREEN);
            }
        }
        else {
            getTravelerInfo(request.getTravelerId(), textInfo, textInfo2);
            textInfo.setText("From Traveler");
            textInfo2.setText("Traveler Info + Dates");

        }
        buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveRequest(request.getRequestId());
                remove(request);
                notifyDataSetChanged();
            }
        });


        return objectView;
    }

    public void approveRequest(final String requestId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPROVEREQ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("MIEK APPROVE REQUEST RESPONSE: " + response);
                        Utils.showAlert("Success", "Request has been approved.", (Activity) context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("RequestId", requestId);
                params.put("StatusId", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getTravelerInfo(final String userId, final TextView textInfo, final TextView textInfo2) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USERINFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("MIEK TRAVELER INFO: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            user2 = new UserAccount(jsonArray.getString(0), db);
                            textInfo.setText("From: " + user2.getFirstName() + " " + user2.getLastName());
                            String line1 = user2.getEmail() + ", " + user2.getLocation();
                            String line2 = user2.getStartDate() + " to " + user2.getEndDate();
                            textInfo2.setText(line1 + "\n" + line2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("UserId", userId);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getHostInfo(final String userId, final TextView textInfo, final TextView textInfo2) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USERINFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("MIEK HOST INFO: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            user2 = new UserAccount(jsonArray.getString(0), db);
                            textInfo.setText("To: " + user2.getFirstName() + " " + user2.getLastName());
                            String line1 = user2.getEmail() + ", " + user2.getLocation();
                            String line2 = user2.getStartDate() + " to " + user2.getEndDate();
                            textInfo2.setText(line1 + "\n" + line2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("UserId", userId);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}

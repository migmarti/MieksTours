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
    String USERINFO_URL = "";
    String APPROVEREQ_URL = "";

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

        final Requests request = this.getItem(position);
        textBody.setText(request.getComment());

        Button buttonApprove = (Button) objectView.findViewById(R.id.buttonApprove);
        if (user.getHostingStatus() == 0) {
            textInfo.setText("To Host");
            textInfo2.setText("Host Info + Dates");
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

    public void approveRequest(String requestId) {
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
                //params.put("", requestId);
                //params.put("", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getUserInfo(String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USERINFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                //params.put(db.KEY_EMAIL, email);
                //params.put(db.KEY_PASSWORD, password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}

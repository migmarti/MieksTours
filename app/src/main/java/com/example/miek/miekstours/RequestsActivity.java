package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.miek.miekstours.Classes.Requests;
import com.example.miek.miekstours.Classes.RequestsAdapter;
import com.example.miek.miekstours.Classes.UserAccount;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {

    UserAccount user;
    ArrayList<Requests> requests;
    ListView requestList;
    RequestsAdapter adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        queue = Volley.newRequestQueue(this);

        user = this.getIntent().getParcelableExtra("User");
        requests = this.getIntent().getParcelableArrayListExtra("Requests");

        requestList = (ListView) findViewById(R.id.listViewRequests);
        adapter = new RequestsAdapter(this, user, queue);
        requestList.setAdapter(adapter);
        fillList(requests);
    }

    public void fillList(ArrayList<Requests> requests) {
        for (Requests request : requests) {
            adapter.add(request);
        }
        adapter.notifyDataSetChanged();
    }
    public void onBackPressed() {
        finish();
    }
}

package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.UserAccount;
import com.example.miek.miekstours.Classes.UserAdapter;

import java.util.ArrayList;

public class AvailableHostsActivity extends AppCompatActivity {

    DatabaseHandler db;
    UserAccount currentUser;
    ArrayList<UserAccount> hosts;
    UserAdapter adapter;
    ListView hostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hosts);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();

        String startDate = this.getIntent().getStringExtra("StartDate");
        String endDate = this.getIntent().getStringExtra("EndDate");
        hosts = this.getIntent().getParcelableArrayListExtra("Parcel");

        hostList = (ListView) findViewById(R.id.listViewHosts);
        adapter = new UserAdapter(this, startDate, endDate);
        hostList.setAdapter(adapter);

        fillList(hosts);
    }

    public void fillList(ArrayList<UserAccount> hosts) {
        for (UserAccount host : hosts) {
            adapter.add(host);
        }
        adapter.notifyDataSetChanged();
    }


    public void onBackPressed() {
        finish();
    }
}

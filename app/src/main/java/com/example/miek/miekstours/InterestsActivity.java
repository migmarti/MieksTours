package com.example.miek.miekstours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.miek.miekstours.Classes.DatabaseHandler;
import com.example.miek.miekstours.Classes.UserAccount;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {

    ListView list;
    ScrollView scrollView;
    Button buttonAccept;
    DatabaseHandler db;
    UserAccount currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        db = new DatabaseHandler(getApplicationContext());
        currentUser = db.getCurrentUser();

        String[] items = getInterests(20);

        list = (ListView) findViewById(R.id.listInterests);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items));
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //void setItemChecked (int position, boolean value)


        buttonAccept = (Button) findViewById(R.id.buttonAcceptInterests);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> checkedItems = getCheckedItems(list);
                finish();
            }
        });
    }

    private String[] getInterests(int size) {
        String[] items = new String[size];
        for(int i = 0; i < size; i++) {
            items[i] = "Test Interest " + i;
        }
        return items;
    }

    private ArrayList<String> getCheckedItems(ListView list) {
        ArrayList<String> checkedItems = new ArrayList<String>();
        SparseBooleanArray checked = list.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            if(checked.valueAt(i)) {
                String item = (String) list.getItemAtPosition(checked.keyAt(i));
                System.out.println("MIEK DEBUG - CHECKED ITEM: " + item);
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }

    public void onBackPressed() {
        finish();
    }
}

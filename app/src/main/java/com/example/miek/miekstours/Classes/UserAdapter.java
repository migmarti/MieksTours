package com.example.miek.miekstours.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.miek.miekstours.HostProfileActivity;
import com.example.miek.miekstours.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MMART on 12/3/2017.
 */
public class UserAdapter extends ArrayAdapter<UserAccount> {

    Context context;
    String startDate, endDate;

    public UserAdapter(Context context, String startDate, String endDate) {
        super(context, R.layout.user_row, R.id.textViewHost);
        this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView = super.getView(position, convertView, parent);
        final TextView textInfo = (TextView) objectView.findViewById(R.id.textViewHost);
        TextView textLocation = (TextView) objectView.findViewById(R.id.textViewLocation);
        TextView textDates = (TextView) objectView.findViewById(R.id.textViewDates);

        final UserAccount user = this.getItem(position);
        textInfo.setText(user.getFirstName() + " " + user.getLastName() + ", " + getAge(user));
        textLocation.setText(user.getLocation());
        textDates.setText("From " + user.getStartDate() + " to " + user.getEndDate());

        Button viewProfile = (Button) objectView.findViewById(R.id.buttonViewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), HostProfileActivity.class);
                intent.putExtra("Chosen Host", user);
                intent.putExtra("Title", textInfo.getText());
                intent.putExtra("StartDate", startDate);
                intent.putExtra("EndDate", endDate);
                context.startActivity(intent);
            }
        });


        return objectView;
    }

    public int getAge(UserAccount user){
        Date dob = Utils.parseDate(user.getDob());
        Calendar dobCalendar = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dobCalendar.setTime(dob);
        int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
        return age;
    }
}

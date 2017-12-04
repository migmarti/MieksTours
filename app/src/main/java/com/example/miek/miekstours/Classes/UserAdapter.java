package com.example.miek.miekstours.Classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.miek.miekstours.R;

/**
 * Created by MMART on 12/3/2017.
 */
public class UserAdapter extends ArrayAdapter<UserAccount> {

    public UserAdapter(Context context) {
        super(context, R.layout.user_row);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView = super.getView(position, convertView, parent);

        final UserAccount user = this.getItem(position);

        return objectView;
    }
}

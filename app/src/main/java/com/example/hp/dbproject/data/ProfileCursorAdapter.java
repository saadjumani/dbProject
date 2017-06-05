package com.example.hp.dbproject.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.hp.dbproject.R;

/**
 * Created by HP on 5/28/2017.
 */

public class ProfileCursorAdapter extends CursorAdapter {


    public ProfileCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.profile_summary, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameText = (TextView) view.findViewById(R.id.profile_name);
        TextView cityText = (TextView) view.findViewById(R.id.profile_city);
        TextView countryText = (TextView) view.findViewById(R.id.profile_country);

        String name = cursor.getString(cursor.getColumnIndex("first_name"));
        String city = cursor.getString(cursor.getColumnIndex("city"));
        String country = cursor.getString(cursor.getColumnIndex("country"));

        nameText.setText(name);
        cityText.setText(city);
        countryText.setText(country);
    }
}

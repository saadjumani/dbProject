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
 * Created by HP on 5/23/2017.
 */

public class TweetCursorAdapter extends CursorAdapter{
    public TweetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView breed = (TextView) view.findViewById(R.id.summary);


        String TweeterName = cursor.getString(cursor.getColumnIndex("fName"));
        String TweetText = cursor.getString(cursor.getColumnIndex("desc"));

        name.setText(TweeterName);
        breed.setText(TweetText);

    }
}

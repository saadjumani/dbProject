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
 * Created by HP on 5/29/2017.
 */

public class MessageCursorAdapter extends CursorAdapter {

    public MessageCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.message_summary, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameText = (TextView) view.findViewById(R.id.sender_name);
        TextView mSummaryText = (TextView) view.findViewById(R.id.message_summary);
        TextView mTimeText = (TextView) view.findViewById(R.id.message_time);
        TextView mDateText = (TextView) view.findViewById(R.id.message_date);

        String fname = cursor.getString(cursor.getColumnIndex("first_name"));
        String lname = cursor.getString(cursor.getColumnIndex("last_name"));

        String fullNme = fname + " " + lname;

        String mSummary = cursor.getString(cursor.getColumnIndex("message_text"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        String date = cursor.getString(cursor.getColumnIndex("date"));

        nameText.setText(fullNme);
        mSummaryText.setText(mSummary);
        mTimeText.setText(time);
        mDateText.setText(date);

    }
}

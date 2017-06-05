package com.example.hp.dbproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hp.dbproject.R;
import com.example.hp.dbproject.data.DbHelper;

public class MessageDetails extends AppCompatActivity {
    int currentUserID;
    Long messageID;
    DbHelper mDbHelper = new DbHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        messageID = intent.getLongExtra("messageID",-1);
        setContentView(R.layout.activity_message_details);
        showMessage();
    }

    public void showMessage(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT message._id as _id, profile.first_name as first_name, login.user_name as user_name, profile.last_name as last_name, message.message_text as message_text, message.message_time as time, message.message_date as date FROM (profile JOIN message ON profile._id = message.sender_id) JOIN login ON login._id = profile._id WHERE message._id = "+messageID+";",null);

        cursor.moveToFirst();

        TextView senderNameView = (TextView) findViewById(R.id.sender_name);
        TextView userNameView = (TextView) findViewById(R.id.sender_username);
        TextView messageView = (TextView) findViewById(R.id.message_text);
        TextView dateView = (TextView) findViewById(R.id.date);
        TextView timeView = (TextView) findViewById(R.id.time);

        String name = cursor.getString(cursor.getColumnIndex("first_name"));
        String userName = cursor.getString(cursor.getColumnIndex("user_name"));
        String messageText = cursor.getString(cursor.getColumnIndex("message_text"));
        String mDate = cursor.getString(cursor.getColumnIndex("date"));
        String mTime = cursor.getString(cursor.getColumnIndex("time"));

        senderNameView.setText(name);
        userNameView.setText(userName);
        messageView.setText(messageText);
        dateView.setText(mDate);
        timeView.setText(mTime);

        db.close();
    }
}

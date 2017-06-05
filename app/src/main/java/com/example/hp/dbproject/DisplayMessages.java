package com.example.hp.dbproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.dbproject.data.DbHelper;
import com.example.hp.dbproject.data.MessageCursorAdapter;
import com.example.hp.dbproject.data.ProfileCursorAdapter;
import com.example.hp.dbproject.data.TweetCursorAdapter;

public class DisplayMessages extends AppCompatActivity {
    int currentUserID;
    DbHelper mDbHelper = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        setContentView(R.layout.activity_display_messages);
        displayMessageList();
    }

    public void displayMessageList(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT message._id as _id, profile.first_name as first_name, profile.last_name as last_name, message.message_text as message_text, message.message_time as time, message.message_date as date FROM (profile JOIN message ON profile._id = message.sender_id) WHERE message.receiver_id = "+currentUserID+" ORDER BY message._id DESC;",null);

        ListView messageListView = (ListView) findViewById(R.id.messages);
        MessageCursorAdapter messageCursorAdapter = new MessageCursorAdapter(this,cursor);
        messageListView.setAdapter(messageCursorAdapter);

        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strId = Long.toString(id);
                Intent intent = new Intent(DisplayMessages.this, MessageDetails.class);
                intent.putExtra("messageID",id);
                intent.putExtra("currentUserID",currentUserID);
                startActivity(intent);
            }
        });
        db.close();
    }
}

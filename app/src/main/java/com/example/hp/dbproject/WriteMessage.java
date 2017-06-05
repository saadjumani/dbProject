package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;

import java.text.DateFormat;
import java.util.Date;

public class WriteMessage extends AppCompatActivity {
    int currentUserID;
    Long selectedUserID;
    DbHelper mDbHelper = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        selectedUserID = intent.getLongExtra("selectedUserID",-1);
        setContentView(R.layout.activity_write_message);

        if(selectedUserID != -1){
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM login WHERE _id = '"+selectedUserID+"';",null);
            if(cursor.moveToFirst()){
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                EditText usernameEditText = (EditText) findViewById(R.id.receiver_username);
                usernameEditText.setText(userName, TextView.BufferType.EDITABLE);
            }
        }

    }

    public  void onSend(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.receiver_username);
        EditText messageEditText = (EditText) findViewById(R.id.message_text);

        String rUserName = usernameEditText.getText().toString().trim();
        String msgText = messageEditText.getText().toString().trim();

        String currentDate = DateFormat.getDateInstance().format(new Date());
        String currentTime = DateFormat.getTimeInstance().format(new Date());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM login WHERE user_name = '"+rUserName+"';",null);
        if(cursor.moveToFirst()){
            int recieverID = cursor.getInt(cursor.getColumnIndex("_id"));

            SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("sender_id",currentUserID);
            values.put("receiver_id",recieverID);
            values.put("message_text",msgText);
            values.put("message_date", currentDate);
            values.put("message_time",currentTime);

            db1.insert("message",null,values);

            db1.close();
            Toast.makeText(this,"message Sent",Toast.LENGTH_SHORT);
            db.close();
            finish();
        }
        else{
            Toast.makeText(this,"invalid username",Toast.LENGTH_SHORT);
        }
    }

}

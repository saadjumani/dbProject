package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TweetPage extends AppCompatActivity {
    private EditText editTweet;
    private DbHelper mDbHelper = new DbHelper(this);
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_page);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        //EditText editTweet = (EditText) findViewById(R.id.editTweet);
        //Toast.makeText(this, "Error in saving like" + currentUserID, Toast.LENGTH_SHORT).show();

    }

    public void onTweetButton (View view){

        EditText tweetBox = (EditText) findViewById(R.id.editTweet);
        String tweet = tweetBox.getText().toString();

        String currentDate = DateFormat.getDateInstance().format(new Date());
        String currentTime = DateFormat.getTimeInstance().format(new Date());


        if(tweet.length() <= 200){
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("user_id",currentUserID);
            values.put("description",tweet);
            values.put("post_date",currentDate );
            values.put("post_time",currentTime );
            long rowID = db.insert("post",null,values);

            if(rowID == -1){
               Toast.makeText(this, "Error in insertion", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Value inserted at ID: "+rowID, Toast.LENGTH_SHORT).show();
            }

            db.close();

            finish();
        }
        else{
            Toast.makeText(this, "Error: Your tweet is longer than 200 characters",Toast.LENGTH_SHORT);
        }

    }

}

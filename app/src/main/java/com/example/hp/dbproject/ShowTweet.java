package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;

public class ShowTweet extends AppCompatActivity {

    Long rowID;
    int currentUserID;
    DbHelper mDbHelper = new DbHelper(this);
    int tweetLikes;
    int liked = 0;
    int likes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tweet);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
        {
            rowID= bd.getLong("RowID");
            currentUserID = bd.getInt("currentUserID");
        }
        displayTweet();
    }

    public  void displayTweet(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT profile._id as tweeter_id, profile.first_name, profile.last_name, post.description, post.post_time, post.post_date FROM profile JOIN post ON profile._id = post.user_id where post._id = "+rowID +" ;",null);
        cursor.moveToFirst();
        String tweetText = cursor.getString(cursor.getColumnIndex("description"));
        String tweeterName = cursor.getString(cursor.getColumnIndex("profile.first_name"));
        String tweetDate = cursor.getString(cursor.getColumnIndex("post.post_date"));
        String tweetTime = cursor.getString(cursor.getColumnIndex("post.post_time"));
        int tweeterID = cursor.getInt(cursor.getColumnIndex("tweeter_id"));

        Button delete = (Button) findViewById(R.id.delete);

        if(tweeterID == currentUserID){
            delete.setVisibility(View.VISIBLE);
        }
        //tweetLikes = cursor.getInt(cursor.getColumnIndex("tweet_likes"));

        Cursor likesCursor = db.rawQuery("select * from likes where post_id="+rowID+";",null);
        tweetLikes = likesCursor.getCount();

        TextView name = (TextView) findViewById(R.id.TweeterName);
        TextView text = (TextView) findViewById(R.id.TweetText);
        TextView likes = (TextView) findViewById(R.id.noOfLikes);
        TextView dateView = (TextView) findViewById(R.id.tweet_date);
        TextView timeView = (TextView) findViewById(R.id.tweet_time);

        name.setText(tweeterName);
        text.setText(tweetText);
        likes.setText(Integer.toString(tweetLikes));
        dateView.setText(tweetDate);
        timeView.setText(tweetTime);


        //String tweetText = cursor.getString(cursor.getColumnIndex("tweet_text"));
        //Toast.makeText(this, "Found Value: "+value, Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void updateLikes (View view){
        //verify that user has not liked this tweet before
        SQLiteDatabase countDb = mDbHelper.getReadableDatabase();

        Cursor cursor = countDb.rawQuery("select * from likes where post_id="+rowID+" AND user_id = "+currentUserID+";",null);

        if(cursor.getCount() >0){
            liked=1;
        }
        //long updatedRowID = 0;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsAffected = 0;
        if (liked == 0){
            //db.rawQuery("UPDATE tweets SET tweet_likes = tweet_likes + 1  WHERE _id = "+ rowID + ";",null);
            ContentValues likes = new ContentValues();
            tweetLikes++;
            likes.put("post_id",rowID);
            likes.put("user_id",currentUserID);
            db.insert("likes",null,likes);
            liked =1;
        }
        else{
            ContentValues likes = new ContentValues();
            tweetLikes--;
            likes.put("tweet_likes",tweetLikes);
            db.delete("likes", "post_id = "+rowID+" AND user_id = " + currentUserID, null);
            liked =0;
        }


        db.close();
        displayTweet();
    }

    public void onDelete(View view){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("post","_id = '"+rowID+"';",null);
        db.close();
        finish();
    }
}

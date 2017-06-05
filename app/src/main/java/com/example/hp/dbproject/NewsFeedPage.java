package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;
import com.example.hp.dbproject.data.TweetCursorAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static java.security.AccessController.getContext;

public class NewsFeedPage extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DbHelper mDbHelper = new DbHelper(this);
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_feed_page);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        //Toast.makeText(this, "Currently browsing: "+ currentUserID, Toast.LENGTH_SHORT).show();
        displayTweets();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onSeeMessages(View view){
        Intent intent = new Intent(this, DisplayMessages.class);
        intent.putExtra("currentUserID",currentUserID);
        startActivity(intent);
    }
    public void onTweetButton(View view) {
        Intent intent = new Intent(this, TweetPage.class);
        intent.putExtra("currentUserID",currentUserID);
        startActivity(intent);

    }

    public void onFollowPeopleButton(View view) {
        Intent intent = new Intent(this, FollowPage.class);
        intent.putExtra("currentUserID",currentUserID);
        startActivity(intent);
    }

    public void displayTweets(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT post._id as _id, profile.first_name as fName, profile.last_name, post.description as desc, post.post_time as time, post.post_date as date FROM (profile JOIN post ON profile._id = post.user_id) JOIN follow ON follow.id_followee = profile._id WHERE follow.id_follower = "+currentUserID+";",null);

        ListView tweetListView = (ListView) findViewById(R.id.list);
        TweetCursorAdapter tweetCursorAdapter = new TweetCursorAdapter(this,cursor);
        tweetListView.setAdapter(tweetCursorAdapter);

        tweetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strId = Long.toString(id);
                Intent intent = new Intent(NewsFeedPage.this, ShowTweet.class);
                intent.putExtra("RowID",id);
                intent.putExtra("currentUserID",currentUserID);
                startActivity(intent);
            }
        });
        db.close();
    }
    public void insertData(View view){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tweeter_id",1);
        values.put("tweet_text","Just testing to see if there is a crash");

        long rowID = db.insert("tweets",null,values);

        if(rowID == -1){
            Toast.makeText(this, "Error in insertion", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Value inserted at ID: "+rowID, Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("NewsFeedPage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        displayTweets();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

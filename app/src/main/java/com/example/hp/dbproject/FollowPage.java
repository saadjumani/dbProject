package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;
import com.example.hp.dbproject.data.ProfileCursorAdapter;
import com.example.hp.dbproject.data.TweetCursorAdapter;

public class FollowPage extends AppCompatActivity {
    private EditText editTweet;
    private int currentUserID;
    private DbHelper mDbHelper = new DbHelper(this);
    int num = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_page);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        displayProfiles();
    }

    public void displayProfiles(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profile;",null);

        ListView peopleListView = (ListView) findViewById(R.id.people_list);
        //cursor.moveToFirst();
        ProfileCursorAdapter profileCursorAdapter = new ProfileCursorAdapter(this,cursor);
        peopleListView.setAdapter(profileCursorAdapter);

        peopleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strId = Long.toString(id);
                Intent intent = new Intent(FollowPage.this, ShowProfileDetailed.class);

                intent.putExtra("currentUserID",currentUserID);
                intent.putExtra("selectedUserID",id);

                startActivity(intent);
            }
        });
    }





    protected void onFollowButton(View view){
        EditText userNameEdit = (EditText) findViewById(R.id.followee_username);
        String followeeUsername = userNameEdit.getText().toString();
        SQLiteDatabase db1 = mDbHelper.getReadableDatabase();
        Cursor c1 = db1.rawQuery("SELECT _id FROM login WHERE user_name = '"+followeeUsername+"';",null);
        int followeeID = -2;
        if(c1.moveToFirst()){
            followeeID = c1.getInt(c1.getColumnIndex("_id"));
        }
        else {
            followeeID = -1;
        }
        c1 = db1.rawQuery("SELECT * FROM follow WHERE id_followee = '" +followeeID + "' AND id_follower = '"+ currentUserID + "' ;",null);
        int rowCount = c1.getCount();
        db1.close();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        if(followeeID != -1 && rowCount == 0){
            ContentValues values = new ContentValues();
            values.put("id_followee",followeeID);
            values.put("id_follower",currentUserID);
            Long insertedRowID = db.insert("follow",null,values);
            db.close();
            if(insertedRowID == -1){
                Toast.makeText(this, "following faliure", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "followed successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        }
        else{
            Toast.makeText(this, "invalid username or already followed", Toast.LENGTH_SHORT).show();
        }

    }
}

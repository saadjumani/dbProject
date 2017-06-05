package com.example.hp.dbproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;

public class ShowProfileDetailed extends AppCompatActivity {
    private int currentUserID;
    private Long selectedUserID;
    private DbHelper mDbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUserID = intent.getIntExtra("currentUserID",-1);
        selectedUserID = intent.getLongExtra("selectedUserID",-2);
        setContentView(R.layout.activity_show_profile_detailed);
        DisplayInfo();
    }

    public void DisplayInfo(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM profile WHERE _id = "+selectedUserID+";",null);
        cursor.moveToFirst();

        String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
        String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
        String DoB = cursor.getString(cursor.getColumnIndex("birth_date"));
        String gender = cursor.getString(cursor.getColumnIndex("gender"));
        String city = cursor.getString(cursor.getColumnIndex("city"));
        String country = cursor.getString(cursor.getColumnIndex("country"));
        String email = cursor.getString(cursor.getColumnIndex("email"));

        String fullName = firstName + " " + lastName;

        TextView nameView = (TextView) findViewById(R.id.Name);
        TextView DoBView = (TextView) findViewById(R.id.dob);
        TextView genderView = (TextView) findViewById(R.id.gender);
        TextView cityView = (TextView) findViewById(R.id.city);
        TextView countryView = (TextView) findViewById(R.id.country);
        TextView emailView = (TextView) findViewById (R.id.email);

        nameView.setText(fullName);
        DoBView.setText(DoB);
        genderView.setText(gender);
        cityView.setText(city);
        countryView.setText(country);
        emailView.setText(email);
    }

    public void onSendMessage(View view){
        Intent intent = new Intent(this, WriteMessage.class);
        intent.putExtra("currentUserID",currentUserID);
        intent.putExtra("selectedUserID",selectedUserID);
        startActivity(intent);
    }

    public void  onFollowUser(View view){

        //verify that this record does not already exist
        SQLiteDatabase dbx = mDbHelper.getReadableDatabase();
        Cursor c = dbx.rawQuery("SELECT * FROM follow WHERE id_followee = '" +selectedUserID + "' AND id_follower = '"+ currentUserID + "' ;", null);

        int rowCount = c.getCount();

        if(rowCount==0){
            int followeeID = selectedUserID.intValue();
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            if(followeeID != -1){
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
                Toast.makeText(this, "invalid username/ID", Toast.LENGTH_SHORT).show();

            }
        }

        else {
            Toast.makeText(this, "Unfollowed", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete("follow","id_follower = '"+currentUserID+"' AND id_followee='"+selectedUserID+"'",null);
            db.close();
        }

    }
}


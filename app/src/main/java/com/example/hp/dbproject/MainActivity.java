package com.example.hp.dbproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;

public class MainActivity extends AppCompatActivity {
    DbHelper mDbHelper;
    int currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DbHelper(this);
    }

    public  void openNewsFeed(){
        Intent intent = new Intent(this,NewsFeedPage.class);
        intent.putExtra("currentUserID",currentUserID);
        startActivity(intent);
    }

    public void onLoginButton(View view){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int valid = 0;
        EditText emailEditText = (EditText) findViewById(R.id.loginEmail);
        EditText passEditText = (EditText) findViewById(R.id.loginPass);

        String username = emailEditText.getText().toString().trim();
        String pass = passEditText.getText().toString().trim();

        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE user_name = '"+username+"' AND password = '"+pass+"';",null);
        if(cursor.getCount() > 0){
            valid =1;
            cursor.moveToFirst();
            currentUserID = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        else {
            valid =0;
        }
        //validation code goes here

        if(valid==1){
            openNewsFeed();
        }
        else {
            Toast.makeText(this, "Validation failed, can't open news feed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignupButton(View view){
        Intent intent = new Intent(this,SignupPage.class);
        startActivity(intent);
    }
}

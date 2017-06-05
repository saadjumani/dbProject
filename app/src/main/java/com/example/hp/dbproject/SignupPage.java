package com.example.hp.dbproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.dbproject.data.DbHelper;

public class SignupPage extends AppCompatActivity {

    DbHelper mDbHelper ;

    EditText editFirstName;
    EditText editLastName;
    EditText editGender;
    EditText editDOB;
    EditText editCity;
    EditText editCountry;
    EditText editUsername;
    EditText editEmail;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        mDbHelper = new DbHelper(this);

        editFirstName = (EditText) findViewById(R.id.firstName);
        editLastName = (EditText) findViewById(R.id.lastName);
        editGender=(EditText) findViewById(R.id.gender);
        editDOB = (EditText) findViewById(R.id.age);

        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        editCity = (EditText) findViewById(R.id.city);
        editCountry = (EditText) findViewById(R.id.country);
        editUsername = (EditText) findViewById(R.id.username);

    }

    public void onSignUpButton(View view){
        //get all relevant values from EditText views
        String DOB;
        String nullString ="";
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String gender = editGender.getText().toString().trim().toLowerCase();
        String country = editCountry.getText().toString().trim().toLowerCase();
        String username = editUsername.getText().toString();
        String city = editCity.getText().toString().trim().toLowerCase();
        DOB = editDOB.getText().toString().trim();

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString();
        //validation of values
        if(firstName.length() > 0 && lastName.length() > 0 && gender.length() > 0 && DOB.length() > 0 && email.length() > 0 && password.length() > 7){
            if(email.contains("@")){
                // insert the data
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("first_name",firstName);
                values.put("last_name",lastName);
                values.put("email",email);
                values.put("birth_date",DOB);
                values.put("gender",gender);
                values.put("country",country);
                values.put("city",city);
                values.put("description", "DescTesting");
                //values.put("last_name",lastName);
                ContentValues loginValues = new ContentValues();
                //loginValues.put("email",email);
                loginValues.put("password",password);
                loginValues.put("user_name",username);

                ContentValues userValues = new ContentValues();


                long rowID = db.insert("profile",null,values);
                long rowID2 = db.insert("login",null, loginValues);
                if(rowID == -1){
                    Toast.makeText(this, "Error in user profile info. Please make sure you don't already have an account with this email", Toast.LENGTH_SHORT).show();
                }
                else if(rowID2 == -1){
                    Toast.makeText(this, "Error in authentication info", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Account created successfully: ", Toast.LENGTH_SHORT).show();
                    userValues.put("login_id",rowID2);
                    userValues.put("profile_id",rowID);
                    db.insert("user",null, userValues);
                    finish();
                    db.close();
                }
            }
            else {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Error: please ensure all the given details are correct", Toast.LENGTH_SHORT).show();
        }

        //insert them into database

    }
}

package com.example.hp.dbproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 5/23/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blog.db";
    private static int DATABASE_VERSION = 1;

    public DbHelper (Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");

        String SQL_CREATE_PROFILE_TABLE = "create table profile(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT," +
                "email TEXT NOT NULL UNIQUE," +
                "birth_date TEXT," +
                "gender TEXT," +
                "city TEXT," +
                "country TEXT," +
                "description TEXT" +
                ");";

        String SQL_CREATE_LOGIN_TABLE = "create table login(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "user_name TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL" +
                ");";

        String SQL_CREATE_USERS_TABLE = "create table user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "login_id int NOT NULL," +
                "profile_id int NOT NULL," +
                "FOREIGN KEY(login_id) REFERENCES login(login_id)," +
                "FOREIGN KEY(profile_id) REFERENCES profile(profile_id)" +
                ");";



        String SQL_CREATE_FOLLOW_TABLE = "create table follow(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "id_followee int NOT NULL," +
                "id_follower int NOT NULL," +
                "FOREIGN KEY(id_followee) REFERENCES user(user_id)," +
                "FOREIGN KEY(id_follower) REFERENCES user(user_id)" +
                ");";

        String SQL_CREATE_POST_TABLE = "create table post(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "user_id int NOT NULL," +
                "post_date TEXT NOT NULL," +
                "post_time TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id)" +
                ");";

        String SQL_CREATE_LIKES_TABLE = "create table likes(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "post_id int NOT NULL," +
                "user_id int NOT NULL," +
                "FOREIGN KEY(post_id) REFERENCES post(post_id)  ON DELETE CASCADE ," +
                "FOREIGN KEY(user_id) REFERENCES user(user_id)  ON DELETE CASCADE" +
                ");";

        String SQL_CREATE_MESSAGES_TABLE  = "create table message(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "sender_id int NOT NULL," +
                "receiver_id int NOT NULL," +
                "message_text TEXT NOT NULL," +
                "message_date TEXT NOT NULL," +
                "message_time TEXT NOT NULL," +
                "FOREIGN KEY(sender_id) REFERENCES user(user_id)," +
                "FOREIGN KEY(receiver_id) REFERENCES user(user_id)" +
                ");";



        db.execSQL(SQL_CREATE_PROFILE_TABLE);
        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_FOLLOW_TABLE);
        db.execSQL(SQL_CREATE_POST_TABLE);
        db.execSQL(SQL_CREATE_LIKES_TABLE);
        db.execSQL(SQL_CREATE_MESSAGES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

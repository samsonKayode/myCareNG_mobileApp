package com.oxfordplus.hypen.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDatabase extends SQLiteOpenHelper {

    public static String DATABASE_NAME="MY_CARE_NG.db";
    private static int DATABASE_VERSION = 2;


    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String QUERY1 = "CREATE TABLE IF NOT EXISTS PROFILE (FIRSTNAME TEXT, LASTNAME, DOB DATETIME, SEX TEXT, CITY " +
                "TEXT, STATE TEXT, EMAIL TEXT, PHONE TEXT, TYPE TEXT DEFAULT 'OWN', PASSKEY TEXT, PASSWORD TEXT);";

        sqLiteDatabase.execSQL(QUERY1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PROFILE");


        onCreate(sqLiteDatabase);

    }

    public Cursor confirmData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM PROFILE", null);

        return res;
    }


    public Cursor validateCode(String code){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cr = db.rawQuery("SELECT * FROM PROFILE WHERE PASSKEY = ?", new String[]{code});

        return cr;
    }


    public String insertProfile(String firstName, String lastName, String dob, String sex,
                                String phone, String city, String state, String email, String password, String passkey){

        SQLiteDatabase db = this.getWritableDatabase();
        String a=null;

        try{

            ContentValues values = new ContentValues();

            values.put("FIRSTNAME", firstName);
            values.put("LASTNAME", lastName);
            values.put("DOB", dob);
            values.put("SEX", firstName);
            values.put("CITY", firstName);
            values.put("STATE", firstName);
            values.put("EMAIL", firstName);
            values.put("PHONE", firstName);
            values.put("CODE", firstName);
            values.put("PASSWORD", firstName);

            long result = db.insert("PROFILE", null, values);

            if(result>0){

                a = "OK";
            }else{
                a = "NOT OK";
            }

        }catch(Exception nn){

            a = "ERROR";
        }

        return a;

    }

    public Cursor verifyLogin(String username, String password){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT * FROM PROFILE WHERE EMAIL=? and PASSWORD=?", new String[]{username, password});

        return cursor;
    }

}

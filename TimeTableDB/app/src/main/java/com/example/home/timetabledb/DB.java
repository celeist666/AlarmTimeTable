package com.example.home.timetabledb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 2016-12-03.
 */
public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "timetable5.db";
    public static final String TABLE_NAME = "timetable";
    public static final String COLUMN_ID = "_id";
    public static final String COULUMN_SUBJECT = "subject";
    public static final String COULUMN_TIME = "starttime";
    public static final String COULUMN_TIMELENGTH = "timelength";
    public static final String COULUMN_DAY = "day";
    public static final String COULUMN_COLOR = "color";
    public static final String COULUMN_BTNID = "btnid";

    public DB(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table btntable" + "(_id integer primary key autoincrement, btnid text)");
        db.execSQL("create table timetable" + "(_id integer primary key autoincrement, subject text, starttime text, timelength text, day text, color text, btnid text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insert(String subject, String starttime, String timelength, String day, String color, String btnid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put("starttime", starttime);
        contentValues.put("timelength", timelength);
        contentValues.put("day", day);
        contentValues.put("color", color);
        contentValues.put("btnid", btnid);

        db.insert("timetable", null, contentValues);
        return true;
    }

    public boolean btninsert(String btnid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("btnid", btnid);

        db.insert("btntable", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from timetable where id="+id+"", null );
        return res;
    }

    public Integer delete(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("timeteble",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}

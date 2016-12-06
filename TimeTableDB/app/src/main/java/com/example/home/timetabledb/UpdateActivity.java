package com.example.home.timetabledb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    Button btn_update;
    Button btn_delete;
    Spinner spn_starttime;
    Spinner spn_timelength;
    Spinner spn_day;
    EditText edt_sub;

    private final int DYNAMIC_VIEW_ID = 0x8000;

    String[] starttime = {"1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시"};
    String[] timelength = {"1시간", "2시간", "3시간", "4시간"};
    String[] day = {"월", "화", "수", "목", "금"};

    LinearLayout color;
    String colors = "";

    String getbtnid;

    DB helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        DB helper = new DB(getApplicationContext());
        final SQLiteDatabase db = helper.getWritableDatabase();

        Intent i = getIntent();
        getbtnid = i.getStringExtra("btnid");

        Cursor item = db.rawQuery("SELECT * FROM timetable where btnid = " + getbtnid, null);
        item.moveToFirst();

        btn_update = (Button) findViewById(R.id.Update_btn_update);
        btn_delete = (Button) findViewById(R.id.Update_btn_delete);
        edt_sub = (EditText) findViewById(R.id.Update_edit_subject);
        spn_starttime = (Spinner) findViewById(R.id.Update_spinner_starttime);
        spn_timelength = (Spinner) findViewById(R.id.Update_spinner_timelength);
        spn_day = (Spinner) findViewById(R.id.Update_spinner_day);

//        ArrayAdapter<String> starttimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, starttime);
//        ArrayAdapter<String> timelengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timelength);
//        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day);
        SpinnerAdapter starttimeAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, starttime);
        SpinnerAdapter timelengthAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, timelength);
        SpinnerAdapter dayAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, day);
        spn_starttime.setAdapter(starttimeAdapter);
        spn_timelength.setAdapter(timelengthAdapter);
        spn_day.setAdapter(dayAdapter);

        edt_sub.setText(item.getString(1));
        for(int z = 0; z < starttime.length; z++){
            if(item.getString(2) == starttime[z]){
                spn_starttime.setSelection(z);
            }
        }
        for(int z = 0; z < timelength.length; z++){
            if(item.getString(3) == timelength[z]){
                spn_timelength.setSelection(z);
            }
        }
        for(int z = 0; z < day.length; z++){
            if(item.getString(4) == day[z]){
                spn_day.setSelection(z);
            }
        }

        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db.execSQL("update timetable set subject = '" + edt_sub.getText().toString() + "' where btnid = '" + getbtnid + "';");
                db.execSQL("update timetable set starttime = '" + spn_starttime.getSelectedItem().toString() + "' where btnid = '" + getbtnid + "';");
                db.execSQL("update timetable set timelength = '" + spn_timelength.getSelectedItem().toString() + "' where btnid = '" + getbtnid + "';");
                db.execSQL("update timetable set day = '" + spn_day.getSelectedItem().toString() + "' where btnid = '" + getbtnid + "';");
                Log.i("update", "Complete");
                Toast.makeText(UpdateActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();

                db.close();
                Intent i = new Intent(getApplicationContext(), TimetableActivity.class);
                TimetableActivity activity = new TimetableActivity();
                activity.Finish();
                startActivity(i);
                finish();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db.delete("timetable", "btnid" + "='" + getbtnid + "'", null);
                Log.i("delete", "Complete");
                Toast.makeText(UpdateActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();

                db.close();
                finish();
            }
        });
    }
}
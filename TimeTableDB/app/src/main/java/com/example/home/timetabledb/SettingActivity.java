package com.example.home.timetabledb;

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

public class SettingActivity extends AppCompatActivity {

    Button btn_save;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_save = (Button) findViewById(R.id.Setting_btn_save);
        edt_sub = (EditText) findViewById(R.id.Setting_edit_subject);
        spn_starttime = (Spinner) findViewById(R.id.Setting_spinner_starttime);
        spn_timelength = (Spinner) findViewById(R.id.Setting_spinner_timelength);
        spn_day = (Spinner) findViewById(R.id.Setting_spinner_day);

//        ArrayAdapter<String> starttimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, starttime);
//        ArrayAdapter<String> timelengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timelength);
//        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day);
        SpinnerAdapter starttimeAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, starttime);
        SpinnerAdapter timelengthAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, timelength);
        SpinnerAdapter dayAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, day);
        spn_starttime.setAdapter(starttimeAdapter);
        spn_timelength.setAdapter(timelengthAdapter);
        spn_day.setAdapter(dayAdapter);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DB db = new DB(getApplicationContext());
                SQLiteDatabase db2 = db.getWritableDatabase();

                if(edt_sub.getText().toString().equals("")){
                    Toast.makeText(SettingActivity.this, "과목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else{
                    db.btninsert("zz");
                    Cursor res =  db2.rawQuery( "select * from btntable", null );
                    res.moveToLast();
                    int btnid = res.getInt(0) + DYNAMIC_VIEW_ID;
                    db.insert(edt_sub.getText().toString(), spn_starttime.getSelectedItem().toString(), spn_timelength.getSelectedItem().toString(), spn_day.getSelectedItem().toString(), colors, Integer.toString(btnid));
                    Toast.makeText(SettingActivity.this, "등록 완료.", Toast.LENGTH_SHORT).show();
                    edt_sub.setText("");
                    Log.i("setting", "setting");
                }
                db.close();
                db2.close();
            }
        });
    }
}
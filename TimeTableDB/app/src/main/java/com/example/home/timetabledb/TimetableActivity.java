package com.example.home.timetabledb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {

    RelativeLayout relMon;
    RelativeLayout relTue;
    RelativeLayout relWed;
    RelativeLayout relThu;
    RelativeLayout relFri;

    private final int DYNAMIC_VIEW_ID = 0x8000;

    String margin;
    String height;

    public void Finish(){
        finish();
    }

    public String getHeight(String timelength){

        String height = "0";
        String[] strtimelength = {"1시간", "2시간", "3시간", "4시간"};
        int time = 0;
        if(timelength.equals(strtimelength[0])){
            time = 1;
        } else if(timelength.equals(strtimelength[1])){
            time = 2;
        } else if(timelength.equals(strtimelength[2])){
            time = 3;
        } else if(timelength.equals(strtimelength[3])){
            time = 4;
        }

        switch(time){
            case 1:
                height = "105";
                break;
            case 2:
                height = "210";
                break;
            case 3:
                height = "315";
                break;
            case 4:
                height = "420";
                break;
        }
        return height;
    }

    public String getTopMargin(String starttime){

        String size = "0";
        String[] strtime = {"1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시"};
        int time = 0;
        if(starttime.equals(strtime[0])){
            time = 1;
        } else if(starttime.equals(strtime[1])){
            time = 2;
        } else if(starttime.equals(strtime[2])){
            time = 3;
        } else if(starttime.equals(strtime[3])){
            time = 4;
        } else if(starttime.equals(strtime[4])){
            time = 5;
        } else if(starttime.equals(strtime[5])){
            time = 6;
        } else if(starttime.equals(strtime[6])){
            time = 7;
        } else if(starttime.equals(strtime[7])){
            time = 8;
        } else if(starttime.equals(strtime[8])){
            time = 9;
        }
        switch(time){
            case 1:
                size = "0";
                break;
            case 2:
                size = "105";
                break;
            case 3:
                size = "210";
                break;
            case 4:
                size = "315";
                break;
            case 5:
                size = "420";
                break;
            case 6:
                size = "525";
                break;
            case 7:
                size = "630";
                break;
            case 8:
                size = "735";
                break;
            case 9:
                size = "840";
                break;
        }
        return size;
    }

    public Button getButtonToLayout(int height, int marginTop,
                                    String buttonText, int id){
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, height);
        Button button = new Button(getApplicationContext());
        button.setLayoutParams(params);
        button.setText(buttonText);
        button.setTextSize(9);
        button.setOnClickListener(buttonOnClick);
        button.setId(id);
        button.setBackgroundColor(Color.rgb(255, 187, 0));
        button.setTextColor(Color.rgb(37, 37, 37));
        params.setMargins(0, marginTop, 0, 0);

        return button;
    }

    public View.OnClickListener buttonOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Button b = (Button) v;
            DB helper = new DB(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor item = db.rawQuery("SELECT * FROM timetable where btnid = " + b.getId(), null);
            item.moveToFirst();
            Log.i("진입", "진입");
            Log.i("b.getid()", Integer.toString(b.getId()));
            Log.i("과목", item.getString(1));
            Log.i("교시", item.getString(2));
            Log.i("시간", item.getString(3));
            Log.i("버튼ID", item.getString(6));
            CustumDialog custumdialog = new CustumDialog(TimetableActivity.this, item.getString(1), item.getString(2), item.getString(3), Integer.parseInt(item.getString(6)));
            Log.i("완료", "완료");
            custumdialog.show();
            db.close();
//            http://sharepid.tistory.com/988
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        relMon = (RelativeLayout) findViewById(R.id.relativeLayoutMonday);
        relTue = (RelativeLayout) findViewById(R.id.relativeLayoutTueDay);
        relWed = (RelativeLayout) findViewById(R.id.relativeLayoutWedDay);
        relThu = (RelativeLayout) findViewById(R.id.relativeLayoutThuDay);
        relFri = (RelativeLayout) findViewById(R.id.relativeLayoutFriDay);

        DB helper = new DB(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        SubjectInfo subinfo = new SubjectInfo();
        Cursor cursor = db.rawQuery("SELECT * FROM timetable ", null);

        ArrayList<SubjectInfo> subinfolist = new ArrayList<SubjectInfo>();

        ArrayList<String> idList = new ArrayList<String>();
        ArrayList<String> dayList = new ArrayList<String>();
        ArrayList<String> subList = new ArrayList<String>();
        ArrayList<String> starttimeList = new ArrayList<String>();
        ArrayList<String> timelengthList = new ArrayList<String>();
        ArrayList<String> colorsList = new ArrayList<String>();
        ArrayList<String> btnidList = new ArrayList<String>();

        Log.i("ID count", Integer.toString(cursor.getCount()));
        if(cursor.moveToFirst()){
            do{
                Log.i("ID", Integer.toString(cursor.getInt(0)));
                Log.i("Day", cursor.getString(4));
                idList.add(Integer.toString(cursor.getInt(0)));
                dayList.add(cursor.getString(4));
                subList.add(cursor.getString(1));
                starttimeList.add(cursor.getString(2));
                timelengthList.add(cursor.getString(3));
                colorsList.add(cursor.getString(5));
                btnidList.add(cursor.getString(6));
            } while(cursor.moveToNext());
        }

        for(int i = 0; i < idList.size(); i++){
            if(dayList.get(i).equals("월")){
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.FILL_PARENT, Integer.parseInt(getHeight(timelengthList.get(i))));
//                Button subView = new Button(this);
//                subView.setLayoutParams(params);
//                subView.setId(DYNAMIC_VIEW_ID + Integer.parseInt(idList.get(i)));
//                subView.setText(subList.get(i));
//                subView.setOnClickListener(buttonOnClick);
//                params.setMargins(0, Integer.parseInt(getTopMargin(starttimeList.get(i))), 0, 0);
//                relMon.addView(subView);
                relMon.addView(getButtonToLayout(Integer.parseInt(getHeight(timelengthList.get(i))), Integer.parseInt(getTopMargin(starttimeList.get(i))), subList.get(i), Integer.parseInt(btnidList.get(i))));

                Log.i("월 Button ID", btnidList.get(i));
            }
            if(dayList.get(i).equals("화")){
                int id = DYNAMIC_VIEW_ID + Integer.parseInt(idList.get(i));
                relTue.addView(getButtonToLayout(Integer.parseInt(getHeight(timelengthList.get(i))), Integer.parseInt(getTopMargin(starttimeList.get(i))), subList.get(i), Integer.parseInt(btnidList.get(i))));

                Log.i("화 Button ID", btnidList.get(i));
            }
            if(dayList.get(i).equals("수")){
                int id = DYNAMIC_VIEW_ID + Integer.parseInt(idList.get(i));
                relWed.addView(getButtonToLayout(Integer.parseInt(getHeight(timelengthList.get(i))), Integer.parseInt(getTopMargin(starttimeList.get(i))), subList.get(i), Integer.parseInt(btnidList.get(i))));

                Log.i("수 Button ID", btnidList.get(i));
            }
            if(dayList.get(i).equals("목")){
                int id = DYNAMIC_VIEW_ID + Integer.parseInt(idList.get(i));
                relThu.addView(getButtonToLayout(Integer.parseInt(getHeight(timelengthList.get(i))), Integer.parseInt(getTopMargin(starttimeList.get(i))), subList.get(i), Integer.parseInt(btnidList.get(i))));

                Log.i("목 Button ID", btnidList.get(i));
            }
            if(dayList.get(i).equals("금")){
                int id = DYNAMIC_VIEW_ID + Integer.parseInt(idList.get(i));
                relFri.addView(getButtonToLayout(Integer.parseInt(getHeight(timelengthList.get(i))), Integer.parseInt(getTopMargin(starttimeList.get(i))), subList.get(i), Integer.parseInt(btnidList.get(i))));

                Log.i("금 Button ID", btnidList.get(i));
            }
        }

        db.close();
    }
}

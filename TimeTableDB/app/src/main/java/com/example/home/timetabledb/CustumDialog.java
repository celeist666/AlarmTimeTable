package com.example.home.timetabledb;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Home on 2016-12-04.
 */
public class CustumDialog extends Dialog{

    private TextView sub, time, timelength;
    private Button edit, close;

    Context context;
    String gsub, gtime, gtimelength;
    int btnid;

    public CustumDialog(Context context, String sub, String time, String timelength, int btnid){
        super(context);
        this.context = context;
        this.gsub = sub;
        this.gtime = time;
        this.gtimelength = timelength;
        this.btnid = btnid;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dialog_view);

        sub = (TextView) findViewById(R.id.dialogview_subject);
        time = (TextView) findViewById(R.id.dialogview_time);
        timelength = (TextView) findViewById(R.id.dialogview_timelength);
        edit = (Button) findViewById(R.id.dialogview_edit);
        close = (Button) findViewById(R.id.dialogview_close);

        sub.setText(gsub);
        time.setText(gtime);
        timelength.setText(gtimelength);

        edit.setOnClickListener(buttonOnClick);
        close.setOnClickListener(buttonOnClick);
    }

    public View.OnClickListener buttonOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            DB helper = new DB(context);
            SQLiteDatabase db = helper.getWritableDatabase();

            if(v == edit){
                Log.i("editbtn", "start");
                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("btnid", Integer.toString(btnid));
                context.startActivity(i);
                CustumDialog.this.dismiss();
            } else{
                Log.i("closebtn", "start");
                CustumDialog.this.dismiss();
            }

            db.close();
//            http://sharepid.tistory.com/988
        }
    };

//    @Override
//    public boolean onTouch(View v, MotionEvent event){
//
//        DB helper = new DB(context);
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        if(v == del){
//            Log.i("delbtn", "start");
//            db.delete("timetable", "btnid" + "='" + btnid+"'", null);
//        } else if(v == edit){
//            Log.i("editbtn", "start");
//            Toast.makeText(context, "Edit 만드는중", Toast.LENGTH_SHORT).show();
//        } else{
//            Log.i("closebtn", "start");
//            Toast.makeText(context, "Cancel 만드는중", Toast.LENGTH_SHORT).show();
//        }
//
//        db.close();
//        return false;
//    }
}

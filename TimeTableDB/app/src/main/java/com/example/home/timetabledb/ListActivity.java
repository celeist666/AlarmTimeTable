package com.example.home.timetabledb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListActivity extends AppCompatActivity {

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final ListView list = (ListView) findViewById(R.id.ListView1);
        DB helper = new DB(getApplicationContext());
        final SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM timetable ", null);
        String[] data_columns = new String[] {"subject", "starttime", "timelength", "day"};
        int[] widgets = new int[] {R.id.view1, R.id.view2, R.id.view3, R.id.view4};

        if (cursor != null ){
            startManagingCursor(cursor);//초기화

            adapter = new SimpleCursorAdapter(this, R.layout.dbview, cursor, data_columns, widgets);

            list.setAdapter(adapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor item = (Cursor) list.getAdapter().getItem(position);
                int _id = item.getInt(item.getColumnIndex("_id"));
                db.delete("timetable","_id" + "='" +_id+"'" , null);
                Log.i("del", "del");
            }
        });

        db.close();
    }
}

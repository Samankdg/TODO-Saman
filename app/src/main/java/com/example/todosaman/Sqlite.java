package com.example.todosaman;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite extends SQLiteOpenHelper {

    private static final String dbname = "todo.db";

    public Sqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry="create table todo(id integer primary key autoincrement, titleTODO, descTODO, dateTODO)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(db);
    }

    public String addRecord(String p1, String p2, String p3) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("titleTOO", p1);
        cv.put("descTODO", p2);
        cv.put("dateTODO", p3);

        long res= db.insert("tbl todo", null, cv);
        if (res==-1)
            return "Failed";
        else
            return "Successfully Inserted";
    }
}

package com.example.timur.organizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Timur on 4/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "OrganizerDB";
    static final int DATABASE_VERSION = 1;
    static final int NAME_COLUMN = 1;
    private static final String CREATE_USER_TABLE = "CREATE TABLE "+"User "
            +"( "+"UserId INTEGER PRIMARY KEY AUTOINCREMENT, "+"Username TEXT, "+"Login TEXT, "
            +"Password text);";
    private static final String CREATE_TASK_TABLE = "create table Task ( "
            +"TaskId integer primary key autoincrement, "+"Description text, "+"FromDate text, "+"ToDate integer);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+"User");
        db.execSQL("DROP TABLE IF EXISTS "+"Colormatch");
        db.execSQL("DROP TABLE IF EXISTS "+"Raingame");
        db.execSQL("DROP TABLE IF EXISTS "+"Cardgame");
        onCreate(db);
    }
}

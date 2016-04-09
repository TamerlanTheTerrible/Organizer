package com.example.timur.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timur on 4/8/2016.
 */
public class DBService {
    private SQLiteDatabase db;
    private final Context context;
    private DBHelper dbHelper;
    public  DBService(Context _context){
        context =_context;
        dbHelper = new DBHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
    }

    public DBService open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public void register(String username, String login, String password){
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Login", login);
        cv.put("Password", password);
        db.insert("User", null, cv);
    }

    public boolean login(String login, String password){
        boolean bool=false;
        Cursor c = db.query("User", null, "Username = ?", new String[]{login}, null, null, null);
        if(c.moveToFirst()){
            int pswrdCol = c.getColumnIndex("Password");
            do{
                if(password.equals(c.getString(pswrdCol))){
                    bool = true;
                }
            }while (c.moveToNext());
        }
        return bool;
    }

    public void addTask(String task, String fromDate, String toDate){
        ContentValues cv = new ContentValues();
        cv.put("Description", task);
        cv.put("FromDate", fromDate);
        cv.put("ToDate", toDate);
        db.insert("Task", null, cv);
    }

    public Task getTask(String desc){
        Task task=null;
        Cursor c = db.query("Task", null, "Description = ?", new String[]{desc}, null, null, null);
        if(c.moveToFirst()){
            int taskCol = c.getColumnIndex("Description");
            int toCol = c.getColumnIndex("ToDate");
            int fromCol = c.getColumnIndex("FromDate");
            do{
                String description = c.getString(taskCol);
                String start = c.getString(fromCol);
                String finish = c.getString(toCol);
                task = new Task(description, start, finish);

            }while (c.moveToNext());
        }
        return task;
    }

/*    public void updateTask(String task, String fromDate, String toDate, String initialTask){
        ContentValues cv = new ContentValues();
        cv.put("Description", task);
        cv.put("FromDate", fromDate);
        cv.put("ToDate", toDate);
        db.update("Task", cv, "Description = ?", new String[]{initialTask});
    }*/

    public List<String> getAllDates(){
        List<String> dates = new ArrayList<String>();
        Cursor c = db.query("Task", null,  null, null, null, null, null);
        if(c.moveToFirst()){
            int toDateCol = c.getColumnIndex("ToDate");
            do{
                dates.add(c.getString(toDateCol));
            }while (c.moveToNext());
        }
        return  dates;
    }

    public List<String> getTasks(String date){
        List<String> tasks = new ArrayList<String>();
        Cursor c = db.query("Task", null,  "ToDate = ?", new String[]{date}, null, null, null);
        if(c.moveToFirst()){
            int toTaskCol = c.getColumnIndex("Description");
            do{
                tasks.add(c.getString(toTaskCol));
            }while (c.moveToNext());
        }
        return  tasks;
    }

    public List<String> getStartDates(String date){
        List<String> dates = new ArrayList<String>();
        Cursor c = db.query("Task", null,  "ToDate = ?", new String[]{date}, null, null, null);
        if(c.moveToFirst()){
            int toFromCol = c.getColumnIndex("FromDate");
            do{
                dates.add(c.getString(toFromCol));
            }while (c.moveToNext());
        }
        return  dates;
    }

    public List<String> getFinishDates(String date){
        List<String> dates = new ArrayList<String>();
        Cursor c = db.query("Task", null,  "ToDate = ?", new String[]{date}, null, null, null);
        if(c.moveToFirst()){
            int toToCol = c.getColumnIndex("ToDate");
            do{
                dates.add(c.getString(toToCol));
            }while (c.moveToNext());
        }
        return  dates;
    }

}

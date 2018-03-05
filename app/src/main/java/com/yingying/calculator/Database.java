package com.yingying.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Yingying Xia on 2016/6/1.
 */
public class Database extends SQLiteOpenHelper {
    public static final int database_version = 1;

    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLENAME + " (" + TableData.TableInfo.DATE +
            " TEXT," + TableData.TableInfo.CONTENT + " TEXT);";
    public Database(Context context){
        super(context,TableData.TableInfo.TABLENAME,null,database_version);
        Log.d("Database operation","Database Created");

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInfo(Database db, String content){
        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.DATE,new Date().toString());
        cv.put(TableData.TableInfo.CONTENT,content);

        long k = SQ.insert(TableData.TableInfo.TABLENAME,null,cv);
        Log.d("Database Operations","One row inserted.");
    }

    public Cursor getInformation(Database db){
        SQLiteDatabase SQ=  db.getReadableDatabase();
        String [] columns ={TableData.TableInfo.DATE, TableData.TableInfo.CONTENT};
        Cursor SR = SQ.query(TableData.TableInfo.TABLENAME, columns, null, null, null, null, null);
        return SR;
    }

    public boolean isEmpty(Database db){
        Cursor CR = this.getInformation(db);
        CR.moveToFirst();
        if(CR.isAfterLast()){
            return true;
        }else{
            return false;
        }
    }

    public void deleteReminder(Database db, String date, String content){
        String selection = TableData.TableInfo.DATE + " LIKE ? AND " + TableData.TableInfo.CONTENT + " LIKE ?";
        String args[] = {date,content};
        SQLiteDatabase SQ = db.getWritableDatabase();
        SQ.delete(TableData.TableInfo.TABLENAME,selection,args);
    }
}

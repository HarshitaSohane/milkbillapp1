package com.example.milkbill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class milkdb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "milk.db";
    public static final String TABLE_NAME = "monthy_rec";
    public static final String COL_1 = "Date";
    public static final String COL_2 = "Cow_qty";
    public static final String COL_3 = "Buff_qty";
  //  public static final String COL_4 = "MARKS";

    public milkdb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (Date DATE PRIMARY KEY ,Cow_qty  DECIMAL(4,2) default(1) Not null,Buff_qty   DECIMAL(4,2) default (1) Not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date1,float cow_qty ,float buff_qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,date1);
        contentValues.put(COL_2,cow_qty);
        contentValues.put(COL_3,buff_qty);
        long result = db.insert(TABLE_NAME,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public void del()
    {
        SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("delete  from "+TABLE_NAME);
         db.close();

    }

    public boolean updateData(String ob,float cow_qty,float buff_qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, (ob));
        contentValues.put(COL_2,cow_qty);
        contentValues.put(COL_3,buff_qty);

        db.update(TABLE_NAME, contentValues, "Date = ?",new String[] {(ob)});
        return true;
    }
    public float sum_c() {
        SQLiteDatabase db = this.getWritableDatabase();
        float total_c=0;
        Cursor cursor = db.rawQuery("SELECT SUM(Cow_qty) as Total FROM monthy_rec", null);

        if (cursor.moveToFirst()) {

            total_c = cursor.getFloat(cursor.getColumnIndex("Total"));// get final total
        }
        return total_c;
    }
    public float sum_b() {
        SQLiteDatabase db = this.getWritableDatabase();
        float total_b=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_3 + ") as Total FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {

            total_b = cursor.getFloat(cursor.getColumnIndex("Total"));// get final total
        }
        return total_b;
    }

    public Integer deleteData (String ob) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Date = ?",new String[] {(ob)});
    }
}
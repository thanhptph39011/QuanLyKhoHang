package com.example.quanlykhohang.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.DataBase.DbHelper;

public class KhoDao {
    private SQLiteDatabase sql;
    private DbHelper db;

    public KhoDao(Context context) {
        db = new DbHelper(context);
        sql = db.getWritableDatabase();
    }

    @SuppressLint("Range")
    public int getSLConLai(int ID) {
        int soL = 0;
        sql = db.getReadableDatabase();
        String sl = "select soLuong from SanPham where maSp=?";
        Cursor cursor = sql.rawQuery(sl, new String[]{String.valueOf(ID)});
        while (cursor.moveToNext()) {
            int a = Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong")));
            soL = a;
        }
        cursor.close();
        return soL;
    }

    public int updateSL(int sl, int maSp) {
        ContentValues values = new ContentValues();
        values.put("soLuong", sl);
        return sql.update("SanPham", values, "maSp=?", new String[]{String.valueOf(maSp)});

    }

}

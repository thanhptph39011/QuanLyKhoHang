package com.example.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.NguoiDung;
import com.example.quanlykhohang.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDao {
    private SQLiteDatabase db;
    public TheLoaiDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }
    public List<TheLoai> getAll() {
        String sql = "Select * from TheLoai";
        return getData(sql);
    }
    public boolean insertTheLoai(TheLoai tl) {
        ContentValues values = new ContentValues();
        values.put("tenLoai",tl.getTenTheLoai());
        long row = db.insert("TheLoai", null, values);
        return (row > 0);
    }
    public boolean updateTheLoai(TheLoai tl) {
        ContentValues values = new ContentValues();
        values.put("tenLoai",tl.getTenTheLoai());
        long row = db.update("TheLoai", values,"maLoai=?", new String[]{String.valueOf(tl.getMaTheLoai())});
        return (row > 0);
    }
    public boolean deleteTheLoai(int maLoai){
        long row = db.delete("TheLoai", "maLoai=?", new String[]{String.valueOf(maLoai)});
        return (row > 0);
    }
    private List<TheLoai> getData(String sql, String... selectionArgs) {
        List<TheLoai> list = new ArrayList<TheLoai>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            TheLoai tl = new TheLoai();
            tl.setMaTheLoai(c.getInt(0));
            tl.setTenTheLoai(c.getString(1));
            list.add(tl);
        }
        return list;
    }
    public TheLoai getID(String id){
        String sql ="select * from TheLoai where maLoai=?";
        List<TheLoai> list = getData(sql,id);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }

    }
}

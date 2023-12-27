package com.example.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }
    public List<NguoiDung> getAll() {
        String sql = "Select * from User";
        return getData(sql);
    }
    public boolean insertUser(NguoiDung nd) {
        ContentValues values = new ContentValues();
        values.put("userName", nd.getUserName());
        values.put("passWord", nd.getPassWord());
        values.put("fullName",nd.getFullName());
        values.put("email", nd.getEmail());
        long row = db.insert("User", null, values);
        return (row > 0);
    }
    public long updatePass(NguoiDung obj) {
        ContentValues values = new ContentValues();
        values.put("userName", obj.getUserName());
        values.put("passWord", obj.getPassWord());
        values.put("fullName",obj.getFullName());
        values.put("email",obj.getEmail());
        return db.update("User", values, "maUser=?", new String[]{String.valueOf(obj.getMaNguoiDung())});
    }
    public boolean deleteUser(int maUser) {
        long row = db.delete("User", "maUser=?", new String[]{String.valueOf(maUser)});
        return (row > 0);
    }

    private List<NguoiDung> getData(String sql, String... selectionArgs) {
        List<NguoiDung> list = new ArrayList<NguoiDung>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            NguoiDung nd = new NguoiDung();
            nd.setMaNguoiDung(c.getInt(0));
            nd.setUserName(c.getString(1));
            nd.setPassWord(c.getString(2));
            nd.setFullName(c.getString(3));
            nd.setEmail(c.getString(4));
            list.add(nd);
        }
        return list;
    }
    public NguoiDung getID(String id){
        String sql ="select * from User where maUser=?";
        List<NguoiDung> list = getData(sql,id);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }
    public NguoiDung getUserName(String userName){
        String sql ="select * from User where userName=?";
        List<NguoiDung> list = getData(sql,userName);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }
}

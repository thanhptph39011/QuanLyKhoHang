package com.example.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.SanPham;
import com.example.quanlykhohang.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDao {
    private SQLiteDatabase db;
    public SanPhamDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }
    public List<SanPham> getAll() {
        String sql = "Select * from SanPham";
        return getData(sql);
    }
    public boolean insertSanPham(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("maLoai",sp.getMaLoai());
        values.put("tenSp",sp.getTenSp());
        values.put("gia",sp.getGia());
        values.put("soLuong",sp.getSoLuong());
        values.put("moTa",sp.getMaLoai());
        long row = db.insert("SanPham", null, values);
        return (row > 0);
    }
    public boolean updateSanPham(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("maLoai",sp.getMaLoai());
        values.put("tenSp",sp.getTenSp());
        values.put("gia",sp.getGia());
        values.put("soLuong",sp.getSoLuong());
        values.put("moTa",sp.getMaLoai());
        long row = db.update("SanPham",  values,"maSp=?",new String[]{String.valueOf(sp.getMaSp())});
        return (row > 0);
    }
    public boolean deleteTheLoai(int maSp){
        long row = db.delete("SanPham", "maSp=?", new String[]{String.valueOf(maSp)});
        return (row > 0);
    }
    private List<SanPham> getData(String sql, String... selectionArgs) {
        List<SanPham> list = new ArrayList<SanPham>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            SanPham sp = new SanPham();
            sp.setMaSp(c.getInt(0));
            sp.setMaLoai(c.getInt(1));
            sp.setTenSp(c.getString(2));
            sp.setGia(c.getInt(3));
            sp.setSoLuong(c.getInt(4));
            sp.setMoTa(c.getString(5));
            list.add(sp);
        }
        return list;
    }
}
